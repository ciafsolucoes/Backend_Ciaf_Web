package br.com.ciaf.admin.controllers.fiscal;

import br.com.ciaf.admin.dtos.fiscal.DadosNfeDto;
import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.entities.fiscal.NotasItens;
import br.com.ciaf.admin.entities.fiscal.NotasStatus;
import br.com.ciaf.admin.master.repositories.MasterEmitenteRepository;
import br.com.ciaf.admin.repositories.NotasFiscaisRepository;
import br.com.ciaf.admin.services.NotasFiscaisService;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.impressao.model.Impressao;
import br.com.swconsultoria.impressao.service.ImpressaoService;
import br.com.swconsultoria.impressao.util.ImpressaoUtil;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.dom.enuns.StatusEnum;
import br.com.swconsultoria.nfe.schemas.*;
import br.com.swconsultoria.nfe.util.ChaveUtil;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/emitir-nfe")
public class EmitNfe {
    private static final Logger log = LoggerFactory.getLogger(EmitNfe.class);

    private final MasterEmitenteRepository masterEmitenteRepository;
    private final NotasFiscaisService service;

    private static final Logger logger = LoggerFactory.getLogger(EmitNfe.class);

    public EmitNfe(MasterEmitenteRepository masterEmitenteRepository, NotasFiscaisService service) {
        this.masterEmitenteRepository = masterEmitenteRepository;
        this.service = service;
    }

    @CrossOrigin
    @PostMapping(path = "/{emitente_id}")
    public Optional<NotasStatus> emitirNfe(@PathVariable Long emitente_id, @RequestBody DadosNfeDto dadosDaNota) throws Exception, SocketException {
        int numero = dadosDaNota.getNumero();
        String pathPdf = null;
        String pathXml = null;
        String pathXml2 = null;
        String retornosefaz = null;
        String msgRetornoSefaz = "Um erro desconhecido aconteceu.";
        String chaveNf = null;

        String password = masterEmitenteRepository.getPassword(emitente_id);
        Emitente emitente = masterEmitenteRepository.findById(emitente_id).orElseThrow();

        if (password != null &&
                !password.isEmpty() &&
                emitente.getEmit_cnpj() != null &&
                emitente.getEmit_UF() != null &&
                !emitente.getEmit_UF().isEmpty() &&
                !emitente.getEmit_cnpj().isEmpty()
        ) {
            try {
                EmissaoContext contexto = new EmissaoContext(
                        emitente.getEmit_cnpj(),
                        "55",
                        dadosDaNota.getNatOp(),
                        dadosDaNota.getTpEmis(),
                        String.format("%08d", new Random().nextInt(99999999)),
                        dadosDaNota.getSerie(),
                        numero,
                        LocalDateTime.now()
                );
                String certPath = "certificado_" + emitente.getId() + ".pfx";
                contexto.configuracoesNfe = criarConfiguracoes(password, certPath, emitente.getEmit_UF(), emitente.getEmit_cnpj());
                contexto.chaveUtil = montaChaveNFe(contexto);
                chaveNf = contexto.chaveUtil.getChaveNF();
                TEnviNFe enviNFe = criaEnviNFe(dadosDaNota, emitente, contexto);
                String myxml = XmlNfeUtil.objectToXml(enviNFe);
                convertStringToXMLDocument(myxml, emitente.getId(), numero);
                // Efetua assinatura e Validação
                enviNFe = Nfe.montaNfe(contexto.configuracoesNfe, enviNFe, true);
                // salvar xml
//                myxml = XmlNfeUtil.objectToXml(enviNFe);
//                convertStringToXMLDocument(myxml, emitente.getId());
                // Envio Da Nota
                TRetEnviNFe retorno = Nfe.enviarNfe(contexto.configuracoesNfe, enviNFe, DocumentoEnum.NFE);
                retornosefaz = retorno.getCStat();
                pathPdf = "NFe_"+ emitente.getId() + "_" + numero + ".pdf";
                pathXml = "/var/www/ciafonline.com.br/xmls/NFe_" + emitente.getId() + "_" + numero + ".xml";
                pathXml2 = "/var/www/ciafonline.com.br/xmls2/NFe_" + emitente.getId() + "_" + numero + ".xml";
                saveXmlToFile(myxml, pathXml2);
                String xmlFinal;

                // FAz a verificação se o Retorno é Assincrono e consulta o Recibo
                if (RetornoUtil.isRetornoAssincrono(retorno)) {
                    TRetConsReciNFe tRetConsReciNFe = verificaEnvioAssincrono(
                            retorno, contexto.configuracoesNfe);
                    RetornoUtil.validaAssincrono(tRetConsReciNFe);
                    System.out.println("Status: " + tRetConsReciNFe.getProtNFe().get(0).getInfProt().getCStat());
                    System.out.println("Protocolo: " + tRetConsReciNFe.getProtNFe().get(0).getInfProt().getNProt());
                    xmlFinal = XmlNfeUtil.criaNfeProc(enviNFe, tRetConsReciNFe.getProtNFe().get(0));
                    System.out.println("Xml Final: " + xmlFinal);
                    retornosefaz = retorno.getProtNFe().getInfProt().getCStat();
                    service.updateInEmit(numero, emitente_id, chaveNf, pathPdf);
                } else {
                    RetornoUtil.validaSincrono(retorno);
                    logger.info("Status: " + retorno.getProtNFe().getInfProt().getCStat());
                    System.out.println("Protocolo: " + retorno.getProtNFe().getInfProt().getNProt());
                    xmlFinal = XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe());
                    System.out.println("Xml Final: " + xmlFinal);
                    retornosefaz = retorno.getProtNFe().getInfProt().getCStat();
                    msgRetornoSefaz = retorno.getProtNFe().getInfProt().getXMsg();
                    logger.info("status pegado aqui SINCRONO getCStat:" + retornosefaz);
                    logger.info(".getInfProt().getXMsg() pegado aqui SINCRONO:" + msgRetornoSefaz);
                    service.updateInEmit(numero,emitente_id, chaveNf, pathPdf);
                }
                saveXmlToFile(xmlFinal, pathXml);
//                saveXmlToFile(myxml, pathXml2);
                convertStringToXMLDocument(xmlFinal, emitente.getId(), numero);
//                efetuarImpressaoNFe(myxml, dadosDaNota);
//                efetuarImpressaoNFe(xmlFinal, dadosDaNota);
            } catch (Exception e) {
                msgRetornoSefaz = e.getMessage() != null ? e.getMessage() : "Erro interno ao emitir NFe";
                log.error(
                        "Erro ao emitir NFe. emitenteId={}, numero={}, chave={}, pathPdf={}, pathXml={}, pathXml2={}, msgRetornoSefaz={}",
                        emitente_id,
                        numero,
                        chaveNf,
                        pathPdf,
                        pathXml,
                        pathXml2,
                        msgRetornoSefaz,
                        e
                );
            }
        } else {
            msgRetornoSefaz = "Cadastro do cliente incompleto ou problemas de certificado(vencimento, senha ou outro)";
            logger.error("faltou um parâmetro fundamental: do certificado ou emitente");
        }
        return getNotasStatus(numero, pathPdf, retornosefaz, msgRetornoSefaz, pathXml);
    }

    private TRetConsReciNFe verificaEnvioAssincrono(
            TRetEnviNFe retorno, ConfiguracoesNfe configuracoesNfe) throws Exception {
        String recibo = retorno.getInfRec().getNRec();
        int tentativa = 1;
        TRetConsReciNFe retornoConsulta = null;
        while (true) {
            retornoConsulta = Nfe.consultaRecibo(configuracoesNfe, recibo, DocumentoEnum.NFE);
            if (retornoConsulta.getCStat().equals(StatusEnum.LOTE_EM_PROCESSAMENTO.getCodigo())) {
                Thread.sleep(1000);
                tentativa++;
                if (tentativa > 10) {
                    // SALVAR O RECIBO E CONSULTA POSTERIORMENTE
                    throw new Exception("Lote em processamento.Aguarde um tempo e tente novamente.");
                }
            } else {
                break;
            }
        }
        return retornoConsulta;
    }

    private void efetuarImpressaoNFe(String xmlFinal, DadosNfeDto dadosDaNota, int numero)
            throws JRException, ParserConfigurationException, IOException, SAXException {
        Impressao impressao = ImpressaoUtil.impressaoPadraoNFe(xmlFinal);

        // Faz a impressão em pdf File passando o caminho do Arquivo
        ImpressaoService.impressaoPdfArquivo(impressao, "/var/www/ciafonline.com.br/danfes/NFe_" + numero + ".pdf");
        ImpressaoService.impressaoHtml(impressao, "/var/www/ciafonline.com.br/teste-nfe.html");
    }

    private void convertStringToXMLDocument(String myXml2, Long emitente_id, int numero) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            // Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            // Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(myXml2)));

            // salvando o xml com nome destinatário e data de emissão
            try (FileOutputStream output = new FileOutputStream(
                    "/var/www/ciafonline.com.br/xmls/NFe_" + emitente_id + "_" + numero + ".xml")) {
                writeXml(doc, output);
            } catch (IOException e) {
                System.out.println("Error create xml");
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveXmlToFile(String xmlContent, String filePath) throws IOException {
        File file = new File(filePath);
        // Create directories if they do not exist
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(xmlContent);
        }
    }


    private void writeXml(Document doc, FileOutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }

    private ConfiguracoesNfe criarConfiguracoes(String password, String certPath, String estado, String emitente) throws FileNotFoundException, CertificadoException {
        String env = "PRODUCAO";
        if (emitente.equals("5182162520022")) env = "HOMOLOGACAO";
        AmbienteEnum ambiente = createAmbiente(env);
        EstadosEnum estadosEnum = createEstado(estado);
        Certificado certificado = CertificadoService.certificadoPfx(
                "/home/gilvan/ciaf/certificados/" + certPath, password);
        return ConfiguracoesNfe.criarConfiguracoes(estadosEnum, ambiente, certificado,
                "/home/gilvan/ciaf/schemas");
    }

    private EstadosEnum createEstado(String estado) {
        EstadosEnum estadoEnum = null;
        for (EstadosEnum estadosEnum : EstadosEnum.values()) {
            if (estadosEnum.toString().equals(estado)) {
                estadoEnum = estadosEnum;
                break;
            }
        }
        return estadoEnum;
    }

    private AmbienteEnum createAmbiente(String ambiente) {

        AmbienteEnum ambienteEnum = null;
        for (AmbienteEnum ambienteEnum2 : AmbienteEnum.values()) {
            if (ambienteEnum2.toString().equals(ambiente)) {
                ambienteEnum = ambienteEnum2;
                break;
            }
        }
        return ambienteEnum;
    }

    private ChaveUtil montaChaveNFe(EmissaoContext contexto) {
        return new ChaveUtil(
                contexto.configuracoesNfe.getEstado(),
                contexto.cnpj,
                contexto.modelo,
                contexto.serie,
                contexto.numero,
                contexto.tipoEmissao,
                contexto.cnf,
                contexto.dataEmissao
        );
    }

    private TEnviNFe criaEnviNFe(DadosNfeDto dadosDaNota, Emitente emitente, EmissaoContext contexto) {
        TEnviNFe enviNFe = new TEnviNFe();
        enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
        enviNFe.setIdLote("1");
        enviNFe.setIndSinc("1");
        TNFe nfe = new TNFe();
        TNFe.InfNFe infNFe = getInfNFe(dadosDaNota, emitente, contexto);
        nfe.setInfNFe(infNFe);
        enviNFe.getNFe().add(nfe);
        return enviNFe;
    }

    private TNFe.InfNFe getInfNFe(DadosNfeDto dadosDaNota, Emitente emitente, EmissaoContext contexto) {
        TNFe.InfNFe infNFe = new TNFe.InfNFe();
        TNFe.InfNFe.Exporta export = new TNFe.InfNFe.Exporta();
        infNFe.setId(contexto.chaveUtil.getChaveNF());
        infNFe.setVersao(ConstantesUtil.VERSAO.NFE);
        infNFe.setIde(montaIde(dadosDaNota, contexto));
        infNFe.setEmit(montaEmitente(emitente, contexto));
        infNFe.setDest(montaDestinatario(dadosDaNota, contexto.cnpj));
        contexto.vBCIBSCBSTot = 0.0;
        NotasItens[] notasItens = dadosDaNota.getItens();
        for (int i = 0; i < notasItens.length; i++) {
            infNFe.getDet().addAll(montaDet(i, dadosDaNota, notasItens[i]));
            contexto.vBCIBSCBSTot += Double.valueOf(notasItens[i].getProd_VProd());
        }
        infNFe.setTransp(montaTransportadora(dadosDaNota));
        infNFe.setPag(pagamento(dadosDaNota));
        infNFe.setInfRespTec(montaRespTecnico());
        infNFe.setTotal(montaTotal(dadosDaNota, contexto.vBCIBSCBSTot));

        if (dadosDaNota.getIde_IdDest().equals("3")) {
            System.out.println("=== Exportacao ===");
            export.setUFSaidaPais(TUfEmi.valueOf(dadosDaNota.getUFSaidaPais()));
            export.setXLocExporta(dadosDaNota.getxLocExporta());
            export.setXLocDespacho(contexto.cnf);
            infNFe.setExporta(export);
        } else {
            infNFe.setExporta(null);
        }
        if (dadosDaNota.getEntrega() != null) {
            infNFe.setEntrega(montaEntrega(dadosDaNota));
        }
        infNFe.setInfAdic(montaInfAdcionais(dadosDaNota));
        return infNFe;
    }


    private TLocal montaEntrega(DadosNfeDto dadosDaNota) {
        TLocal entrega = new TLocal();
        entrega.setCNPJ(dadosDaNota.getEntrega().getCpf_cnpj());
        entrega.setCPF(dadosDaNota.getEntrega().getCpf_cnpj());
        entrega.setXNome(dadosDaNota.getEntrega().getxNome());
        entrega.setXLgr(dadosDaNota.getEntrega().getxLgr());
        entrega.setNro(dadosDaNota.getEntrega().getNro());
        entrega.setXCpl(dadosDaNota.getEntrega().getxCpl());
        entrega.setXBairro(dadosDaNota.getEntrega().getxBairro());
        entrega.setCMun(dadosDaNota.getEntrega().getcMun());
        entrega.setXMun(dadosDaNota.getEntrega().getxMun());
        entrega.setUF(TUf.valueOf(dadosDaNota.getEntrega().getUf()));
        entrega.setCEP(dadosDaNota.getEntrega().getCep());
        entrega.setCPais(dadosDaNota.getEntrega().getcPais());
        entrega.setXPais(dadosDaNota.getEntrega().getxPais());
        entrega.setFone(dadosDaNota.getEntrega().getFone());
        entrega.setEmail(dadosDaNota.getEntrega().getEmail());
        entrega.setIE(dadosDaNota.getEntrega().getIe());
        return entrega;
    }

    private TNFe.InfNFe.InfAdic montaInfAdcionais(DadosNfeDto dadosDaNota) {
        TNFe.InfNFe.InfAdic infAdic = new TNFe.InfNFe.InfAdic();
        if (dadosDaNota != null && dadosDaNota.getInfAdic() != null && !dadosDaNota.getInfAdic().isEmpty()) {
            infAdic.setInfCpl(dadosDaNota.getInfAdic());
        }
        return infAdic;
    }

    private TNFe.InfNFe.Ide montaIde(DadosNfeDto dadosNfeDto, EmissaoContext contexto) {
        TNFe.InfNFe.Ide ide = new TNFe.InfNFe.Ide();
        ide.setCUF(contexto.configuracoesNfe.getEstado().getCodigoUF());
        ide.setCNF(contexto.cnf);
        ide.setNatOp(String.valueOf(contexto.natOp));
        ide.setMod(contexto.modelo);
        ide.setSerie(String.valueOf(contexto.serie));
        ide.setNNF(String.valueOf(contexto.numero));
        ide.setDhEmi(XmlNfeUtil.dataNfe(contexto.dataEmissao));
        ide.setTpNF(dadosNfeDto.getTpNF()); // 0 = Entrada 1 = Saída
        ide.setIdDest(dadosNfeDto.getIde_IdDest()); // 1=Operação interna; 2=Operação interestadual; 3=Operação com exterior.
        ide.setCMunFG(dadosNfeDto.getcMunFG());
//        ide.setTpNFCredito();
//        ide.setTpNFDebito();
//        ide.setCMunFGIBS("");
//        ide.setGCompraGov();
//         ide.setGPagAntecipado();
        ide.setTpImp(dadosNfeDto.getTpImp()); // tipo de impressão da danfe 1=DANFE normal, Retrato; 2=DANFE normal, Paisagem;
        // 3=DANFE Simplificado; 4=DANFE NFC-e;
        ide.setTpEmis(contexto.tipoEmissao); // 1=Emissão normal (não em contingência); 2=Contingência FS-IA,
        ide.setCDV(contexto.chaveUtil.getDigitoVerificador());
        ide.setTpAmb(contexto.configuracoesNfe.getAmbiente().getCodigo());
        ide.setFinNFe(dadosNfeDto.getFinNFe()); // 1=NF-e normal; 2=NF-e complementar; 3=NF-e de ajuste; 4=Devolução de
        // mercadoria.
        ide.setIndFinal(dadosNfeDto.getIndFinal());
        ide.setIndPres(dadosNfeDto.getIndPres());
        ide.setProcEmi(dadosNfeDto.getProcEmi());
        ide.setVerProc(dadosNfeDto.getVerProc());
        ide.setIndIntermed("0");
        return ide;
    }

    private TNFe.InfNFe.Emit montaEmitente(Emitente emitente, EmissaoContext contexto) {
        TNFe.InfNFe.Emit emit = new TNFe.InfNFe.Emit();
        emit.setXNome(emitente.getEmit_XNome());
        emit.setCNPJ(contexto.cnpj);
        emit.setIE(emitente.getEmit_IE());
        emit.setCRT(emitente.getEmit_CRT());
        TEnderEmi enderecoEmitente = new TEnderEmi();
        enderecoEmitente.setXLgr(emitente.getEmit_XLgr());
        enderecoEmitente.setNro(emitente.getEmit_Nro());
        enderecoEmitente.setXCpl(emitente.getEmit_XCpl());
        enderecoEmitente.setXBairro(emitente.getEmit_XBairro());
        enderecoEmitente.setCMun(emitente.getEmit_CMun());
        enderecoEmitente.setXMun(emitente.getEmit_XMun());
        enderecoEmitente.setUF(TUfEmi.valueOf(contexto.configuracoesNfe.getEstado().toString()));
        enderecoEmitente.setCEP(emitente.getEmit_CEP());
        emit.setEnderEmit(enderecoEmitente);
        return emit;
    }

    private TNFe.InfNFe.Dest montaDestinatario(DadosNfeDto dadosDaNota, String cnpj) {
        TNFe.InfNFe.Dest dest = new TNFe.InfNFe.Dest();
        if (cnpj.equals("5182162520022")) {
            dest.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        } else {
            dest.setXNome(dadosDaNota.getDest_XNome());
        }
//	    dest.setXNome(dadosDaNota.getDest_XNome());
        dest.setIndIEDest(dadosDaNota.getDest_IndIEDest()); //1=Contribuinte ICMS (informar a IE do destinatário); 2 = Contribuinte isento de Inscrição no cadastro de Contribuintes do ICMS;
        if (!dadosDaNota.getDest_CPF().isEmpty() && !dadosDaNota.getDest_CPF().isBlank()) {
            dest.setCPF(dadosDaNota.getDest_CPF());
        }
        if (!dadosDaNota.getDest_CNPJ().isEmpty() && !dadosDaNota.getDest_CNPJ().isBlank()) {
            dest.setCNPJ(dadosDaNota.getDest_CNPJ());
        }
        if (dest.getIndIEDest().equals("1") && !dadosDaNota.getDest_CNPJ().isEmpty()) {
            dest.setCNPJ(dadosDaNota.getDest_CNPJ());
        }
        if (dadosDaNota.getDest_ie() != null) {
            if (
                    !dadosDaNota.getDest_ie().isEmpty()
                            && !dadosDaNota.getDest_ie().isBlank()
                            && !dest.getIndIEDest().equals("9")
                            && !(dadosDaNota.getDest_ie().equalsIgnoreCase("ISENTO"))
            ) {
                dest.setIE(dadosDaNota.getDest_ie());
            }
        }

        TEndereco enderecoDestinatario = new TEndereco();
        enderecoDestinatario.setXLgr(dadosDaNota.getDest_XLgr());
        enderecoDestinatario.setNro(dadosDaNota.getDest_Nro());
        if (!dadosDaNota.getDest_XCpl().isEmpty()) {
            enderecoDestinatario.setXCpl(dadosDaNota.getDest_XCpl());
        }
        enderecoDestinatario.setXBairro(dadosDaNota.getDest_XBairro());
        enderecoDestinatario.setCMun(dadosDaNota.getDest_CMun());
        enderecoDestinatario.setXMun(dadosDaNota.getDest_XMun());
//	        usa TUf para o UF
        enderecoDestinatario.setUF(TUf.valueOf(dadosDaNota.getDest_UF()));
        enderecoDestinatario.setCEP(dadosDaNota.getDest_CEP());
        dest.setEnderDest(enderecoDestinatario);
        return dest;
    }


    // ======================================
    private TNFe.InfNFe.Total montaTotal(DadosNfeDto dadosDaNota, double vBCIBSCBSTot) {
        TNFe.InfNFe.Total total = new TNFe.InfNFe.Total();
        TNFe.InfNFe.Total.ICMSTot icmsTot = new TNFe.InfNFe.Total.ICMSTot();
        TISTot tisTot = new TISTot();
        tisTot.setVIS("0.00");
        icmsTot.setVICMS(formatToTwoDecimals(dadosDaNota.getIcmsTot_VICMS()));
        icmsTot.setVBC(formatToTwoDecimals(dadosDaNota.getIcmsTot_VBC()));
        icmsTot.setVICMSDeson(formatToTwoDecimals(dadosDaNota.getIcmsTot_VICMSDeson()));
        icmsTot.setVFCP(formatToTwoDecimals(dadosDaNota.getIcmsTot_VFCP()));
        icmsTot.setVBCST(formatToTwoDecimals(dadosDaNota.getIcmsTot_VBCST()));
        icmsTot.setVST(formatToTwoDecimals(dadosDaNota.getIcmsTot_VST()));
        icmsTot.setVFCPST(formatToTwoDecimals(dadosDaNota.getIcmsTot_VFCPST()));
        icmsTot.setVFCPSTRet(formatToTwoDecimals(dadosDaNota.getIcmsTot_vFCPStRet()));
        icmsTot.setVProd(formatToTwoDecimals(dadosDaNota.getIcmsTot_VProd()));
        icmsTot.setVFrete(formatToTwoDecimals(dadosDaNota.getIcmsTot_vFrete()));
        icmsTot.setVSeg(formatToTwoDecimals(dadosDaNota.getIcmsTot_vSeg()));
        icmsTot.setVDesc(formatToTwoDecimals(dadosDaNota.getIcmsTot_vDesc()));
        icmsTot.setVII(formatToTwoDecimals(dadosDaNota.getIcmsTot_vII()));
        icmsTot.setVIPI(formatToTwoDecimals(dadosDaNota.getIcmsTot_VIPI()));
        icmsTot.setVIPIDevol(formatToTwoDecimals(dadosDaNota.getIcmsTot_VIPIDevol()));
        icmsTot.setVPIS(formatToTwoDecimals(dadosDaNota.getIcmsTot_VPIS()));
        icmsTot.setVCOFINS(formatToTwoDecimals(dadosDaNota.getIcmsTot_VCOFINS()));
//	        icmsTot.setVTotTrib("");
        icmsTot.setVOutro(formatToTwoDecimals(dadosDaNota.getIcmsTot_VOutro()));
        icmsTot.setVNF(formatToTwoDecimals(dadosDaNota.getIcmsTot_VNF()));
        System.out.println(dadosDaNota.getIcmsTot_VNF() + "  valor NF");

        total.setICMSTot(icmsTot);
        total.setIBSCBSTot(criaTibsCbsMonoTot(vBCIBSCBSTot));
        total.setISTot(tisTot);
        return total;
    }

    public TIBSCBSTot criaTIBSCBSTot() {
        TIBSCBSTot tibscbsTot = new TIBSCBSTot();
        return tibscbsTot;
    }

    public TIBSCBSMonoTot criaTibsCbsMonoTot(double vBCIBSCBSTot) {
        Double vIbs = (vBCIBSCBSTot * 0.1) / 100;
        Double vCbs = (vBCIBSCBSTot * 0.9) / 100;
        TIBSCBSMonoTot tibscbsMonoTot = new TIBSCBSMonoTot();
        TIBSCBSMonoTot.GIBS gibs = new TIBSCBSMonoTot.GIBS();


        TIBSCBSMonoTot.GIBS.GIBSMun gibsMun = new TIBSCBSMonoTot.GIBS.GIBSMun();
        gibsMun.setVDif("0.00");
        gibsMun.setVIBSMun("0.00");
        gibsMun.setVDevTrib("0.00");

        TIBSCBSMonoTot.GIBS.GIBSUF gibsuf = new TIBSCBSMonoTot.GIBS.GIBSUF();
        gibsuf.setVDif("0.00");
        gibsuf.setVDevTrib("0.00");
        String plainStringIbs = BigDecimal.valueOf(vIbs).setScale(2, RoundingMode.HALF_UP).toPlainString();
        gibsuf.setVIBSUF(plainStringIbs);

        gibs.setGIBSUF(gibsuf);
        gibs.setGIBSMun(gibsMun);
        gibs.setVIBS(plainStringIbs);
        gibs.setVCredPres("0.00");
        gibs.setVCredPresCondSus("0.00");

        TIBSCBSMonoTot.GCBS gcbs = new TIBSCBSMonoTot.GCBS();
        gcbs.setVDif("0.00");
        gcbs.setVDevTrib("0.00");
        gcbs.setVCBS(BigDecimal.valueOf(vCbs).setScale(2, RoundingMode.HALF_UP).toPlainString());
        gcbs.setVCredPres("0.00");
        gcbs.setVCredPresCondSus("0.00");

        TIBSCBSMonoTot.GMono gMono = new TIBSCBSMonoTot.GMono();
        gMono.setVCBSMono("0.00");
        gMono.setVIBSMono("0.00");
        gMono.setVCBSMonoRet("0.00");
        gMono.setVIBSMonoRet("0.00");
        gMono.setVCBSMonoReten("0.00");
        gMono.setVIBSMonoReten("0.00");

        tibscbsMonoTot.setVBCIBSCBS(String.format(Locale.US, "%.2f", vBCIBSCBSTot));
        tibscbsMonoTot.setGIBS(gibs);
        tibscbsMonoTot.setGCBS(gcbs);
        tibscbsMonoTot.setGMono(gMono);

        return tibscbsMonoTot;
    }

    private TInfRespTec montaRespTecnico() {
        TInfRespTec respTec = new TInfRespTec();
        respTec.setCNPJ("03054280000109");
        respTec.setXContato("Ciaf Soluções em Software");
        respTec.setEmail("gilvan@ciaf.com.br");
        respTec.setFone("35984384286");
        return respTec;
    }

    private TNFe.InfNFe.Pag pagamento(DadosNfeDto dadosDaNota) {
//	    	System.out.println(dadosDaNota.getProd_VProd());
        TNFe.InfNFe.Pag pag = new TNFe.InfNFe.Pag();
        TNFe.InfNFe.Pag.DetPag detPag = new TNFe.InfNFe.Pag.DetPag();
        detPag.setTPag("01");
        var pgHere = Double.parseDouble(dadosDaNota.getvPag().replace(",", "."));
//        detPag.setVPag(dadosDaNota.getvPag());
        detPag.setVPag(String.format(Locale.US, "%.2f", pgHere));
        pag.getDetPag().add(detPag);
        if (dadosDaNota.getvTroco() > 0) {
            String strTroco = String.format(Locale.ENGLISH, "%.2f", dadosDaNota.getvTroco());
            pag.setVTroco(strTroco);
        }
        return pag;
    }

    private TNFe.InfNFe.Transp montaTransportadora(DadosNfeDto dadosDaNota) {
        TNFe.InfNFe.Transp transp = new TNFe.InfNFe.Transp();
        TNFe.InfNFe.Transp.Transporta transportadora = new TNFe.InfNFe.Transp.Transporta();
        TNFe.InfNFe.Transp.Vol volume = new TNFe.InfNFe.Transp.Vol();
        TVeiculo veiculo = new TVeiculo();
//	    transp.setModFrete("9");
        transp.setModFrete(dadosDaNota.getModFrete());
        if (!dadosDaNota.getModFrete().equals("9")) {
            transportadora.setXNome(dadosDaNota.getNome_transp());
            System.out.println(dadosDaNota.getCNPJ_transp() + " <= CNPJ trnasporte ");
            transportadora.setCNPJ(dadosDaNota.getCNPJ_transp());
            transportadora.setCPF(dadosDaNota.getCPF_transp());
            transportadora.setIE(dadosDaNota.getIe_transp());
            if (!dadosDaNota.getUf_transp().isEmpty()) {
                transportadora.setUF(TUf.valueOf(dadosDaNota.getUf_transp()));
            }
            transportadora.setXEnder(dadosDaNota.getxEnder_transp());
            transportadora.setXMun(dadosDaNota.getxMun_transp());
            transp.setTransporta(transportadora);
            volume.setQVol(dadosDaNota.getQVol());
            volume.setEsp(dadosDaNota.getEsp());
            volume.setMarca(dadosDaNota.getMarca());
            volume.setNVol(dadosDaNota.getnVol());
            volume.setPesoL(dadosDaNota.getPesoL());
            volume.setPesoB(dadosDaNota.getPesoB());
            transp.getVol().add(volume);
            if (!dadosDaNota.getIde_IdDest().equals("2")) {
                veiculo.setPlaca(dadosDaNota.getVeiculo_placa());
                veiculo.setUF(TUf.valueOf(dadosDaNota.getEmit_UF()));
                transp.setVeicTransp(veiculo);
            }

        }
        return transp;
    }

    private List<TNFe.InfNFe.Det> montaDet(int i, DadosNfeDto dadosDaNota, NotasItens notasItens) {
        TNFe.InfNFe.Det det = new TNFe.InfNFe.Det();
        det.setNItem(Integer.toString(i + 1));
        det.setProd(montaProduto(notasItens));
        det.setImposto(montaImposto(dadosDaNota, notasItens));
//        det.setObsItem();
        return Collections.singletonList(det);
    }

    private TNFe.InfNFe.Det.Imposto montaImposto(DadosNfeDto dadosDaNota, NotasItens notasItens) {
        TNFe.InfNFe.Det.Imposto imposto = new TNFe.InfNFe.Det.Imposto();
        ObjectFactory objectFactory = new ObjectFactory();
        criaImpostoIcms(imposto, dadosDaNota, notasItens);
        criaImpostoPis(imposto, dadosDaNota, notasItens);
        criaImpostoCofins(imposto, dadosDaNota, notasItens);
        montaImpostoSeletivo(imposto, dadosDaNota, notasItens);
        montaImpostoIbsCbs(imposto, dadosDaNota, notasItens);
        return imposto;
    }

    private void montaImpostoIbsCbs(TNFe.InfNFe.Det.Imposto imposto, DadosNfeDto dadosDaNota, NotasItens notasItens) {
        extractedIbsCbs(imposto, dadosDaNota, notasItens);
    }


    public void extractedIbsCbs(TNFe.InfNFe.Det.Imposto imposto, DadosNfeDto dadosDaNota, NotasItens notasItens) {
//        String vbc = dadosDaNota.getValorBaseVBC().substring(3).replace(",", ".");

        try {
            TTribNFe impostos = new TTribNFe();
            TMonofasia gMono = new TMonofasia();

            impostos.setCST("000");
            impostos.setCClassTrib("000001");

            boolean includeDif = impostos.getCST().equals("510") || impostos.getCST().equals("515");
            boolean includeRed = impostos.getCST().equals("200") || impostos.getCST().equals("515");
            boolean includeMono = impostos.getCST().equals("620");
            boolean includeTrib = !impostos.getCST().equals("000");

            if (includeMono) {
                impostos.setGIBSCBSMono(gMono);
            }

            String vbc = formatToTwoDecimals(notasItens.getProd_VProd());
            Double vCbs = (Double.parseDouble(vbc) * 0.9) / 100;
            Double vIbs = (Double.parseDouble(vbc) * 0.1) / 100;
            TCIBS gibscbs = new TCIBS();

            TCIBS.GIBSMun gibsMun = new TCIBS.GIBSMun();
            TCIBS.GIBSUF gibsuf = new TCIBS.GIBSUF();
            TTribRegular tributoCBSRegular = new TTribRegular();
            TCredPres tCredPres = new TCredPres();

            TDif tDif = new TDif();
            tDif.setPDif("0.00");
            tDif.setVDif("0.00");

            TDevTrib tDevTrib = new TDevTrib();
            tDevTrib.setVDevTrib("0.00");

            TRed tRed = new TRed();
            tRed.setPRedAliq("0.00");
            tRed.setPAliqEfet("0.00");

            gibsuf.setPIBSUF("0.10");
            if (includeRed) gibsuf.setGRed(tRed);
            if (includeDif) gibsuf.setGDif(tDif);
            if (includeTrib) gibsuf.setGDevTrib(tDevTrib);
//          gibsuf.setVIBSUF(String.format(Locale.US, "%.4f", vIbs));
            gibsuf.setVIBSUF(BigDecimal.valueOf(vIbs).setScale(2, RoundingMode.HALF_UP).toPlainString());

            gibsMun.setPIBSMun("0.00");
            if (includeRed) gibsMun.setGRed(tRed);
            if (includeDif) gibsMun.setGDif(tDif);
            gibsMun.setGDevTrib(tDevTrib);
            gibsMun.setVIBSMun("0.00");

            tributoCBSRegular.setCSTReg("000");
            tributoCBSRegular.setCClassTribReg("000001");
            tributoCBSRegular.setPAliqEfetRegIBSUF("0.00");
            tributoCBSRegular.setVTribRegIBSUF("0.00");
            tributoCBSRegular.setPAliqEfetRegIBSMun("0.00");
            tributoCBSRegular.setVTribRegIBSMun("0.00");
            tributoCBSRegular.setPAliqEfetRegCBS("0.00");
            tributoCBSRegular.setVTribRegCBS("0.00");

            gibscbs.setVBC(formatToTwoDecimals(notasItens.getProd_VProd()));
//          gibscbs.setVIBS(String.format(Locale.US, "%.4f", vIbs));
            gibscbs.setVIBS(BigDecimal.valueOf(vIbs).setScale(2, RoundingMode.HALF_UP).toPlainString());
            gibscbs.setGIBSMun(gibsMun);
            gibscbs.setGIBSUF(gibsuf);
            if (includeTrib) gibscbs.setGTribRegular(tributoCBSRegular);

            // gibscbs.setGCBSCredPres();

            TCIBS.GCBS gCBS = new TCIBS.GCBS();
            gCBS.setPCBS("0.9000");
            if (includeRed) gCBS.setGRed(tRed);
            if (includeDif) gCBS.setGDif(tDif);
            gCBS.setGDevTrib(tDevTrib);
//          gCBS.setVCBS(String.format(Locale.US, "%.4f", vCbs));
            gCBS.setVCBS(BigDecimal.valueOf(vCbs).setScale(2, RoundingMode.HALF_UP).toPlainString());
            gibscbs.setGCBS(gCBS);
            tCredPres.setPCredPres("0.00");
            tCredPres.setVCredPres("0.00");
//            tCredPres.setCCredPres("0.00");
            tCredPres.setVCredPresCondSus("0.00");

            impostos.setGIBSCBS(gibscbs);
            imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoIBSCBS(impostos));
        } catch (Exception e) {
            System.out.println("Error extractedIbsCbs => " + e.getMessage());
        }

    }

    private void montaImpostoSeletivo(TNFe.InfNFe.Det.Imposto imposto, DadosNfeDto dadosDaNota, NotasItens notasItens) {
        TIS tis = new TIS();
        Double value = Double.parseDouble(formatToTwoDecimals(notasItens.getProd_VProd())) * 0.5 / 100;
        tis.setCSTIS("000");
        tis.setCClassTribIS("000000");
        tis.setVBCIS(formatToTwoDecimals(notasItens.getProd_VProd()));

        tis.setUTrib(notasItens.getProd_UTrib());
        Double myQTrib = Double.parseDouble(notasItens.getProd_QTrib());
        if (tis.getCSTIS().equals("000")) {
            tis.setQTrib(String.format(Locale.US, "%.4f", myQTrib));
            tis.setPIS("0.00");
            tis.setPISEspec("0.00");
            tis.setVIS("0.00");
        } else {
            tis.setQTrib(String.format(Locale.US, "%.4f", myQTrib));
            tis.setVIS(BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).toPlainString());
            tis.setPIS("0.5");
            tis.setPISEspec("0.5");
        }

        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoIS(tis));
    }

    private void criaImpostoPis(TNFe.InfNFe.Det.Imposto imposto, DadosNfeDto dadosDaNota, NotasItens notasItens) {
        TNFe.InfNFe.Det.Imposto.PIS pis = new TNFe.InfNFe.Det.Imposto.PIS();
        TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAliq = new TNFe.InfNFe.Det.Imposto.PIS.PISAliq();
        TNFe.InfNFe.Det.Imposto.PIS.PISOutr pisOutr = new TNFe.InfNFe.Det.Imposto.PIS.PISOutr();
//		pisAliq.setCST("01");
        if (!notasItens.getProd_CST_Pis().isEmpty() && !notasItens.getProd_CST_Pis().isBlank()) {
            pisAliq.setCST(notasItens.getProd_CST_Pis());
        } else {
            pisAliq.setCST("01");
        }
        if (notasItens.getProd_CST_Pis().equals("99") || notasItens.getProd_CST_Pis().equals("90")) {
            pisOutr.setCST(notasItens.getProd_CST_Pis());
            pisOutr.setVBC(formatToTwoDecimals(notasItens.getProd_VProd()));
            pisOutr.setPPIS(notasItens.getProd_PPIS());
            pisOutr.setVPIS(formatToTwoDecimals(notasItens.getProd_VPIS()));
            pis.setPISOutr(pisOutr);
        } else {
            pisAliq.setCST(notasItens.getProd_CST_Pis());
            pisAliq.setVBC(formatToTwoDecimals(notasItens.getProd_VProd()));
            pisAliq.setPPIS(notasItens.getProd_PPIS());
            pisAliq.setVPIS(formatToTwoDecimals(notasItens.getProd_VPIS()));
            pis.setPISAliq(pisAliq);
        }
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoPIS(pis));
    }

    private void criaImpostoCofins(TNFe.InfNFe.Det.Imposto imposto, DadosNfeDto dadosDaNota, NotasItens notasItens) {
        TNFe.InfNFe.Det.Imposto.COFINS cofins = new TNFe.InfNFe.Det.Imposto.COFINS();
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq cofinsAliq = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq();
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSOutr cofinsOutr = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSOutr();

        cofinsOutr.setCST(notasItens.getProd_CST_Cofins());
        cofinsOutr.setPCOFINS(notasItens.getCofins().replace(",", "."));
        cofinsOutr.setVBC(formatToTwoDecimals(notasItens.getProd_VProd()));
        cofinsOutr.setVCOFINS(formatToTwoDecimals(notasItens.getProd_VCOFINS()));

        cofinsAliq.setCST(notasItens.getProd_CST_Cofins());
        cofinsAliq.setVBC(formatToTwoDecimals(notasItens.getProd_VProd()));
        cofinsAliq.setPCOFINS(notasItens.getCofins().replace(",", "."));
        cofinsAliq.setVCOFINS(formatToTwoDecimals(notasItens.getProd_VCOFINS()));
        if (notasItens.getProd_CST_Cofins().equals("99") || notasItens.getProd_CST_Cofins().equals("90")) {
            cofins.setCOFINSOutr(cofinsOutr);
        } else {
            cofins.setCOFINSAliq(cofinsAliq);
        }

        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoCOFINS(cofins));
    }

    private void criaImpostoIcms(TNFe.InfNFe.Det.Imposto imposto, DadosNfeDto dadosDaNota, NotasItens produto) {
        TNFe.InfNFe.Det.Imposto.ICMS icms = new TNFe.InfNFe.Det.Imposto.ICMS();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 icms00 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS00();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMS90 icms90 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS90();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMS51 icms51 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS51();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMS60 icms60 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS60();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN900 icmssn900 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN900();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN101 icmssn101 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN101();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN102 icmssn102 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN102();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN201 icmssn201 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN201();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN202 icmssn202 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN202();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN500 icmssn500 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN500();

        if (Objects.equals(produto.getCst_csosn(), "900")) {
            icmssn900.setOrig(produto.getProd_orig());
            icmssn900.setCSOSN(produto.getCst_csosn());
            icmssn900.setModBC(produto.getProd_modBC());
            icmssn900.setVBC(formatToTwoDecimals(produto.getProd_VProd()));
            icmssn900.setPICMS(produto.getProd_PICMS());
            System.out.println(produto.getProd_VICMS() + " <= VICMS");
            icmssn900.setVICMS(formatToTwoDecimals(produto.getProd_VICMS()));
            icms.setICMSSN900(icmssn900);
        } else if (Objects.equals(produto.getCst_csosn(), "60")) {
            icms60.setOrig(produto.getProd_orig());
            icms60.setCST("60");
            icms60.setVBCSTRet("0.00");
            icms60.setPST("0.00");
            icms60.setVICMSSTRet("0.00");
            icms.setICMS60(icms60);
        }
        else if (Objects.equals(produto.getCst_csosn(), "90")) {
            icms90.setOrig(produto.getProd_orig());
            icms90.setModBC(produto.getProd_modBC());
            icms90.setVBC(formatToTwoDecimals(produto.getProd_VProd()));
            icms90.setPICMS(produto.getProd_PICMS());
            icms90.setVICMS(formatToTwoDecimals(produto.getProd_VICMS()));
            icms.setICMS90(icms90);
        } else if (Objects.equals(produto.getCst_csosn(), "101")) {
            icmssn101.setOrig(produto.getProd_orig());
            icmssn101.setCSOSN(produto.getCst_csosn());
            icms.setICMSSN101(icmssn101);
        } else if (Objects.equals(produto.getCst_csosn(), "102")) {
            icmssn102.setOrig(produto.getProd_orig());
            icmssn102.setCSOSN(produto.getCst_csosn());
            icms.setICMSSN102(icmssn102);
        } else if (Objects.equals(produto.getCst_csosn(), "201")) {
            icmssn201.setOrig(produto.getProd_orig());
            icmssn201.setCSOSN(produto.getCst_csosn());
            icmssn201.setModBCST(produto.getProd_modBC());
            icmssn201.setVBCST(formatToTwoDecimals(produto.getProd_VBCIcms())); // VWERIFICAR
            icmssn201.setPFCPST(produto.getProd_pFCP());
            icmssn201.setVFCPST(formatToTwoDecimals(produto.getProd_vFCP()));
            icmssn201.setPCredSN(produto.getProd_pCredSN());
            icmssn201.setVCredICMSSN(formatToTwoDecimals(produto.getProd_vCredICMSSN()));
            icms.setICMSSN201(icmssn201);
        } else if (Objects.equals(produto.getCst_csosn(), "202")) {
            icmssn202.setOrig(produto.getProd_orig());
            icmssn202.setCSOSN(produto.getCst_csosn());
            icmssn202.setModBCST(produto.getProd_modBC());
            icmssn202.setVBCST(formatToTwoDecimals(produto.getProd_VBCIcms())); // VWERIFICAR
            icmssn202.setPFCPST(produto.getProd_pFCP());
            icmssn202.setVFCPST(formatToTwoDecimals(produto.getProd_vFCP()));
            icms.setICMSSN202(icmssn202);
        } else if (Objects.equals(produto.getCst_csosn(), "500")) {
            icmssn500.setOrig(produto.getProd_orig());
            icmssn500.setCSOSN(produto.getCst_csosn());
            icms.setICMSSN500(icmssn500);
        } else {
            icms00.setOrig(produto.getProd_orig());
            icms00.setModBC(produto.getProd_modBC());
            icms00.setCST(produto.getProd_CST());
            icms00.setVBC(formatToTwoDecimals(produto.getProd_VProd()));
            icms00.setPICMS(produto.getProd_PICMS());
            icms00.setVICMS(formatToTwoDecimals(produto.getProd_VICMS()));
            icms.setICMS00(icms00);
        }
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoICMS(icms));
    }

    private TNFe.InfNFe.Det.Prod montaProduto(NotasItens myproduto) {
        TNFe.InfNFe.Det.Prod produto = getProduto(myproduto);
        return produto;
    }

    private TNFe.InfNFe.Det.Prod getProduto(NotasItens myproduto) {
        TNFe.InfNFe.Det.Prod produto = new TNFe.InfNFe.Det.Prod();
        produto.setCProd(myproduto.getProd_CProd());
        produto.setCEAN(myproduto.getProd_CEAN());
        produto.setXProd(myproduto.getProd_XProd());
        produto.setNCM(myproduto.getProd_NCM().replace(".", ""));
        produto.setCEST(myproduto.getProd_CEST().replace(".", ""));
        produto.setIndEscala(myproduto.getProd_IndEscala());
        produto.setCFOP(myproduto.getProd_CFOP());
        produto.setUCom(myproduto.getProd_UCom());
        produto.setQCom(String.valueOf(myproduto.getProd_QCom()));
        produto.setVUnCom(String.format(Locale.US, "%.2f", myproduto.getProd_VUnCom()));
        produto.setVProd(formatToTwoDecimals(myproduto.getProd_VProd()));
        produto.setCEANTrib(myproduto.getProd_CEAN());
//		produto.setCEANTrib(myproduto.getProd_CEANTrib());
        produto.setQTrib(myproduto.getProd_QTrib());
        produto.setUTrib(myproduto.getProd_UTrib());
        produto.setVUnTrib(myproduto.getProd_VUnTrib());
        produto.setIndTot(myproduto.getProd_IndTot());
        produto.setUTrib(myproduto.getProd_UTrib());
        String quantidadeTributada = formatToTwoDecimals(myproduto.getProd_QTrib());
        produto.setQTrib(myproduto.getProd_QTrib());
        if (myproduto.getProd_vFrete() != null && !myproduto.getProd_vFrete().isEmpty() && !myproduto.getProd_vFrete().equals("0.00")) {
            produto.setVFrete(formatToTwoDecimals(myproduto.getProd_vFrete()));
        }

//        String descontoFormatado = formatToTwoDecimals(myproduto.getProd_vDesc());
        String descontoFormatado = myproduto.getProd_vDesc() != null ? formatToTwoDecimals(myproduto.getProd_vDesc()) : formatToTwoDecimals("0.00");
        if (!"0.00".equals(descontoFormatado)) {
            logger.info(descontoFormatado + " <= DESCONTO");
            produto.setVDesc(descontoFormatado);
        }
        return produto;
    }

    private String formatToTwoDecimals(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0.00";
        }
        String normalizedValue = value.trim().replace(",", ".");
        try {
            return new BigDecimal(normalizedValue).setScale(2, RoundingMode.HALF_UP).toPlainString();
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    Optional<NotasStatus> getNotasStatus(int numero, String pathPdf, String retornosefaz, String msgRetornoSefaz, String pathXml) {
        Optional<NotasStatus> objStatus = Optional.of(new NotasStatus());
        objStatus.orElseThrow(null).setNumber(numero);
        objStatus.orElseThrow(null).setPathPdf(pathPdf);
        objStatus.orElseThrow(null).setStatus(retornosefaz);
        objStatus.orElseThrow(null).setMsgstatus(msgRetornoSefaz);
        objStatus.orElseThrow(null).setPathXml(pathXml);
        return objStatus;
    }

    private static class EmissaoContext {
        private final String cnpj;
        private final String modelo;
        private final String natOp;
        private final String tipoEmissao;
        private final String cnf;
        private final int serie;
        private final int numero;
        private final LocalDateTime dataEmissao;
        private ConfiguracoesNfe configuracoesNfe;
        private ChaveUtil chaveUtil;
        private double vBCIBSCBSTot;

        private EmissaoContext(String cnpj, String modelo, String natOp, String tipoEmissao, String cnf, int serie, int numero, LocalDateTime dataEmissao) {
            this.cnpj = cnpj;
            this.modelo = modelo;
            this.natOp = natOp;
            this.tipoEmissao = tipoEmissao;
            this.cnf = cnf;
            this.serie = serie;
            this.numero = numero;
            this.dataEmissao = dataEmissao;
        }
    }
}
