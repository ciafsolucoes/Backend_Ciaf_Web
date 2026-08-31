package br.com.ciaf.admin.controllers.fiscal;

import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.entities.fiscal.CartaCorrecao;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import br.com.ciaf.admin.services.CartaCorrecaoService;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;

import br.com.swconsultoria.nfe.schema.eventoSuframaInternaliza.TEnvEvento;
import br.com.swconsultoria.nfe.schema.eventoSuframaInternaliza.TRetEnvEvento;
import br.com.swconsultoria.nfe.schemas_eventos.TEnvEventoCartaCorrecao;
import br.com.swconsultoria.nfe.schemas_eventos.TRetEnvEventoCartaCorrecao;
import br.com.swconsultoria.nfe.util.CartaCorrecaoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carta-correcao")
public class CartaCorrecaoController {
    private static String cnpj;
    private String status;
    private String statusMotivo;
    private String protocolo;
    private String chave;
    private static String pathXml;
    private static String totalPathXml;
    private int sequencia;

    private final EmitenteRepository emitenteRepository;
    private final CartaCorrecaoService service;

    public CartaCorrecaoController(EmitenteRepository emitenteRepository, CartaCorrecaoService service) {
        this.emitenteRepository = emitenteRepository;
        this.service = service;
    }


    @CrossOrigin
    @GetMapping(path = { "/all/{emitente_id}" })
    public ResponseEntity<List<CartaCorrecao>> getCartas(@PathVariable Long emitente_id){
        List<CartaCorrecao> list = service.findAll(emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = { "/{id}" })
    public ResponseEntity<?> findById(@PathVariable Long id){
        CartaCorrecao list = service.findById(id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = { "/number/{emitente_id}" })
    public int findLastNumber(@PathVariable Long emitente_id){
        int	number = service.findFinalNumber(emitente_id);
        return number;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<CartaCorrecao> save(@RequestBody CartaCorrecao dados){
        dados = service.save(dados);
        return ResponseEntity.ok().body(dados);
    }

    @CrossOrigin
    @PatchMapping(value = { "/{id}" })
    public ResponseEntity<CartaCorrecao> update(@PathVariable Long id, @RequestBody CartaCorrecao dados){
        dados = service.update(id, dados);
        return ResponseEntity.ok().body(dados);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public Optional<CartaCorrecao> enviarCarta(@PathVariable long id, @RequestBody CartaCorrecao dadosCarta) {
        Optional<CartaCorrecao> obj = Optional.of(new CartaCorrecao()) ;
        try {
            String password = emitenteRepository.getPassword(id);
            Emitente emitente = emitenteRepository.findById(id).orElseThrow();
            String certPath = "certificado" + emitente.getId() + ".pfx";
            CartaCorrecao updateCarta = service.findById(id);
            sequencia = updateCarta.getNrseq() + 1;
            cnpj = "03054280000109"; // cnpj emitente
            ConfiguracoesNfe config  = criarConfiguracoes(password, certPath);

            Evento cce = new Evento();
            cce.setChave(dadosCarta.getChave());
//			System.out.println(dadosCarta.getChave());
//			System.out.println(cce.getChave());
            //Informe o CNPJ do emitente
//          cce.setCnpj(dadosCarta.getCnpjcpf());
            cce.setCnpj(cnpj);
            //Informe o Texto da Carta de Correção
            cce.setMotivo(dadosCarta.getMotivo());
            //Informe a data da Carta de Correção
            cce.setDataEvento(LocalDateTime.now());
            //Informe a sequencia do Evento
            cce.setSequencia(sequencia);
            // Monta o Evento
            TEnvEventoCartaCorrecao envEvento = CartaCorrecaoUtil.montaCCe(cce,config);
            //Envia a CCe
            TRetEnvEventoCartaCorrecao retorno = Nfe.cce(config, envEvento, true);
//            System.out.println(envEvento);
            //Valida o Retorno do Carta de Correção
            RetornoUtil.validaCartaCorrecao(retorno);

            //Resultado
            System.out.println();
            String xmlFinal;
            retorno.getRetEvento().forEach( resultado -> {
                System.out.println("# Chave: " + resultado.getInfEvento().getChNFe());
                chave = resultado.getInfEvento().getChNFe();
                System.out.println("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
//                status ="# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo();
//                xmlFinal = XmlNfeUtil.criaNfeProc();
                status = resultado.getInfEvento().getCStat();
                statusMotivo = resultado.getInfEvento().getXMotivo();
                System.out.println("# Protocolo: " + resultado.getInfEvento().getNProt());
                protocolo =  resultado.getInfEvento().getNProt();
            });
            //Cria ProcEvento da CCe
            String proc = CartaCorrecaoUtil.criaProcEventoCCe(config, envEvento, retorno);
            convertStringToXMLDocument(proc, dadosCarta);
            pathXml = "carta-correcao-" + dadosCarta.getNrcarta() + ".xml" ;
            System.out.println();
            System.out.println("# ProcEvento : " + proc);
//          CartaCorrecao updateCarta = service.findById(id);
            dadosCarta.setProtocolo(protocolo);
            dadosCarta.setStatus(status);
            dadosCarta.setCnpjcpf(cnpj);
            dadosCarta.setChave(chave);
            dadosCarta.setCodigouf(31);
            dadosCarta.setNrseq(sequencia);
            dadosCarta.setPathxml(pathXml);
            service.update(id, dadosCarta);

            obj.orElseThrow(null).setProtocolo(protocolo);
            obj.orElseThrow(null).setStatus(status);
            obj.orElseThrow(null).setCnpjcpf(cnpj);
            obj.orElseThrow(null).setChave(chave);
            obj.orElseThrow(null).setNrseq(sequencia);

        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
            String message = e.getMessage().substring(20);
            obj.orElseThrow(null).setStatus("Error =>  " +  message);
//	        obj.orElseThrow(null).setStatus(statusMotivo);
        }
        return obj;
    }

    private static Document convertStringToXMLDocument(String proc, CartaCorrecao dadosCarta) {
        // Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            // Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            // Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(proc)));

            // salvando o xml com nome destinatário e data de emissão
//			try (FileOutputStream output = new FileOutputStream(
//					"/var/www/ciaf.online/xmls/" +
//			         dados.getDest_XNome().substring(0, 8).replace(" ", "-")
//			         +"_" + dataEmissao.toString().substring(0, 19) + ".xml")) {
//				writeXml(doc, output);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
            totalPathXml = "/home/gilvan/ciaf/frontciaf/static/cartas/carta-correcao-" + dadosCarta.getNrcarta() + ".xml";
            try (FileOutputStream output = new FileOutputStream(
                    totalPathXml)) {
                writeXml(doc, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);

    }
    private ConfiguracoesNfe criarConfiguracoes(String password, String certPath) throws FileNotFoundException, CertificadoException {
        Certificado certificado = CertificadoService.certificadoPfx(
                "/home/gilvan/ciaf/certificados/"+certPath, password);
        return ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.MG, AmbienteEnum.HOMOLOGACAO, certificado,
                "/home/gilvan/ciaf/schemas");
    }

}
