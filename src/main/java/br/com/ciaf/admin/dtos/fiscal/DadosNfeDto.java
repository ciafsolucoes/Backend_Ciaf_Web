package br.com.ciaf.admin.dtos.fiscal;

import br.com.ciaf.admin.entities.Entrega;
import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.entities.fiscal.NotasItens;

import java.time.LocalDateTime;

public class DadosNfeDto {
    private Long nota_id;
    private Long emitente_id;
    private String modelo; //
    private String natOp; //
    private String tpNF;
    private String tpNFDebito;
    private String tpNFCredito;
    private String tpEmis; //
    private String tpImp; //
    private String idDest; //
    private String cMunFG; //
    private String cMunFGIBS; //
    private String finNFe; //
    private String indFinal; //
    private String indPres; //
    private String indIntermed; //
    private String procEmi;

    private String tpCompraGov;
    private String pRedutor;

    // Grupo UB. Informações dos tributos IBS / CBS e Imposto Seletivo
    private String CSTIS;
    private String cClassTribIS;
    private String vBCIS;
    private String pIS;
    private String pISEspec;
    private String uTrib;
    private String qTrib;
    private String vIS;
    private String CST;

    private String verProc;
    private String tipoEmissao;
    private int numero; //
    private int serie; //
    private static LocalDateTime dataEmissao;
    private static LocalDateTime dataSaida;
    private String valorBaseVBC;
    private String icmsVICMS;
    private String icmsVICMSDeson;
    private String setVProd;
    private String ValorPIS;
    private String ValorCOFINS;
    private String ValorNF;
    private String detPag_Tpag;
    private String detPag_VPag;
    private String transpModFrete;
    private String transpValorFrete;

    private String icmsTot_vII;
    private String icmsTot_VTotTrib;
    private String icmsTot_VNF;
    private String dest_XNome;
    private String dest_IndIEDest;
    private String dest_CPF;
    private String dest_CNPJ;
    private String dest_ie;
    private String dest_Email;
    private String dest_XLgr;
    private String dest_Nro;
    private String dest_XCpl;
    private String dest_XBairro;
    private String dest_CMun;
    private String dest_XMun;
    private String dest_UF;
    private String dest_CEP;
    private String emit_XNome;
    private String emit_CNPJ;
    private String emit_IE;
    private String emit_CRT; //classificação de regime tributário
    private String emit_XLgr;
    private String emit_Nro;
    private String emit_XCpl;
    private String emit_XBairro;
    private String emit_CMun;
    private String emit_XMun;
    private String emit_UF;
    private String emit_CEP;
    private String ide_CUF;
    private String ide_CNF;
    private String ide_NatOp;
    private String ide_Mod;
    private String ide_Serie;
    private String ide_NNF;
    private String ide_DhEmi;
    private String ide_TpNF;
    private String ide_IdDest;
    private String ide_CMunFG;
    private String ide_TpImp;
    private String ide_TpEmis;
    private String ide_CDV;
    private String ide_TpAmb;
    private String ide_FinNFe;
    private String ide_IndFinal;
    private String ide_IndPres;
    private String ide_ProcEmi;
    private String ide_VerProc;
    private String prod_VProd;
    private String UFSaidaPais;
    private String xLocExporta;
    private String modFrete;
    private String nome_transp;
    private String CNPJ_transp;
    private String CPF_transp;
    private String ie_transp;
    private String uf_transp;
    private String veiculo_placa;
    private String veiculo_uf;
    private String xEnder_transp;
    private String xMun_transp;
    private String QVol;
    private String esp;
    private String marca;
    private String nVol;
    private String pesoL;
    private String pesoB;
    private String infAdic;
    private String infAdFisco;
    private String vPag;
    private Double vTroco;

    private String pisAliq_CST;
    private String pisAliq_VBC;
    private String pisAliq_PPIS;
    private String pisAliq_VPIS;
    //    private String cofinsAliqAliq_VCOFINSAliqAliq_PCOFINS;
//    private String cofinsAliqAliq_VCOFINSAliqAliq_VCOFINS;
    private String icmsTot_VBC;
    private String icmsTot_VICMS;
    private String icmsTot_VProd;
    private String icmsTot_VPIS;
    private String icmsTot_VCOFINS;
    private String icmsTot_VIPI;
    private String icmsTot_VIPIDevol;
    private String icmsTot_VICMSDeson;
    private String icmsTot_VBCST;
    private String icmsTot_VFCP;
    private String icmsTot_VFCPST;
    private String icmsTot_vFCPStRet;
    private String icmsTot_VST;
    private String icmsTot_vFrete;
    private String icmsTot_vSeg;

    private String icmsTot_vDesc;
    private String icmsTot_VOutro;

    //  reforma tirbutária


    private Entrega entrega;
    private Products[] products;
    private NotasItens[] itens;

    public Long getNota_id() {
        return nota_id;
    }

    public void setNota_id(Long nota_id) {
        this.nota_id = nota_id;
    }

    public Long getEmitente_id() {
        return emitente_id;
    }

    public void setEmitente_id(Long emitente_id) {
        this.emitente_id = emitente_id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNatOp() {
        return natOp;
    }

    public void setNatOp(String natOp) {
        this.natOp = natOp;
    }

    public String getTpNF() {
        return tpNF;
    }

    public void setTpNF(String tpNF) {
        this.tpNF = tpNF;
    }

    public String getTpEmis() {
        return tpEmis;
    }

    public void setTpEmis(String tpEmis) {
        this.tpEmis = tpEmis;
    }

    public String getTpImp() {
        return tpImp;
    }

    public void setTpImp(String tpImp) {
        this.tpImp = tpImp;
    }

    public String getIdDest() {
        return idDest;
    }

    public void setIdDest(String idDest) {
        this.idDest = idDest;
    }

    public String getcMunFG() {
        return cMunFG;
    }

    public void setcMunFG(String cMunFG) {
        this.cMunFG = cMunFG;
    }

    public String getFinNFe() {
        return finNFe;
    }

    public void setFinNFe(String finNFe) {
        this.finNFe = finNFe;
    }

    public String getIndFinal() {
        return indFinal;
    }

    public void setIndFinal(String indFinal) {
        this.indFinal = indFinal;
    }

    public String getIndPres() {
        return indPres;
    }

    public void setIndPres(String indPres) {
        this.indPres = indPres;
    }

    public String getProcEmi() {
        return procEmi;
    }

    public void setProcEmi(String procEmi) {
        this.procEmi = procEmi;
    }

    public String getVerProc() {
        return verProc;
    }

    public void setVerProc(String verProc) {
        this.verProc = verProc;
    }

    public String getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(String tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public static LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public static void setDataEmissao(LocalDateTime dataEmissao) {
        DadosNfeDto.dataEmissao = dataEmissao;
    }

    public static LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public static void setDataSaida(LocalDateTime dataSaida) {
        DadosNfeDto.dataSaida = dataSaida;
    }

    public String getValorBaseVBC() {
        return valorBaseVBC;
    }

    public void setValorBaseVBC(String valorBaseVBC) {
        this.valorBaseVBC = valorBaseVBC;
    }

    public String getIcmsVICMS() {
        return icmsVICMS;
    }

    public void setIcmsVICMS(String icmsVICMS) {
        this.icmsVICMS = icmsVICMS;
    }

    public String getIcmsVICMSDeson() {
        return icmsVICMSDeson;
    }

    public void setIcmsVICMSDeson(String icmsVICMSDeson) {
        this.icmsVICMSDeson = icmsVICMSDeson;
    }

    public String getSetVProd() {
        return setVProd;
    }

    public void setSetVProd(String setVProd) {
        this.setVProd = setVProd;
    }

    public String getValorPIS() {
        return ValorPIS;
    }

    public void setValorPIS(String valorPIS) {
        ValorPIS = valorPIS;
    }

    public String getValorCOFINS() {
        return ValorCOFINS;
    }

    public void setValorCOFINS(String valorCOFINS) {
        ValorCOFINS = valorCOFINS;
    }

    public String getValorNF() {
        return ValorNF;
    }

    public void setValorNF(String valorNF) {
        ValorNF = valorNF;
    }

    public String getDetPag_Tpag() {
        return detPag_Tpag;
    }

    public void setDetPag_Tpag(String detPag_Tpag) {
        this.detPag_Tpag = detPag_Tpag;
    }

    public String getDetPag_VPag() {
        return detPag_VPag;
    }

    public void setDetPag_VPag(String detPag_VPag) {
        this.detPag_VPag = detPag_VPag;
    }

    public String getTranspModFrete() {
        return transpModFrete;
    }

    public void setTranspModFrete(String transpModFrete) {
        this.transpModFrete = transpModFrete;
    }

    public String getTranspValorFrete() {
        return transpValorFrete;
    }

    public void setTranspValorFrete(String transpValorFrete) {
        this.transpValorFrete = transpValorFrete;
    }

    public String getPisAliq_CST() {
        return pisAliq_CST;
    }

    public void setPisAliq_CST(String pisAliq_CST) {
        this.pisAliq_CST = pisAliq_CST;
    }

    public String getPisAliq_VBC() {
        return pisAliq_VBC;
    }

    public void setPisAliq_VBC(String pisAliq_VBC) {
        this.pisAliq_VBC = pisAliq_VBC;
    }

    public String getPisAliq_PPIS() {
        return pisAliq_PPIS;
    }

    public void setPisAliq_PPIS(String pisAliq_PPIS) {
        this.pisAliq_PPIS = pisAliq_PPIS;
    }

    public String getPisAliq_VPIS() {
        return pisAliq_VPIS;
    }

    public void setPisAliq_VPIS(String pisAliq_VPIS) {
        this.pisAliq_VPIS = pisAliq_VPIS;
    }

    public String getIcmsTot_VBC() {
        return icmsTot_VBC;
    }

    public void setIcmsTot_VBC(String icmsTot_VBC) {
        this.icmsTot_VBC = icmsTot_VBC;
    }

    public String getIcmsTot_VICMS() {
        return icmsTot_VICMS;
    }

    public void setIcmsTot_VICMS(String icmsTot_VICMS) {
        this.icmsTot_VICMS = icmsTot_VICMS;
    }

    public String getIcmsTot_VProd() {
        return icmsTot_VProd;
    }

    public void setIcmsTot_VProd(String icmsTot_VProd) {
        this.icmsTot_VProd = icmsTot_VProd;
    }

    public String getIcmsTot_VPIS() {
        return icmsTot_VPIS;
    }

    public void setIcmsTot_VPIS(String icmsTot_VPIS) {
        this.icmsTot_VPIS = icmsTot_VPIS;
    }

    public String getIcmsTot_VCOFINS() {
        return icmsTot_VCOFINS;
    }

    public void setIcmsTot_VCOFINS(String icmsTot_VCOFINS) {
        this.icmsTot_VCOFINS = icmsTot_VCOFINS;
    }

    public String getIcmsTot_VIPI() {
        return icmsTot_VIPI;
    }

    public void setIcmsTot_VIPI(String icmsTot_VIPI) {
        this.icmsTot_VIPI = icmsTot_VIPI;
    }

    public String getIcmsTot_VIPIDevol() {
        return icmsTot_VIPIDevol;
    }

    public void setIcmsTot_VIPIDevol(String icmsTot_VIPIDevol) {
        this.icmsTot_VIPIDevol = icmsTot_VIPIDevol;
    }

    public String getIcmsTot_VICMSDeson() {
        return icmsTot_VICMSDeson;
    }

    public void setIcmsTot_VICMSDeson(String icmsTot_VICMSDeson) {
        this.icmsTot_VICMSDeson = icmsTot_VICMSDeson;
    }

    public String getIcmsTot_VBCST() {
        return icmsTot_VBCST;
    }

    public void setIcmsTot_VBCST(String icmsTot_VBCST) {
        this.icmsTot_VBCST = icmsTot_VBCST;
    }

    public String getIcmsTot_VFCP() {
        return icmsTot_VFCP;
    }

    public void setIcmsTot_VFCP(String icmsTot_VFCP) {
        this.icmsTot_VFCP = icmsTot_VFCP;
    }

    public String getIcmsTot_VFCPST() {
        return icmsTot_VFCPST;
    }

    public void setIcmsTot_VFCPST(String icmsTot_VFCPST) {
        this.icmsTot_VFCPST = icmsTot_VFCPST;
    }

    public String getIcmsTot_vFCPStRet() {
        return icmsTot_vFCPStRet;
    }

    public void setIcmsTot_vFCPStRet(String icmsTot_vFCPStRet) {
        this.icmsTot_vFCPStRet = icmsTot_vFCPStRet;
    }

    public String getIcmsTot_VST() {
        return icmsTot_VST;
    }

    public void setIcmsTot_VST(String icmsTot_VST) {
        this.icmsTot_VST = icmsTot_VST;
    }

    public String getIcmsTot_vFrete() {
        return icmsTot_vFrete;
    }

    public void setIcmsTot_vFrete(String icmsTot_vFrete) {
        this.icmsTot_vFrete = icmsTot_vFrete;
    }

    public String getIcmsTot_vSeg() {
        return icmsTot_vSeg;
    }

    public void setIcmsTot_vSeg(String icmsTot_vSeg) {
        this.icmsTot_vSeg = icmsTot_vSeg;
    }

    public String getIcmsTot_vDesc() {
        return icmsTot_vDesc;
    }

    public void setIcmsTot_vDesc(String icmsTot_vDesc) {
        this.icmsTot_vDesc = icmsTot_vDesc;
    }

    public String getIcmsTot_VOutro() {
        return icmsTot_VOutro;
    }

    public void setIcmsTot_VOutro(String icmsTot_VOutro) {
        this.icmsTot_VOutro = icmsTot_VOutro;
    }

    public String getIcmsTot_vII() {
        return icmsTot_vII;
    }

    public void setIcmsTot_vII(String icmsTot_vII) {
        this.icmsTot_vII = icmsTot_vII;
    }

    public String getIcmsTot_VTotTrib() {
        return icmsTot_VTotTrib;
    }

    public void setIcmsTot_VTotTrib(String icmsTot_VTotTrib) {
        this.icmsTot_VTotTrib = icmsTot_VTotTrib;
    }

    public String getIcmsTot_VNF() {
        return icmsTot_VNF;
    }

    public void setIcmsTot_VNF(String icmsTot_VNF) {
        this.icmsTot_VNF = icmsTot_VNF;
    }

    public String getDest_XNome() {
        return dest_XNome;
    }

    public void setDest_XNome(String dest_XNome) {
        this.dest_XNome = dest_XNome;
    }

    public String getDest_IndIEDest() {
        return dest_IndIEDest;
    }

    public void setDest_IndIEDest(String dest_IndIEDest) {
        this.dest_IndIEDest = dest_IndIEDest;
    }

    public String getDest_CPF() {
        return dest_CPF;
    }

    public void setDest_CPF(String dest_CPF) {
        this.dest_CPF = dest_CPF;
    }

    public String getDest_CNPJ() {
        return dest_CNPJ;
    }

    public void setDest_CNPJ(String dest_CNPJ) {
        this.dest_CNPJ = dest_CNPJ;
    }

    public String getDest_ie() {
        return dest_ie;
    }

    public void setDest_ie(String dest_ie) {
        this.dest_ie = dest_ie;
    }

    public String getDest_Email() {
        return dest_Email;
    }

    public void setDest_Email(String dest_Email) {
        this.dest_Email = dest_Email;
    }

    public String getDest_XLgr() {
        return dest_XLgr;
    }

    public void setDest_XLgr(String dest_XLgr) {
        this.dest_XLgr = dest_XLgr;
    }

    public String getDest_Nro() {
        return dest_Nro;
    }

    public void setDest_Nro(String dest_Nro) {
        this.dest_Nro = dest_Nro;
    }

    public String getDest_XCpl() {
        return dest_XCpl;
    }

    public void setDest_XCpl(String dest_XCpl) {
        this.dest_XCpl = dest_XCpl;
    }

    public String getDest_XBairro() {
        return dest_XBairro;
    }

    public void setDest_XBairro(String dest_XBairro) {
        this.dest_XBairro = dest_XBairro;
    }

    public String getDest_CMun() {
        return dest_CMun;
    }

    public void setDest_CMun(String dest_CMun) {
        this.dest_CMun = dest_CMun;
    }

    public String getDest_XMun() {
        return dest_XMun;
    }

    public void setDest_XMun(String dest_XMun) {
        this.dest_XMun = dest_XMun;
    }

    public String getDest_UF() {
        return dest_UF;
    }

    public void setDest_UF(String dest_UF) {
        this.dest_UF = dest_UF;
    }

    public String getDest_CEP() {
        return dest_CEP;
    }

    public void setDest_CEP(String dest_CEP) {
        this.dest_CEP = dest_CEP;
    }

    public String getEmit_XNome() {
        return emit_XNome;
    }

    public void setEmit_XNome(String emit_XNome) {
        this.emit_XNome = emit_XNome;
    }

    public String getEmit_CNPJ() {
        return emit_CNPJ;
    }

    public void setEmit_CNPJ(String emit_CNPJ) {
        this.emit_CNPJ = emit_CNPJ;
    }

    public String getEmit_IE() {
        return emit_IE;
    }

    public void setEmit_IE(String emit_IE) {
        this.emit_IE = emit_IE;
    }

    public String getEmit_CRT() {
        return emit_CRT;
    }

    public void setEmit_CRT(String emit_CRT) {
        this.emit_CRT = emit_CRT;
    }

    public String getEmit_XLgr() {
        return emit_XLgr;
    }

    public void setEmit_XLgr(String emit_XLgr) {
        this.emit_XLgr = emit_XLgr;
    }

    public String getEmit_Nro() {
        return emit_Nro;
    }

    public void setEmit_Nro(String emit_Nro) {
        this.emit_Nro = emit_Nro;
    }

    public String getEmit_XCpl() {
        return emit_XCpl;
    }

    public void setEmit_XCpl(String emit_XCpl) {
        this.emit_XCpl = emit_XCpl;
    }

    public String getEmit_XBairro() {
        return emit_XBairro;
    }

    public void setEmit_XBairro(String emit_XBairro) {
        this.emit_XBairro = emit_XBairro;
    }

    public String getEmit_CMun() {
        return emit_CMun;
    }

    public void setEmit_CMun(String emit_CMun) {
        this.emit_CMun = emit_CMun;
    }

    public String getEmit_XMun() {
        return emit_XMun;
    }

    public void setEmit_XMun(String emit_XMun) {
        this.emit_XMun = emit_XMun;
    }

    public String getEmit_UF() {
        return emit_UF;
    }

    public void setEmit_UF(String emit_UF) {
        this.emit_UF = emit_UF;
    }

    public String getEmit_CEP() {
        return emit_CEP;
    }

    public void setEmit_CEP(String emit_CEP) {
        this.emit_CEP = emit_CEP;
    }

    public String getIde_CUF() {
        return ide_CUF;
    }

    public void setIde_CUF(String ide_CUF) {
        this.ide_CUF = ide_CUF;
    }

    public String getIde_CNF() {
        return ide_CNF;
    }

    public void setIde_CNF(String ide_CNF) {
        this.ide_CNF = ide_CNF;
    }

    public String getIde_NatOp() {
        return ide_NatOp;
    }

    public void setIde_NatOp(String ide_NatOp) {
        this.ide_NatOp = ide_NatOp;
    }

    public String getIde_Mod() {
        return ide_Mod;
    }

    public void setIde_Mod(String ide_Mod) {
        this.ide_Mod = ide_Mod;
    }

    public String getIde_Serie() {
        return ide_Serie;
    }

    public void setIde_Serie(String ide_Serie) {
        this.ide_Serie = ide_Serie;
    }

    public String getIde_NNF() {
        return ide_NNF;
    }

    public void setIde_NNF(String ide_NNF) {
        this.ide_NNF = ide_NNF;
    }

    public String getIde_DhEmi() {
        return ide_DhEmi;
    }

    public void setIde_DhEmi(String ide_DhEmi) {
        this.ide_DhEmi = ide_DhEmi;
    }

    public String getIde_TpNF() {
        return ide_TpNF;
    }

    public void setIde_TpNF(String ide_TpNF) {
        this.ide_TpNF = ide_TpNF;
    }

    public String getIde_IdDest() {
        return ide_IdDest;
    }

    public void setIde_IdDest(String ide_IdDest) {
        this.ide_IdDest = ide_IdDest;
    }

    public String getIde_CMunFG() {
        return ide_CMunFG;
    }

    public void setIde_CMunFG(String ide_CMunFG) {
        this.ide_CMunFG = ide_CMunFG;
    }

    public String getIde_TpImp() {
        return ide_TpImp;
    }

    public void setIde_TpImp(String ide_TpImp) {
        this.ide_TpImp = ide_TpImp;
    }

    public String getIde_TpEmis() {
        return ide_TpEmis;
    }

    public void setIde_TpEmis(String ide_TpEmis) {
        this.ide_TpEmis = ide_TpEmis;
    }

    public String getIde_CDV() {
        return ide_CDV;
    }

    public void setIde_CDV(String ide_CDV) {
        this.ide_CDV = ide_CDV;
    }

    public String getIde_TpAmb() {
        return ide_TpAmb;
    }

    public void setIde_TpAmb(String ide_TpAmb) {
        this.ide_TpAmb = ide_TpAmb;
    }

    public String getIde_FinNFe() {
        return ide_FinNFe;
    }

    public void setIde_FinNFe(String ide_FinNFe) {
        this.ide_FinNFe = ide_FinNFe;
    }

    public String getIde_IndFinal() {
        return ide_IndFinal;
    }

    public void setIde_IndFinal(String ide_IndFinal) {
        this.ide_IndFinal = ide_IndFinal;
    }

    public String getIde_IndPres() {
        return ide_IndPres;
    }

    public void setIde_IndPres(String ide_IndPres) {
        this.ide_IndPres = ide_IndPres;
    }

    public String getIde_ProcEmi() {
        return ide_ProcEmi;
    }

    public void setIde_ProcEmi(String ide_ProcEmi) {
        this.ide_ProcEmi = ide_ProcEmi;
    }

    public String getIde_VerProc() {
        return ide_VerProc;
    }

    public void setIde_VerProc(String ide_VerProc) {
        this.ide_VerProc = ide_VerProc;
    }

    public String getProd_VProd() {
        return prod_VProd;
    }

    public void setProd_VProd(String prod_VProd) {
        this.prod_VProd = prod_VProd;
    }

    public String getUFSaidaPais() {
        return UFSaidaPais;
    }

    public void setUFSaidaPais(String UFSaidaPais) {
        this.UFSaidaPais = UFSaidaPais;
    }

    public String getxLocExporta() {
        return xLocExporta;
    }

    public void setxLocExporta(String xLocExporta) {
        this.xLocExporta = xLocExporta;
    }

    public String getModFrete() {
        return modFrete;
    }

    public void setModFrete(String modFrete) {
        this.modFrete = modFrete;
    }

    public String getNome_transp() {
        return nome_transp;
    }

    public void setNome_transp(String nome_transp) {
        this.nome_transp = nome_transp;
    }

    public String getCNPJ_transp() {
        return CNPJ_transp;
    }

    public void setCNPJ_transp(String CNPJ_transp) {
        this.CNPJ_transp = CNPJ_transp;
    }

    public String getCPF_transp() {
        return CPF_transp;
    }

    public void setCPF_transp(String CPF_transp) {
        this.CPF_transp = CPF_transp;
    }

    public String getIe_transp() {
        return ie_transp;
    }

    public void setIe_transp(String ie_transp) {
        this.ie_transp = ie_transp;
    }

    public String getUf_transp() {
        return uf_transp;
    }

    public void setUf_transp(String uf_transp) {
        this.uf_transp = uf_transp;
    }

    public String getVeiculo_placa() {
        return veiculo_placa;
    }

    public void setVeiculo_placa(String veiculo_placa) {
        this.veiculo_placa = veiculo_placa;
    }

    public String getVeiculo_uf() {
        return veiculo_uf;
    }

    public void setVeiculo_uf(String veiculo_uf) {
        this.veiculo_uf = veiculo_uf;
    }

    public String getxEnder_transp() {
        return xEnder_transp;
    }

    public void setxEnder_transp(String xEnder_transp) {
        this.xEnder_transp = xEnder_transp;
    }

    public String getxMun_transp() {
        return xMun_transp;
    }

    public void setxMun_transp(String xMun_transp) {
        this.xMun_transp = xMun_transp;
    }

    public String getQVol() {
        return QVol;
    }

    public void setQVol(String QVol) {
        this.QVol = QVol;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getnVol() {
        return nVol;
    }

    public void setnVol(String nVol) {
        this.nVol = nVol;
    }

    public String getPesoL() {
        return pesoL;
    }

    public void setPesoL(String pesoL) {
        this.pesoL = pesoL;
    }

    public String getPesoB() {
        return pesoB;
    }

    public void setPesoB(String pesoB) {
        this.pesoB = pesoB;
    }

    public String getInfAdic() {
        return infAdic;
    }

    public void setInfAdic(String infAdic) {
        this.infAdic = infAdic;
    }

    public String getInfAdFisco() {
        return infAdFisco;
    }

    public void setInfAdFisco(String infAdFisco) {
        this.infAdFisco = infAdFisco;
    }

    public String getvPag() {
        return vPag;
    }

    public void setvPag(String vPag) {
        this.vPag = vPag;
    }

    public Double getvTroco() {
        return vTroco;
    }

    public void setvTroco(Double vTroco) {
        this.vTroco = vTroco;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public Products[] getProducts() {
        return products;
    }

    public void setProducts(Products[] products) {
        this.products = products;
    }

    public NotasItens[] getItens() {
        return itens;
    }

    public void setItens(NotasItens[] itens) {
        this.itens = itens;
    }
    public String getTpNFDebito() {
        return tpNFDebito;
    }

    public void setTpNFDebito(String tpNFDebito) {
        this.tpNFDebito = tpNFDebito;
    }

    public String getTpNFCredito() {
        return tpNFCredito;
    }

    public void setTpNFCredito(String tpNFCredito) {
        this.tpNFCredito = tpNFCredito;
    }

    public String getcMunFGIBS() {
        return cMunFGIBS;
    }

    public void setcMunFGIBS(String cMunFGIBS) {
        this.cMunFGIBS = cMunFGIBS;
    }

    public String getIndIntermed() {
        return indIntermed;
    }

    public void setIndIntermed(String indIntermed) {
        this.indIntermed = indIntermed;
    }

    public String getTpCompraGov() {
        return tpCompraGov;
    }

    public void setTpCompraGov(String tpCompraGov) {
        this.tpCompraGov = tpCompraGov;
    }

    public String getPRedutor() {
        return pRedutor;
    }

    public void setPRedutor(String pRedutor) {
        this.pRedutor = pRedutor;
    }

    public String getCSTIS() {
        return CSTIS;
    }

    public void setCSTIS(String CSTIS) {
        this.CSTIS = CSTIS;
    }

    public String getcClassTribIS() {
        return cClassTribIS;
    }

    public void setcClassTribIS(String cClassTribIS) {
        this.cClassTribIS = cClassTribIS;
    }

    public String getvBCIS() {
        return vBCIS;
    }

    public void setvBCIS(String vBCIS) {
        this.vBCIS = vBCIS;
    }

    public String getpIS() {
        return pIS;
    }

    public void setpIS(String pIS) {
        this.pIS = pIS;
    }

    public String getpISEspec() {
        return pISEspec;
    }

    public void setpISEspec(String pISEspec) {
        this.pISEspec = pISEspec;
    }

    public String getuTrib() {
        return uTrib;
    }

    public void setuTrib(String uTrib) {
        this.uTrib = uTrib;
    }

    public String getqTrib() {
        return qTrib;
    }

    public void setqTrib(String qTrib) {
        this.qTrib = qTrib;
    }

    public String getvIS() {
        return vIS;
    }

    public void setvIS(String vIS) {
        this.vIS = vIS;
    }

    public String getCST() {
        return CST;
    }

    public void setCST(String CST) {
        this.CST = CST;
    }
}
