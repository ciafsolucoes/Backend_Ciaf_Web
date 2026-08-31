package br.com.ciaf.admin.controllers.fiscal;

import br.com.ciaf.admin.dtos.fiscal.DadosNfeDto;
import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.entities.fiscal.*;
import br.com.ciaf.admin.master.repositories.MasterEmitenteRepository;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import br.com.ciaf.admin.repositories.NfceRepository;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.impressao.model.Impressao;
import br.com.swconsultoria.impressao.service.ImpressaoService;
import br.com.swconsultoria.impressao.util.ImpressaoUtil;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.*;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schemas.*;
import br.com.swconsultoria.nfe.util.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/emitnfce")
public class EmitNfce {
    private static int numeroNFCe;
    private static String dataFormatada;
    private static String myxml;
    private static String pathXml;
    private static String pathPdf;
    private static String returnStatus;
    private static String returnToMessage;
    private static LocalDateTime dataEmissao;
    private static Double vBCIBSCBSTot;
    private final NfceRepository repository;

    private final MasterEmitenteRepository masterEmitenteRepository;

    public EmitNfce(NfceRepository repository,  MasterEmitenteRepository masterEmitenteRepository) {
        this.repository = repository;
        this.masterEmitenteRepository = masterEmitenteRepository;

    }

    @CrossOrigin
    @PostMapping("/{emitente_id}")
    public Optional<NotasStatus> emitirNfce(@PathVariable Long emitente_id, @RequestBody DadosNFCe dadosDaNota) {
        Emitente emitente = masterEmitenteRepository.findById(emitente_id).orElseThrow();
        String password = masterEmitenteRepository.getPassword(emitente_id);
        if (password != null &&
                !password.isEmpty() &&
                emitente.getEmit_cnpj() != null &&
                emitente.getEmit_UF() != null &&
                !emitente.getEmit_UF().isEmpty() &&
                !emitente.getEmit_cnpj().isEmpty()
        ) {
            try {
                numeroNFCe = dadosDaNota.getNumero();
                String cnpj = emitente.getEmit_cnpj();
                if(DadosNFCe.getDataEmissao() != null){
                    dataEmissao = DadosNFCe.getDataEmissao();
                }else {
                    dataEmissao = LocalDateTime.now();
                }
                //Informe a data de Emissao da NFCe
                DateTimeFormatter formato_data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataFormatada = dataEmissao.format(formato_data);

                String cnf = ChaveUtil.completarComZerosAEsquerda(String.valueOf(numeroNFCe), 8);
                String modelo = DocumentoEnum.NFCE.getModelo();
                //Informe a Serie da NFCe
                int serie = 1;
                //Informe o tipo de Emissao da NFCe
                String tipoEmissao = "1";
                //Informe o idToken
                String idToken = "000001";;
                //Informe o CSC da NFCe
                String csc = "dcf51804d78a70ae25f9a6261f6685be";
                String certPath = "certificado_" + emitente.getId() + ".pfx";
                ConfiguracoesNfe config = criarConfiguracoes(password, certPath, emitente.getEmit_UF(), emitente.getEmit_cnpj());
                // MontaChave a NFCe
                ChaveUtil chaveUtil = new ChaveUtil(config.getEstado(), cnpj, modelo, serie, numeroNFCe, tipoEmissao, cnf, dataEmissao);
                String chave = chaveUtil.getChaveNF();
                String cdv = chaveUtil.getDigitoVerificador();
                TNFe.InfNFe infNFe = new TNFe.InfNFe();
                infNFe.setId(chave);
                infNFe.setVersao(ConstantesUtil.VERSAO.NFE);

                //Preenche IDE
                infNFe.setIde(preencheIde(config, cnf, numeroNFCe, tipoEmissao, modelo, serie, cdv, dataEmissao, dadosDaNota));
                //Preenche Emitente
                infNFe.setEmit(preencheEmitente(config, emitente, dadosDaNota));
                //Preenche o Destinatario
                if (checkOnlyIfNull(dadosDaNota.getDest_CNPJ()) || checkOnlyIfNull(dadosDaNota.getDest_CPF())){
                    infNFe.setDest(preencheDestinatario(dadosDaNota, cnpj));
                }
                //Preenche os dados do Produto da NFCe e adiciona a Lista
//            infNFe.getDet().addAll(preencheDet());
                vBCIBSCBSTot = 0.0;
                List<NfceItems> produtos = dadosDaNota.getItems();
                for (int i = 0; i < produtos.size(); i++) {
                    infNFe.getDet().addAll(preencheDet(produtos.get(i), i, dadosDaNota));
                    Double myValue = Double.valueOf(produtos.get(i).getProd_VProd());
                    vBCIBSCBSTot += myValue;
                }
                //Preenche totais da NFCe
                infNFe.setTotal(preencheTotal(dadosDaNota));
                //Preenche os dados de Transporte
                infNFe.setTransp(preencheTransporte());
                // Preenche dados Pagamento
                infNFe.setPag(preenchePag(dadosDaNota));
                TNFe nfe = new TNFe();
                nfe.setInfNFe(infNFe);
                TEnviNFe enviNFe = new TEnviNFe();
                enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
                enviNFe.setIdLote("1");
                enviNFe.setIndSinc("1");
                enviNFe.getNFe().add(nfe);

                // Monta e Assina o XML
                pathXml = "/var/www/ciafonline.com.br/xmls/" +
                        emitente.getId() + "NFCe_" +  numeroNFCe +".xml";

                pathPdf = "NFCe_"+ emitente_id + "_" + numeroNFCe +".pdf";
//                myxml = XmlNfeUtil.objectToXml(enviNFe);
//                convertStringToXMLDocument(myxml, id);
                enviNFe = Nfe.montaNfe(config, enviNFe, true);
                myxml = XmlNfeUtil.objectToXml(enviNFe);

                //Monta QRCode
                String qrCode = preencheQRCode(enviNFe,config,idToken,csc);

                TNFe.InfNFeSupl infNFeSupl = new TNFe.InfNFeSupl();
                infNFeSupl.setQrCode(qrCode);
                infNFeSupl.setUrlChave(WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_CONSULTANFCE));
                enviNFe.getNFe().get(0).setInfNFeSupl(infNFeSupl);

                // Envia a Nfe para a Sefaz
                TRetEnviNFe retorno = Nfe.enviarNfe(config, enviNFe, DocumentoEnum.NFCE);
                String xmlFinal;
                returnStatus = retorno.getProtNFe().getInfProt().getCStat();
                returnToMessage = retorno.getProtNFe().getInfProt().getXMotivo();
                if(returnStatus.equals("100")) {
                    NotasFiscaisConsumidor updateInvoice = repository.findByNumber(numeroNFCe);
                    updateInvoice.setStatus("AUTORIZADA");
                    updateInvoice.setChave(chaveUtil.getChaveNF());
                    updateInvoice.setPath(pathPdf);
                    updateInvoice.setData(dataFormatada);
                    repository.save(updateInvoice);
//                efetuarImpressaoNFe(xmlFinal, qrCode, dadosDaNota);
                    RetornoUtil.validaSincrono(retorno);
                    System.out.println();
                    returnStatus = retorno.getProtNFe().getInfProt().getCStat();
                }else{
                    System.out.println("# Status: " + retorno.getProtNFe().getInfProt().getCStat() + " - "
                            + retorno.getProtNFe().getInfProt().getXMotivo());
                }
                xmlFinal = XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe());
                convertStringToXMLDocument(xmlFinal, emitente_id);
                //Valida se o Retorno é Assincrono
                if (RetornoUtil.isRetornoAssincrono(retorno)) {
                    //Pega o Recibo
                    String recibo = retorno.getInfRec().getNRec();
                    int tentativa = 0;
                    TRetConsReciNFe retornoNfe = null;

                    //Define Numero de tentativas que irá tentar a Consulta
                    while (tentativa < 15) {
                        retornoNfe = Nfe.consultaRecibo(config, recibo, DocumentoEnum.NFE);
                        if (retornoNfe.getCStat().equals(StatusEnum.LOTE_EM_PROCESSAMENTO.getCodigo())) {
                            Thread.sleep(1000);
                            tentativa++;
                        } else {
                            break;
                        }
                    }

                    RetornoUtil.validaAssincrono(retornoNfe);
                    System.out.println();
                    System.out.println("# Status: " + retornoNfe.getProtNFe().get(0).getInfProt().getCStat() + " - " + retornoNfe.getProtNFe().get(0).getInfProt().getXMotivo());
                    System.out.println("# Protocolo: " + retornoNfe.getProtNFe().get(0).getInfProt().getNProt());
                    System.out.println("# XML Final: " + XmlNfeUtil.criaNfeProc(enviNFe, retornoNfe.getProtNFe().get(0)));

                } else {
                    //Se for else o Retorno é Sincrono
                    //Valida Retorno Sincrono
                    RetornoUtil.validaSincrono(retorno);
                    System.out.println();
                    System.out.println("# Status: " + retorno.getProtNFe().getInfProt().getCStat() + " - " + retorno.getProtNFe().getInfProt().getXMotivo());
                    System.out.println("# Protocolo: " + retorno.getProtNFe().getInfProt().getNProt());
                    System.out.println("# Xml Final :" + XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe()));
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                returnStatus = "500 - Erro no Servidor- " + e.getMessage();
                returnToMessage = "Erro ao emitir NFC-e - " + e.getMessage();
                e.printStackTrace();
            }
        }else {
            System.out.println("faltou um parâmetro fundamental: do certificado ou emitente");
        }

        NotasStatus notasStatus = new NotasStatus();
        notasStatus.setMsgstatus( "Sucesso");
//        return notasStatus;
        return getNotasStatus(numeroNFCe, pathPdf, returnStatus, returnToMessage, pathXml);
    }


//    private ConfiguracoesNfe criarConfiguracoes(String password, String certPath) throws FileNotFoundException, CertificadoException {
//        Certificado certificado = CertificadoService.certificadoPfx(
//                "/home/gilvan/ciaf/certificados/"+certPath, password);
//        return ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.MG, AmbienteEnum.HOMOLOGACAO, certificado,
//                "/home/gilvan/ciaf/schemas");
//    }
private ConfiguracoesNfe criarConfiguracoes(String password, String certPath, String estado, String emitente) throws FileNotFoundException, CertificadoException {
    String env = "PRODUCAO";
    if(emitente.equals("5182162520022")) env = "HOMOLOGACAO";
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
    private static void convertStringToXMLDocument(String myxml2, Long id) {
        // Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // API to obtain DOM Document instance
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            // Create DocumentBuilder with default configuracoesNfeuration
            builder = factory.newDocumentBuilder();

            // Parse the content to Document object
            doc = builder.parse(new InputSource(new StringReader(myxml2)));

            //salvando o xml com nome destinatário e data de emissão
            try (FileOutputStream output = new FileOutputStream(
                    "/var/www/ciafonline.com.br/xmls/" +
                           "NFCe_"+ id + "_" + numeroNFCe +".xml")) {
                writeXml(doc, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //escrita do xml
    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);

    }
    private static void efetuarImpressaoNFe(String xmlFinal, String urlNota, DadosNFCe dadosDaNota)
            throws JRException, ParserConfigurationException, IOException, SAXException {

        // Aqui está pegando o Layout Padrão
        Impressao impressao = ImpressaoUtil.impressaoPadraoNFCe(xmlFinal, urlNota);

        // Faz a impressão em pdf File passando o caminho do Arquivo
        ImpressaoService.impressaoPdfArquivo(
                impressao,
                "/var/www/ciafonline.com.br/danfes/"+
                        numeroNFCe + "_" +
                        dataEmissao.toString().substring(0, 19)+".pdf");
    }
    private static TNFe.InfNFe.Ide preencheIde(ConfiguracoesNfe config, String cnf, int numeroNFCe, String tipoEmissao, String modelo, int serie, String cDv, LocalDateTime dataEmissao, DadosNFCe dados) throws NfeException {
        TNFe.InfNFe.Ide ide = new TNFe.InfNFe.Ide();
        ide.setCUF(config.getEstado().getCodigoUF());
        ide.setCNF(cnf);
        ide.setNatOp("NOTA FISCAL CONSUMIDOR ELETRONICA");
        ide.setMod(modelo);
        ide.setSerie(String.valueOf(serie));
        ide.setNNF(String.valueOf(numeroNFCe));
        ide.setDhEmi(XmlNfeUtil.dataNfe(dataEmissao));
        ide.setTpNF("1");
        ide.setIdDest("1");
        ide.setCMunFG(dados.getEmit_CMun());
        ide.setTpImp("4");
        ide.setTpEmis(tipoEmissao);
        ide.setCDV(cDv);
        ide.setTpAmb(config.getAmbiente().getCodigo());
//        ide.setFinNFe("1");
        if(dados.getFinNFe().equals("")){
            ide.setFinNFe("1");
        }else{
            ide.setFinNFe(dados.getFinNFe());
        }

//        ide.setIndFinal("1");
        ide.setIndFinal(dados.getIndFinal());
//        ide.setIndPres("1");
        if(dados.getIndPres().isEmpty() || dados.getIndPres() == null){
            ide.setIndPres("1");
        }else{
            ide.setIndPres(dados.getIndPres());
        }

        ide.setProcEmi("0"); // 0 = Emissão de NF-e com aplicativo do contribuinte.
        ide.setVerProc("1.0");
        return ide;
    }
    /**
     * Preenche o Emitente da NFCe
     * @param config
     * @param emitente
     * @return
     */
    private static TNFe.InfNFe.Emit preencheEmitente(ConfiguracoesNfe config, Emitente emitente, DadosNFCe dadosNFCe) {
        TNFe.InfNFe.Emit emit = new TNFe.InfNFe.Emit();
        emit.setCNPJ(emitente.getEmit_cnpj());
        emit.setXNome(emitente.getEmit_XNome());
//        emit.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        emit.setIE(emitente.getEmit_IE());
        emit.setCRT(dadosNFCe.getEmit_CRT());
        TEnderEmi enderecoEmitente = new TEnderEmi();
        enderecoEmitente.setXLgr(dadosNFCe.getEmit_XLgr());
        enderecoEmitente.setNro(dadosNFCe.getEmit_Nro());
        enderecoEmitente.setXCpl(dadosNFCe.getEmit_XCpl());
        enderecoEmitente.setXBairro(dadosNFCe.getEmit_XBairro());
        enderecoEmitente.setCMun(dadosNFCe.getEmit_CMun());
        enderecoEmitente.setXMun(dadosNFCe.getEmit_XMun());
        enderecoEmitente.setUF(TUfEmi.valueOf(config.getEstado().toString()));
        if (dadosNFCe.getEmit_CEP().contains(".") || dadosNFCe.getEmit_CEP().contains("-")){
            String myCep = dadosNFCe.getEmit_CEP();
            myCep = myCep.replace(".", "");
            myCep = myCep.replace("-", "");
            enderecoEmitente.setCEP(myCep);
        }else {
            enderecoEmitente.setCEP(dadosNFCe.getEmit_CEP());
        }
        emit.setEnderEmit(enderecoEmitente);
        return emit;
    }
    /**
     * Preenche o Destinatario da NFCe
     * @return
     */
    private static TNFe.InfNFe.Dest preencheDestinatario(DadosNFCe dadosDaNota, String cnpj) {
        TNFe.InfNFe.Dest dest = new TNFe.InfNFe.Dest();
        if(dadosDaNota.getDest_CPF() != null && !dadosDaNota.getDest_CPF().isEmpty() && !dadosDaNota.getDest_CPF().isBlank()){
            dest.setCPF(dadosDaNota.getDest_CPF());
            if(cnpj.equals("5182162520022")){
                dest.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
            }else{
                dest.setXNome(dadosDaNota.getDest_XNome());
            }
        }
        if(dadosDaNota.getDest_CNPJ() != null && !dadosDaNota.getDest_CNPJ().isEmpty() && !dadosDaNota.getDest_CNPJ().isBlank()){
            dest.setCNPJ(dadosDaNota.getDest_CNPJ());
            if(cnpj.equals("5182162520022")){
                dest.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
            }else{
                dest.setXNome(dadosDaNota.getDest_XNome());
            }
        }

        TEndereco enderDest = new TEndereco();
        if(checkOnlyIfNull(dadosDaNota.getDest_XLgr())){
            enderDest.setXLgr(dadosDaNota.getDest_XLgr());
        }
        if(checkOnlyIfNull(dadosDaNota.getDest_Nro())){
            enderDest.setNro(dadosDaNota.getDest_Nro());
        }
        if(checkOnlyIfNull(dadosDaNota.getDest_XBairro())){
            enderDest.setXBairro(dadosDaNota.getDest_XBairro());
        }
        if(checkOnlyIfNull(dadosDaNota.getDest_CMun()) && checkOnlyIfNull(dadosDaNota.getDest_XLgr())){
            enderDest.setCMun(dadosDaNota.getDest_CMun());
        }
        if(checkOnlyIfNull(dadosDaNota.getDest_XMun())){
            enderDest.setXMun(dadosDaNota.getDest_XMun());
        }

            if (checkOnlyIfNull(dadosDaNota.getDest_UF().toString()) && checkOnlyIfNull(dadosDaNota.getDest_XLgr())) {

                enderDest.setUF(dadosDaNota.getDest_UF());

            }

        if(checkOnlyIfNull(dadosDaNota.getDest_CEP())){
            if (dadosDaNota.getDest_CEP().contains(".") || dadosDaNota.getDest_CEP().contains("-")){
                enderDest.setCEP(dadosDaNota.getDest_CEP().replace("[\\.-]", ""));
            }else {
                enderDest.setCEP(dadosDaNota.getDest_CEP());
            }

        }
        enderDest.setCPais("1058");
        enderDest.setXPais("Brasil");
        if(!checkIfNull(dadosDaNota.getDest_fone()).isEmpty() && !checkIfNull(dadosDaNota.getDest_fone()).isBlank()){
            enderDest.setFone(checkIfNull(dadosDaNota.getDest_fone()));
        }
        if(checkOnlyIfNull(dadosDaNota.getDest_CPF()) || checkOnlyIfNull(dadosDaNota.getDest_CNPJ())) {
            dest.setEnderDest(enderDest);
//            dest.setIndIEDest("9");
            dest.setIndIEDest(dadosDaNota.getDest_IndIEDest());
        }

        if(!checkIfNull(dadosDaNota.getDest_Email()).isBlank() && !checkIfNull(dadosDaNota.getDest_Email()).isEmpty()){
            dest.setEmail(checkIfNull(dadosDaNota.getDest_Email()));
        }
        return dest;
    }

    /**
     * Preenche Det NFCe
     */
    private static List<TNFe.InfNFe.Det> preencheDet(NfceItems produto, int i, DadosNFCe dadosDaNota) {
        // O Preenchimento deve ser feito por produto, Então deve ocorrer uma LIsta
        TNFe.InfNFe.Det det = new TNFe.InfNFe.Det();
        // O numero do Item deve seguir uma sequencia
        det.setNItem(Integer.toString(i + 1));
        // Preenche dados do Produto
        det.setProd(preencheProduto(produto));
        //Preenche dados do Imposto
        det.setImposto(preencheImposto(dadosDaNota, produto));
        //Retorna a Lista de Det
        return Collections.singletonList(det);
    }

    /**
     * Preenche dados do Produto
     * @return
     */
    private static TNFe.InfNFe.Det.Prod preencheProduto(NfceItems produto) {
        TNFe.InfNFe.Det.Prod prod = new TNFe.InfNFe.Det.Prod();
//        prod.setCProd("7898480650104");
        prod.setCProd(produto.getProd_CProd());
        prod.setCEAN(produto.getProd_CEAN());
        prod.setXProd("NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        prod.setNCM(produto.getProd_NCM().replace(".", ""));
        prod.setCEST(produto.getProd_CEST().replace(".", ""));
        prod.setIndEscala("S");
        prod.setCFOP(produto.getProd_CFOP());
        prod.setUCom(produto.getProd_UCom());
        prod.setQCom(String.valueOf(produto.getProd_QCom()));
        prod.setVUnCom(produto.getProd_VUnCom().toString());
        prod.setVProd(produto.getProd_VProd());
        prod.setCEANTrib(produto.getProd_CEAN());
        prod.setUTrib(produto.getProd_UTrib());
        prod.setQTrib(produto.getProd_QTrib());
        if(!produto.getProd_vDesc().equals("0.00")){
            prod.setVDesc(produto.getProd_vDesc());
        }
        prod.setVUnTrib(produto.getProd_VUnTrib());
        prod.setIndTot("1");

        return prod;
    }

    /**
     * Preenche dados do Imposto da NFCe
     * @return
     */
    private static TNFe.InfNFe.Det.Imposto preencheImposto(DadosNFCe dadosNFCe, NfceItems produto) {
        TNFe.InfNFe.Det.Imposto imposto = new TNFe.InfNFe.Det.Imposto();
        TNFe.InfNFe.Det.Imposto.ICMS icms = new TNFe.InfNFe.Det.Imposto.ICMS();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 icms00 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS00();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN900 icmssn900 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN900();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN101 icmssn101 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN101();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN102 icmssn102 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN102();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN201 icmssn201 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN201();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN202 icmssn202 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN202();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN500 icmssn500 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN500();
        if(produto.getCst_csosn() != null && Objects.equals(produto.getCst_csosn(), "900")){
//            icmssn900.setOrig(produto.getProd_orig());/
            icmssn900.setCSOSN("900");
            icmssn900.setModBC(produto.getProd_modBC());
            icmssn900.setVBC(produto.getProd_VProd());
            icmssn900.setPICMS(produto.getProd_PICMS());
            icmssn900.setVICMS(produto.getProd_VICMS());
            icms.setICMSSN900(icmssn900);
        }else if (Objects.equals(produto.getCst_csosn(), "101")) {
            icmssn101.setOrig(produto.getProd_orig());
            icmssn101.setCSOSN(produto.getCst_csosn());
            icms.setICMSSN101(icmssn101);
        } else if (Objects.equals(produto.getCst_csosn(), "102")) {
            icmssn102.setOrig(produto.getProd_orig());
            icmssn102.setCSOSN(produto.getCst_csosn());
            icms.setICMSSN102(icmssn102);
        }else if (Objects.equals(produto.getCst_csosn(), "201")) {
            icmssn201.setOrig(produto.getProd_orig());
            icmssn201.setCSOSN(produto.getCst_csosn());
            icmssn201.setModBCST(produto.getProd_modBC());
            icmssn201.setVBCST(produto.getProd_VBCIcms()); // VWERIFICAR
            icmssn201.setPFCPST(produto.getProd_pFCP());
            icmssn201.setVFCPST(produto.getProd_vFCP());
            icmssn201.setPCredSN(produto.getProd_pCredSN());
            icmssn201.setVCredICMSSN(produto.getProd_vCredICMSSN());
            icms.setICMSSN201(icmssn201);
        }
        else if (Objects.equals(produto.getCst_csosn(), "202")) {
            icmssn202.setOrig(produto.getProd_orig());
            icmssn202.setCSOSN(produto.getCst_csosn());
            icmssn202.setModBCST(produto.getProd_modBC());
            icmssn202.setVBCST(produto.getProd_VBCIcms()); // VWERIFICAR
            icmssn202.setPFCPST(produto.getProd_pFCP());
            icmssn202.setVFCPST(produto.getProd_vFCP());
            icms.setICMSSN202(icmssn202);
        }
        else if (Objects.equals(produto.getCst_csosn(), "500")) {
            icmssn500.setOrig(produto.getProd_orig());
            icmssn500.setCSOSN(produto.getCst_csosn());
            icms.setICMSSN500(icmssn500);
        }
        else {
            icms00.setOrig(produto.getProd_orig());
            icms00.setModBC(produto.getProd_modBC());
            icms00.setCST(produto.getProd_CST());
            icms00.setVBC(produto.getProd_VProd());
            icms00.setPICMS(produto.getProd_PICMS());
            icms00.setVICMS(produto.getProd_VICMS());
            icms.setICMS00(icms00);
        }

        TNFe.InfNFe.Det.Imposto.PIS pis = new TNFe.InfNFe.Det.Imposto.PIS();
        TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAliq = new TNFe.InfNFe.Det.Imposto.PIS.PISAliq();
        TNFe.InfNFe.Det.Imposto.PIS.PISOutr pisOutr = new TNFe.InfNFe.Det.Imposto.PIS.PISOutr();
        if(produto.getProd_CST_Pis().equals("99")){
            pisOutr.setCST("99");
            pisOutr.setVBC(produto.getProd_vBC_Pis());
            pisOutr.setPPIS(produto.getProd_PPIS());
            pisOutr.setVPIS(produto.getProd_VCOFINS());
            pis.setPISOutr(pisOutr);
        }else{
            pisAliq.setCST("01");
            pisAliq.setVBC(produto.getProd_VProd());
            pisAliq.setPPIS(produto.getProd_PPIS());
            pisAliq.setVPIS(produto.getProd_VPIS());
            pis.setPISAliq(pisAliq);
        }

        TNFe.InfNFe.Det.Imposto.COFINS cofins = new TNFe.InfNFe.Det.Imposto.COFINS();
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq cofinsAliq = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq();
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSOutr cofinsOutr =  new TNFe.InfNFe.Det.Imposto.COFINS.COFINSOutr();
        if (produto.getProd_CST_Cofins().equals("99")){
            cofinsOutr.setCST("99");
            cofinsOutr.setVBC(produto.getProd_vBC_confins());
            cofinsOutr.setPCOFINS(produto.getCofins());
            cofinsOutr.setVCOFINS(produto.getProd_VCOFINS());
            cofins.setCOFINSOutr(cofinsOutr);
        }else{
            cofinsAliq.setCST("01");
            cofinsAliq.setVBC(produto.getProd_vBC_confins());
            cofinsAliq.setPCOFINS(produto.getCofins());
            cofinsAliq.setVCOFINS(produto.getProd_VCOFINS());
            cofins.setCOFINSAliq(cofinsAliq);
        }
        JAXBElement<TNFe.InfNFe.Det.Imposto.ICMS> icmsElement = new JAXBElement<TNFe.InfNFe.Det.Imposto.ICMS>(new QName("ICMS"), TNFe.InfNFe.Det.Imposto.ICMS.class, icms);
        imposto.getContent().add(icmsElement);

        JAXBElement<TNFe.InfNFe.Det.Imposto.PIS> pisElement = new JAXBElement<TNFe.InfNFe.Det.Imposto.PIS>(new QName("PIS"), TNFe.InfNFe.Det.Imposto.PIS.class, pis);
        imposto.getContent().add(pisElement);

        JAXBElement<TNFe.InfNFe.Det.Imposto.COFINS> cofinsElement = new JAXBElement<TNFe.InfNFe.Det.Imposto.COFINS>(new QName("COFINS"), TNFe.InfNFe.Det.Imposto.COFINS.class, cofins);
        imposto.getContent().add(cofinsElement);

        montaImpostoSeletivo(imposto, dadosNFCe, produto);
        montaImpostoIbsCbs(imposto, dadosNFCe, produto);

        return imposto;
    }
    private static void montaImpostoIbsCbs(TNFe.InfNFe.Det.Imposto imposto, DadosNFCe dadosDaNota, NfceItems notasItens){
        String myVBC = "";
        if (dadosDaNota.getValorBaseVBC().charAt(0) == 'R' && dadosDaNota.getValorBaseVBC().charAt(1) == '$'){
            myVBC = dadosDaNota.getValorBaseVBC().substring(2);
            myVBC = myVBC.replace(",", ".");
        }else{
            if (dadosDaNota.getValorBaseVBC().contains(",")){
                myVBC = dadosDaNota.getValorBaseVBC().replace(",", ".");
            }else {
                myVBC = dadosDaNota.getValorBaseVBC();
            }
        }
        try{
            TTribNFe impostos = new TTribNFe();
            TMonofasia gMono = new TMonofasia();

            impostos.setCST("000");
            impostos.setCClassTrib("000001");

            boolean includeDif = impostos.getCST().equals("510") || impostos.getCST().equals("515");
            boolean includeRed = impostos.getCST().equals("200") || impostos.getCST().equals("515");
            boolean includeMono = impostos.getCST().equals("620");
            boolean includeTrib = !impostos.getCST().equals("000");

            if(includeMono) {
                impostos.setGIBSCBSMono(gMono);
            }

            String vbc = notasItens.getProd_VProd();
            Double vCbs = (Double.parseDouble(vbc) * 0.9) / 100;
            Double vIbs = (Double.parseDouble(vbc) * 0.1) / 100;
            TCIBS gibscbs = new TCIBS();
            System.out.println(vCbs + " - " + vIbs);
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
            gibsuf.setVIBSUF(String.format(Locale.US, "%.2f", vIbs));
//            gibsuf.setVIBSUF(BigDecimal.valueOf(vIbs).setScale(2, RoundingMode.HALF_UP).toPlainString());

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

            gibscbs.setVBC(notasItens.getProd_VProd());
//            gibscbs.setVIBS(BigDecimal.valueOf(vIbs).setScale(2, RoundingMode.HALF_UP).toPlainString());
            gibscbs.setVIBS(String.format(Locale.US, "%.2f", vIbs));
            gibscbs.setGIBSMun(gibsMun);
            gibscbs.setGIBSUF(gibsuf);
            if (includeTrib) gibscbs.setGTribRegular(tributoCBSRegular);

            // gibscbs.setGCBSCredPres();

            TCIBS.GCBS gCBS = new TCIBS.GCBS();
            gCBS.setPCBS("0.9000");
            if (includeRed) gCBS.setGRed(tRed);
            if (includeDif) gCBS.setGDif(tDif);
            gCBS.setGDevTrib(tDevTrib);
            gCBS.setVCBS(BigDecimal.valueOf(vCbs).setScale(2, RoundingMode.HALF_UP).toPlainString());
            gibscbs.setGCBS(gCBS);
            tCredPres.setPCredPres("0.00");
//            tCredPres.setPCredPres(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP).toPlainString());
            tCredPres.setVCredPres("0.00");
//            tCredPres.setCCredPres("0.00");
            tCredPres.setVCredPresCondSus("0.00");

            impostos.setGIBSCBS(gibscbs);
            imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoIBSCBS(impostos));
        } catch (Exception e) {
            System.out.println("Error extractedIbsCbs => " + e.getMessage());
        }

    }
    private static void montaImpostoSeletivo(TNFe.InfNFe.Det.Imposto imposto, DadosNFCe dadosDaNota, NfceItems notasItens){
        TIS tis = new TIS();
        System.out.println(notasItens.getProd_VProd());
        Double value = Double.parseDouble(notasItens.getProd_VProd()) * 5 / 100;
        tis.setCSTIS("000");
        tis.setCClassTribIS("000000");
        tis.setVBCIS(notasItens.getProd_VProd());
        Double myQTrib = Double.parseDouble(notasItens.getProd_QTrib());
        tis.setUTrib(notasItens.getProd_UTrib());
        if (tis.getCSTIS().equals("000")) {
            tis.setQTrib(String.format(Locale.US, "%.4f", myQTrib));
            tis.setPIS("0.00");
            tis.setPISEspec("0.00");
            tis.setVIS("0.00");
        } else {
            tis.setQTrib(String.format(Locale.US, "%.4f", myQTrib));
            tis.setVIS(String.valueOf(value));
            tis.setPIS("0.5");
            tis.setPISEspec("0.5");
        }

        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoIS(tis));
    }

    /**
     * Prenche Total NFCe
     * @return
     */
    private static TNFe.InfNFe.Total preencheTotal(DadosNFCe dadosDaNota) {
        TNFe.InfNFe.Total total = new TNFe.InfNFe.Total();
        TNFe.InfNFe.Total.ICMSTot icmstot = new TNFe.InfNFe.Total.ICMSTot();
        TISTot tisTot = new TISTot();
        tisTot.setVIS("0.00");
        icmstot.setVBC(dadosDaNota.getIcmsTot_VBC());
        icmstot.setVICMS(dadosDaNota.getIcmsTot_VICMS());
        icmstot.setVICMSDeson("0.00");
        icmstot.setVFCP(dadosDaNota.getIcmsTot_VFCP());
        icmstot.setVFCPST("0.00");
        icmstot.setVFCPSTRet("0.00");
        icmstot.setVBCST("0.00");
        icmstot.setVST(dadosDaNota.getIcmsTot_VST());
        icmstot.setVProd(dadosDaNota.getIcmsTot_VProd());
        icmstot.setVFrete("0.00");
        icmstot.setVSeg("0.00");
        icmstot.setVDesc(dadosDaNota.getIcmsTot_vDesc());
        icmstot.setVII("0.00");
        icmstot.setVIPI(dadosDaNota.getIcmsTot_VIPI());
        icmstot.setVIPIDevol("0.00");
        icmstot.setVPIS(dadosDaNota.getIcmsTot_VPIS());
        icmstot.setVCOFINS("0.00");
        icmstot.setVOutro("0.00");
        icmstot.setVNF(dadosDaNota.getIcmsTot_VNF());
        total.setICMSTot(icmstot);
        total.setIBSCBSTot(criaTibsCbsMonoTot());
        total.setISTot(tisTot);
        return total;
    }
    public static TIBSCBSMonoTot criaTibsCbsMonoTot(){
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

    /**
     * Preenche Transporte
     * @return
     */
    private static TNFe.InfNFe.Transp preencheTransporte() {
        TNFe.InfNFe.Transp transp = new TNFe.InfNFe.Transp();
        transp.setModFrete("9");
        return transp;
    }
    /**
     * Preenche dados Pagamento
     * @return
     */
    private static TNFe.InfNFe.Pag preenchePag(DadosNFCe dadosDaNota) {
        TNFe.InfNFe.Pag pag = new TNFe.InfNFe.Pag();
        TNFe.InfNFe.Pag.DetPag detPag = new TNFe.InfNFe.Pag.DetPag();
        detPag.setTPag("01");
        detPag.setVPag(dadosDaNota.getDetPag_VPag());
        pag.getDetPag().add(detPag);
//        String strTroco = df.format(dadosDaNota.getvTroco());
        String strTroco = String.format(Locale.ENGLISH,"%.2f", dadosDaNota.getvTroco());
//        System.out.println(dadosDaNota.getvTroco() + " <= vTroco");
//        System.out.println(strTroco + " <= vTroco with strTroco");
        if(dadosDaNota.getvTroco() > 0){
            pag.setVTroco(strTroco);
        }
        return pag;
    }
    /**
     * Preenche QRCode
     * @param enviNFe
     * @param config
     * @param idToken
     * @param csc
     * @return
     * @throws NfeException
     * @throws NoSuchAlgorithmException
     */
    private static String preencheQRCode(TEnviNFe enviNFe, ConfiguracoesNfe config, String idToken, String csc) throws NfeException, NoSuchAlgorithmException {

        //QRCODE EMISAO ONLINE
        return NFCeUtil.getCodeQRCode(
                enviNFe.getNFe().get(0).getInfNFe().getId().substring(3),
                config.getAmbiente().getCodigo(),
                idToken,
                csc,
                WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));

        //QRCODE EMISSAO OFFLINE
//        return NFCeUtil.getCodeQRCodeContingencia(
//                enviNFe.getNFe().get(0).getInfNFe().getId().substring(3),
//                config.getAmbiente().getCodigo(),
//                enviNFe.getNFe().get(0).getInfNFe().getIde().getDhEmi(),
//                enviNFe.getNFe().get(0).getInfNFe().getTotal().getICMSTot().getVNF(),
//                Base64.getEncoder().encodeToString(enviNFe.getNFe().get(0).getSignature().getSignedInfo().getReference().getDigestValue()),
//                idToken,
//                csc,
//                WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));
    }

    private static String checkIfNull(String dado){
        return dado != null ? dado : "";
    }
    private static boolean checkOnlyIfNull(String dado){
        return dado != null && dado.length() > 0 ? true : false;
    }

    static Optional<NotasStatus> getNotasStatus(int numero, String pathPdf, String retornosefaz, String msgRetornoSefaz, String pathXml) {
        Optional<NotasStatus> objStatus = Optional.of(new NotasStatus());
        objStatus.orElseThrow(null).setNumber(numero);
        objStatus.orElseThrow(null).setPathPdf(pathPdf);
        objStatus.orElseThrow(null).setStatus(retornosefaz);
        objStatus.orElseThrow(null).setMsgstatus(msgRetornoSefaz);
        objStatus.orElseThrow(null).setPathXml(pathXml);
        return objStatus;
    }
}
