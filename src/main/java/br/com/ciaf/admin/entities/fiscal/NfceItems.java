package br.com.ciaf.admin.entities.fiscal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "itemsnfce", schema = "ciaf_id")
public class NfceItems implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_id")
    private Long product_id;
    @Column(name = "brand", length = 75)
    private String brand;
    @Column(name = "prod_cprod", length = 28)
    private String prod_CProd; // 01 codigo Produto
    @Column(name="prod_cean", length = 21)
    private String prod_CEAN;  // 02
    @Column(name="prod_xprod", length = 60)
    private String prod_XProd; // 03 descrição
    @Column(name="prod_ncm", length = 18)
    private String prod_NCM;  // 04
    @Column(name="prod_nve", length = 10)
    private String prod_NVE; // 05
    @Column(name="prod_cest", length = 18)
    private String prod_CEST; // 06
    @Column(name="prod_ind_escala", length = 4)
    private String prod_IndEscala; // 07
    @Column(name="prod_cnpjfab", length = 16)
    private String prod_CNPJFab;  // 08
    @Column(name="prod_c_benef", length = 14)
    private String prod_cBenef;   // 09
    @Column(name="prod_extipi", length = 7)
    private String prod_EXTIPI;  // 10
    @Column(name="prod_cfop", length = 7)
    private String prod_CFOP;   // 11
    @Column(name="prod_ucom", length = 7)
    private String prod_UCom;  // 12
    @Column(name="prod_qcom", length = 21, nullable = true)
    private Double prod_QCom; // 13 quantidade
    @Column(name="prod_vun_com", length = 21)
    private Double prod_VUnCom; // 14 valor
    @Column(name="prod_vprod", length = 21)
    private String prod_VProd; // 15
    @Column(name="prod_ceantrib", length = 16)
    private String prod_CEANTrib;  // 16
    @Column(name="prod_utrib", length = 7)
    private String prod_UTrib;    // 17
    @Column(name="prod_qtrib", length = 11)
    private String prod_QTrib;    // 18
    @Column(name="prod_vun_trib", length = 21)
    private String prod_VUnTrib;  // 19
    @Column(name="prod_v_frete", length = 21)
    private String prod_vFrete;
    @Column(name="prod_pesob", length = 14)
    private String prod_pesoB;
    @Column(name="prod_pesol", length = 14)
    private String prod_pesoL;
    @Column(name="prod_v_seg", length = 21)
    private String prod_vSeg;        // 21
    @Column(name="prod_v_desc", length = 21)
    private String prod_vDesc;    // 22
    @Column(name="prod_v_outro", length = 21)
    private String prod_vOutro;     // 23
    @Column(name="cst_csosn", length = 14)
    private String cst_csosn;  // 24
    @Column(name="prod_red_icms", length = 7)
    private String prod_RedIcms; // 25 porcentagem
    @Column(name="prod_vbcicms", length = 21)
    private String prod_VBCIcms; // 26
    @Column(name="prod_pcicms", length = 7)
    private String prod_PCIcms; // 27 percentagem calculo icms
    @Column(name="prod_ind_tot", length = 7)
    private String prod_IndTot; // 28
    @Column(name="prod_vicms", length = 18)
    private String prod_VICMS; // 29

    @Column(name="prod_pbc_icms", length = 18)
    private String prod_pBC_ICMS; // 29

    @Column(name = "prod_p_redbc_icms", length = 7)
    private String prod_pRedBC_ICMS;  // 30

    @Column(name = "prod_picms", length = 7)
    private String prod_PICMS;  // 30

    @Column(name = "prod_p_credsn", length = 5)
    private String prod_pCredSN;

    @Column(name = "prod_v_credicmssn", length = 21)
    private String prod_vCredICMSSN;

    @Column(name = "prod_pipi", length = 7)
    private String prod_pIPI;  // 30

    @Column(name = "prod_vipi", length = 18)
    private String prod_VIPI; // 31

    @Column(name = "prod_orig", length = 7)
    private String prod_orig; // 34
    @Column(name = "prod_modbc", length = 7)
    private String prod_modBC; // 35
    @Column(name="prod_cst", length = 7)
    private String prod_CST = "00"; // 36
    @Column(name = "prod_pfcp", length = 10)
    private String prod_pFCP; // 37
    @Column(name = "prod_vfcp", length = 21)
    private String prod_vFCP; // 38
    @Column(name = "prod_desconto", length = 14)
    private String prod_Desconto; // 39 => analisar se está sobrando
    @Column(name = "prod_vicmsdeson", length = 21)
    private String prod_vICMSDeson;
    @Column(name = "total_price", length = 21)
    private Double totalPrice; // 40
    // importação
    @Column(name = "prod_ndi", length = 14)
    private String prod_nDI;
    @Column(name = "prod_ddi", length = 14)
    private String prod_dDI;
    @Column(name = "prod_x_loc_desemb", length = 63)
    private String prod_xLocDesemb;
    @Column(name = "prod_ufdesemb", length = 4)
    private String prod_UFDesemb;
    @Column(name = "prod_d_desemb", length = 6)
    private String prod_dDesemb;
    @Column(name = "prod_tp_via_transp", length = 4)
    private String prod_tpViaTransp;
    @Column(name = "prod_vafrim", length = 21)
    private String prod_vAFRIM;
    @Column(name = "prod_tp_intermedio", length = 4)
    private String prod_tpIntermedio;
    @Column(name = "prod_iof", length = 14)
    private String prod_IOF;  // não encontrei no manual
    @Column(name = "prod_num_fci", length = 14)
    private String prod_Num_Fci; // não encontrei no manual
    @Column(name = "prod_desp_aduaneira", length = 14)
    private String prod_desp_aduaneira; // não encontrei no manual
    @Column(name = "prod_vl_imp_import", length = 14)
    private String prod_vl_imp_import; // não encontrei no manual
    @Column(name = "prod_cnpj_encomendante", length = 18)
    private String prod_CNPJ_encomendante; // não encontrei no manual
    @Column(name = "prod_ufterceiro", length = 4)
    private String prod_UFTerceiro;
    @Column(name = "prod_c_exportador", length = 63)
    private String prod_cExportador;
    @Column(name = "prod_n_adicao", length = 4)
    private String prod_nAdicao;
    @Column(name = "prod_n_seq_adic", length = 4)
    private String prod_nSeqAdic;
    @Column(name = "prod_desconto_adic", length = 21)
    private String prod_descontoAdic;
    @Column(name = "prod_c_fabricante", length = 63)
    private String prod_cFabricante;
    @Column(name = "prod_v_descdi", length = 21)
    private String prod_vDescDI;
    @Column(name = "prod_n_draw_imp", length = 14)
    private String prod_nDrawImp;

    // exportação
    @Column(name = "prod_n_draw", length = 14)
    private String prod_nDraw;

    // combustíveis
    @Column(name = "prod_c_prodanp", length = 60)
    private String prod_cProdANP;
    @Column(name = "prod_descanp", length = 60)
    private String prod_descANP;
    @Column(name = "prod_v_comb", length = 21)
    private String prod_vComb; // valor do combustível
    @Column(name = "prod_pglp", length = 60)
    private String prod_pGLP;  // Informar em número decimal o percentual do GLP
    @Column(name = "prod_pgnn", length = 5)
    private String prod_pGNn;  // Informar em número decimal o percentual do Gás
    @Column(name = "prod_pgni", length = 21)
    private String prod_pGNi;  // Informar em número decimal o percentual do Gás importado
    @Column(name = "prod_v_part", length = 21)
    private String prod_vPart;  // valor por quilograma sem ICMS
    @Column(name = "prod_codif", length = 28)
    private String prod_CODIF;  // Informar apenas quando a UF utilizar o CODIF
    @Column(name = "prod_q_temp", length = 21)
    private String prod_qTemp;  // uma temperatura diferente da ambiente.
    @Column(name = "prod_ufcons", length = 3)
    private String prod_UFCons;  // Informar a UF de consumo
    @Column(name = "prod_qbcprod", length = 21)
    private String prod_qBCProd;  // Informar a BC da CIDE em quantidade
    @Column(name = "prod_v_aliq_prod", length = 21)
    private String prod_vAliqProd;  // Informar o valor da alíquota em reais da CIDE
    // cofins
    @Column(name = "prod_cst_cofins", length = 6)
    private String prod_CST_Cofins;
    @Column(name = "prod_vbc_confins", length = 21)
    private String prod_vBC_confins;
    @Column(name = "cofins", length = 14)
    private String cofins;
    @Column(name = "prod_vcofins", length = 21)
    private String prod_VCOFINS;
    @Column(name = "prod_qbcofins", length = 18)
    private String prod_qBCofins;
    @Column(name = "prod_vbcst_confins", length = 21)
    private String prod_vBCST_confins;
    @Column(name = "prod_v_aliqstcofins", length = 18)
    private String prod_vAliqSTCofins;
    @Column(name = "prod_vstcofins", length = 21)
    private String prod_vSTCofins;

    // pis
    @Column(name = "prod_cst_pis", length = 21)
    private String prod_CST_Pis;
    @Column(name = "prod_vbc_pis", length = 21)
    private String prod_vBC_Pis;
    @Column(name = "prod_ppis", length = 7)
    private String prod_PPIS;
    @Column(name = "prod_vpis", length = 18)
    private String prod_VPIS;
    @Column(name = "prod_qbpis", length = 18)
    private String prod_qBPis; // QUANTIDADE VENDIDA
    @Column(name = "prod_vbcst_pis", length = 21)
    private String prod_vBCST_Pis;
    @Column(name = "prod_v_aliqstpis", length = 18)
    private String prod_vAliqSTPis;
    @Column(name = "prod_vstpis", length = 21)
    private String prod_vSTPis;

    // partilha
    @Column(name = "prod_uf_origem", length = 3)
    private String prod_UfOrigem;
    @Column(name = "prod_uf_destino", length = 3)
    private String prod_UfDestino;
    @Column(name = "prod_vbcufdest", length = 21)
    private String prod_vBCUFDest;
    @Column(name = "prod_vbcfcpufdest", length = 21)
    private String prod_vBCFCPUFDest;
    @Column(name = "prod_pfcpufdest", length = 10)
    private String prod_pFCPUFDest;
    @Column(name = "prod_picmsufdest", length = 10)
    private String prod_pICMSUFDest;
    @Column(name = "prod_picmsinter", length = 10)
    private String prod_pICMSInter;
    @Column(name = "prod_picmsinter_part", length = 10)
    private String prod_pICMSInterPart;
    @Column(name = "prod_vfcpufdest", length = 21)
    private String prod_vFCPUFDest;
    @Column(name = "prod_vicmsufdest", length = 21)
    private String prod_vICMSUFDest;
    @Column(name = "prod_vicmsufreme", length = 21)
    private String prod_vICMSUFReme;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dados_nfce_id")
    private DadosNFCe dados;

    public DadosNFCe getDados() {
        return dados;
    }

    public void setDados(DadosNFCe dados) {
        this.dados = dados;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProd_IndEscala() {
        return prod_IndEscala;
    }

    public void setProd_IndEscala(String prod_IndEscala) {
        this.prod_IndEscala = prod_IndEscala;
    }

    public String getProd_CNPJFab() {
        return prod_CNPJFab;
    }

    public void setProd_CNPJFab(String prod_CNPJFab) {
        this.prod_CNPJFab = prod_CNPJFab;
    }

    public String getProd_cBenef() {
        return prod_cBenef;
    }

    public void setProd_cBenef(String prod_cBenef) {
        this.prod_cBenef = prod_cBenef;
    }

    public String getProd_EXTIPI() {
        return prod_EXTIPI;
    }

    public void setProd_EXTIPI(String prod_EXTIPI) {
        this.prod_EXTIPI = prod_EXTIPI;
    }

    public String getProd_UTrib() {
        return prod_UTrib;
    }

    public void setProd_UTrib(String prod_UTrib) {
        this.prod_UTrib = prod_UTrib;
    }

    public String getCst_csosn() {
        return cst_csosn;
    }

    public void setCst_csosn(String cst_csosn) {
        this.cst_csosn = cst_csosn;
    }

    public String getProd_RedIcms() {
        return prod_RedIcms;
    }

    public void setProd_RedIcms(String prod_RedIcms) {
        this.prod_RedIcms = prod_RedIcms;
    }

    public String getProd_VBCIcms() {
        return prod_VBCIcms;
    }

    public void setProd_VBCIcms(String prod_VBCIcms) {
        this.prod_VBCIcms = prod_VBCIcms;
    }

    public String getProd_VUnTrib() {
        return prod_VUnTrib;
    }

    public void setProd_VUnTrib(String prod_VUnTrib) {
        this.prod_VUnTrib = prod_VUnTrib;
    }

    public String getProd_vFrete() {
        return prod_vFrete;
    }

    public void setProd_vFrete(String prod_vFrete) {
        this.prod_vFrete = prod_vFrete;
    }

    public String getProd_pesoB() {
        return prod_pesoB;
    }

    public void setProd_pesoB(String prod_pesoB) {
        this.prod_pesoB = prod_pesoB;
    }

    public String getProd_pesoL() {
        return prod_pesoL;
    }

    public void setProd_pesoL(String prod_pesoL) {
        this.prod_pesoL = prod_pesoL;
    }

    public String getProd_vSeg() {
        return prod_vSeg;
    }

    public void setProd_vSeg(String prod_vSeg) {
        this.prod_vSeg = prod_vSeg;
    }

    public String getProd_vDesc() {
        return prod_vDesc;
    }

    public void setProd_vDesc(String prod_vDesc) {
        this.prod_vDesc = prod_vDesc;
    }

    public String getProd_vOutro() {
        return prod_vOutro;
    }

    public void setProd_vOutro(String prod_vOutro) {
        this.prod_vOutro = prod_vOutro;
    }

    public String getProd_Desconto() {
        return prod_Desconto;
    }

    public void setProd_Desconto(String prod_Desconto) {
        this.prod_Desconto = prod_Desconto;
    }

    public String getProd_vICMSDeson() {
        return prod_vICMSDeson;
    }

    public String getProd_pRedBC_ICMS() {
        return prod_pRedBC_ICMS;
    }

    public void setProd_pRedBC_ICMS(String prod_pRedBC_ICMS) {
        this.prod_pRedBC_ICMS = prod_pRedBC_ICMS;
    }

    public void setProd_vICMSDeson(String prod_vICMSDeson) {
        this.prod_vICMSDeson = prod_vICMSDeson;
    }

    public String getProd_PCIcms() {
        return prod_PCIcms;
    }

    public void setProd_PCIcms(String prod_PCIcms) {
        this.prod_PCIcms = prod_PCIcms;
    }

    public String getProd_QTrib() {
        return prod_QTrib;
    }

    public void setProd_QTrib(String prod_QTrib) {
        this.prod_QTrib = prod_QTrib;
    }

    public String getProd_IndTot() {
        return prod_IndTot;
    }

    public void setProd_IndTot(String prod_IndTot) {
        this.prod_IndTot = prod_IndTot;
    }

    public String getProd_CProd() {
        return prod_CProd;
    }

    public void setProd_CProd(String prod_CProd) {
        this.prod_CProd = prod_CProd;
    }

    public String getProd_CEAN() {
        return prod_CEAN;
    }

    public void setProd_CEAN(String prod_CEAN) {
        this.prod_CEAN = prod_CEAN;
    }

    public String getProd_VProd() {
        return prod_VProd;
    }

    public void setProd_VProd(String prod_VProd) {
        this.prod_VProd = prod_VProd;
    }

    public String getProd_CEANTrib() {
        return prod_CEANTrib;
    }

    public void setProd_CEANTrib(String prod_CEANTrib) {
        this.prod_CEANTrib = prod_CEANTrib;
    }

    public String getProd_UCom() {
        return prod_UCom;
    }

    public void setProd_UCom(String prod_UCom) {
        this.prod_UCom = prod_UCom;
    }

    public String getProd_VIPI() {
        return prod_VIPI;
    }

    public void setProd_VIPI(String prod_VIPI) {
        this.prod_VIPI = prod_VIPI;
    }

    public String getProd_pIPI() {
        return prod_pIPI;
    }

    public void setProd_pIPI(String prod_pIPI) {
        this.prod_pIPI = prod_pIPI;
    }

    public String getCofins() {
        return cofins;
    }

    public void setCofins(String cofins) {
        this.cofins = cofins;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProd_NCM() {
        return prod_NCM;
    }

    public void setProd_NCM(String prod_NCM) {
        this.prod_NCM = prod_NCM;
    }

    public String getProd_NVE() {
        return prod_NVE;
    }

    public void setProd_NVE(String prod_NVE) {
        this.prod_NVE = prod_NVE;
    }

    public String getProd_CEST() {
        return prod_CEST;
    }

    public void setProd_CEST(String prod_CEST) {
        this.prod_CEST = prod_CEST;
    }

    public String getProd_CFOP() {
        return prod_CFOP;
    }

    public void setProd_CFOP(String prod_CFOP) {
        this.prod_CFOP = prod_CFOP;
    }

    public String getProd_XProd() {
        return prod_XProd;
    }

    public void setProd_XProd(String prod_XProd) {
        this.prod_XProd = prod_XProd;
    }

    public Double getProd_QCom() {
        return prod_QCom;
    }

    public void setProd_QCom(Double prod_QCom) {
        this.prod_QCom = prod_QCom;
    }

    public String getProd_PICMS() {
        return prod_PICMS;
    }

    public void setProd_PICMS(String prod_PICMS) {
        this.prod_PICMS = prod_PICMS;
    }

    public String getProd_pCredSN() {
        return prod_pCredSN;
    }

    public void setProd_pCredSN(String prod_pCredSN) {
        this.prod_pCredSN = prod_pCredSN;
    }

    public String getProd_vCredICMSSN() {
        return prod_vCredICMSSN;
    }

    public void setProd_vCredICMSSN(String prod_vCredICMSSN) {
        this.prod_vCredICMSSN = prod_vCredICMSSN;
    }

    public String getProd_VICMS() {
        return prod_VICMS;
    }

    public void setProd_VICMS(String prod_VICMS) {
        this.prod_VICMS = prod_VICMS;
    }

    public String getProd_pBC_ICMS() {
        return prod_pBC_ICMS;
    }

    public void setProd_pBC_ICMS(String prod_pBC_ICMS) {
        this.prod_pBC_ICMS = prod_pBC_ICMS;
    }

    public String getProd_PPIS() {
        return prod_PPIS;
    }

    public void setProd_PPIS(String prod_PPIS) {
        this.prod_PPIS = prod_PPIS;
    }

    public String getProd_VPIS() {
        return prod_VPIS;
    }

    public void setProd_VPIS(String prod_VPIS) {
        this.prod_VPIS = prod_VPIS;
    }

    public String getProd_VCOFINS() {
        return prod_VCOFINS;
    }

    public void setProd_VCOFINS(String prod_VCOFINS) {
        this.prod_VCOFINS = prod_VCOFINS;
    }

    public String getProd_orig() {
        return prod_orig;
    }

    public void setProd_orig(String prod_orig) {
        this.prod_orig = prod_orig;
    }

    public String getProd_modBC() {
        return prod_modBC;
    }

    public void setProd_modBC(String prod_modBC) {
        this.prod_modBC = prod_modBC;
    }

    public String getProd_CST() {
        return prod_CST;
    }

    public void setProd_CST(String prod_CST) {
        this.prod_CST = prod_CST;
    }

    public String getProd_pFCP() {
        return prod_pFCP;
    }

    public void setProd_pFCP(String prod_pFCP) {
        this.prod_pFCP = prod_pFCP;
    }

    public String getProd_vFCP() {
        return prod_vFCP;
    }

    public void setProd_vFCP(String prod_vFCP) {
        this.prod_vFCP = prod_vFCP;
    }

    public Double getProd_VUnCom() {
        return prod_VUnCom;
    }

    public void setProd_VUnCom(Double prod_VUnCom) {
        this.prod_VUnCom = prod_VUnCom;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProd_nDI() {
        return prod_nDI;
    }

    public void setProd_nDI(String prod_nDI) {
        this.prod_nDI = prod_nDI;
    }

    public String getProd_dDI() {
        return prod_dDI;
    }

    public void setProd_dDI(String prod_dDI) {
        this.prod_dDI = prod_dDI;
    }

    public String getProd_xLocDesemb() {
        return prod_xLocDesemb;
    }

    public void setProd_xLocDesemb(String prod_xLocDesemb) {
        this.prod_xLocDesemb = prod_xLocDesemb;
    }

    public String getProd_UFDesemb() {
        return prod_UFDesemb;
    }

    public void setProd_UFDesemb(String prod_UFDesemb) {
        this.prod_UFDesemb = prod_UFDesemb;
    }

    public String getProd_dDesemb() {
        return prod_dDesemb;
    }

    public void setProd_dDesemb(String prod_dDesemb) {
        this.prod_dDesemb = prod_dDesemb;
    }

    public String getProd_tpViaTransp() {
        return prod_tpViaTransp;
    }

    public void setProd_tpViaTransp(String prod_tpViaTransp) {
        this.prod_tpViaTransp = prod_tpViaTransp;
    }

    public String getProd_vAFRIM() {
        return prod_vAFRIM;
    }

    public void setProd_vAFRIM(String prod_vAFRIM) {
        this.prod_vAFRIM = prod_vAFRIM;
    }

    public String getProd_tpIntermedio() {
        return prod_tpIntermedio;
    }

    public void setProd_tpIntermedio(String prod_tpIntermedio) {
        this.prod_tpIntermedio = prod_tpIntermedio;
    }

    public String getProd_IOF() {
        return prod_IOF;
    }

    public void setProd_IOF(String prod_IOF) {
        this.prod_IOF = prod_IOF;
    }

    public String getProd_Num_Fci() {
        return prod_Num_Fci;
    }

    public void setProd_Num_Fci(String prod_Num_Fci) {
        this.prod_Num_Fci = prod_Num_Fci;
    }

    public String getProd_desp_aduaneira() {
        return prod_desp_aduaneira;
    }

    public void setProd_desp_aduaneira(String prod_desp_aduaneira) {
        this.prod_desp_aduaneira = prod_desp_aduaneira;
    }

    public String getProd_vl_imp_import() {
        return prod_vl_imp_import;
    }

    public void setProd_vl_imp_import(String prod_vl_imp_import) {
        this.prod_vl_imp_import = prod_vl_imp_import;
    }

    public String getProd_CNPJ_encomendante() {
        return prod_CNPJ_encomendante;
    }

    public void setProd_CNPJ_encomendante(String prod_CNPJ_encomendante) {
        this.prod_CNPJ_encomendante = prod_CNPJ_encomendante;
    }

    public String getProd_UFTerceiro() {
        return prod_UFTerceiro;
    }

    public void setProd_UFTerceiro(String prod_UFTerceiro) {
        this.prod_UFTerceiro = prod_UFTerceiro;
    }

    public String getProd_cExportador() {
        return prod_cExportador;
    }

    public void setProd_cExportador(String prod_cExportador) {
        this.prod_cExportador = prod_cExportador;
    }

    public String getProd_nAdicao() {
        return prod_nAdicao;
    }

    public void setProd_nAdicao(String prod_nAdicao) {
        this.prod_nAdicao = prod_nAdicao;
    }

    public String getProd_nSeqAdic() {
        return prod_nSeqAdic;
    }

    public void setProd_nSeqAdic(String prod_nSeqAdic) {
        this.prod_nSeqAdic = prod_nSeqAdic;
    }

    public String getProd_descontoAdic() {
        return prod_descontoAdic;
    }

    public void setProd_descontoAdic(String prod_descontoAdic) {
        this.prod_descontoAdic = prod_descontoAdic;
    }

    public String getProd_cFabricante() {
        return prod_cFabricante;
    }

    public void setProd_cFabricante(String prod_cFabricante) {
        this.prod_cFabricante = prod_cFabricante;
    }

    public String getProd_vDescDI() {
        return prod_vDescDI;
    }

    public void setProd_vDescDI(String prod_vDescDI) {
        this.prod_vDescDI = prod_vDescDI;
    }

    public String getProd_nDrawImp() {
        return prod_nDrawImp;
    }

    public void setProd_nDrawImp(String prod_nDrawImp) {
        this.prod_nDrawImp = prod_nDrawImp;
    }

    public String getProd_nDraw() {
        return prod_nDraw;
    }

    public void setProd_nDraw(String prod_nDraw) {
        this.prod_nDraw = prod_nDraw;
    }

    public String getProd_cProdANP() {
        return prod_cProdANP;
    }

    public void setProd_cProdANP(String prod_cProdANP) {
        this.prod_cProdANP = prod_cProdANP;
    }

    public String getProd_descANP() {
        return prod_descANP;
    }

    public void setProd_descANP(String prod_descANP) {
        this.prod_descANP = prod_descANP;
    }

    public String getProd_vComb() {
        return prod_vComb;
    }

    public void setProd_vComb(String prod_vComb) {
        this.prod_vComb = prod_vComb;
    }

    public String getProd_pGLP() {
        return prod_pGLP;
    }

    public void setProd_pGLP(String prod_pGLP) {
        this.prod_pGLP = prod_pGLP;
    }

    public String getProd_pGNn() {
        return prod_pGNn;
    }

    public void setProd_pGNn(String prod_pGNn) {
        this.prod_pGNn = prod_pGNn;
    }

    public String getProd_pGNi() {
        return prod_pGNi;
    }

    public void setProd_pGNi(String prod_pGNi) {
        this.prod_pGNi = prod_pGNi;
    }

    public String getProd_vPart() {
        return prod_vPart;
    }

    public void setProd_vPart(String prod_vPart) {
        this.prod_vPart = prod_vPart;
    }

    public String getProd_CODIF() {
        return prod_CODIF;
    }

    public void setProd_CODIF(String prod_CODIF) {
        this.prod_CODIF = prod_CODIF;
    }

    public String getProd_qTemp() {
        return prod_qTemp;
    }

    public void setProd_qTemp(String prod_qTemp) {
        this.prod_qTemp = prod_qTemp;
    }

    public String getProd_UFCons() {
        return prod_UFCons;
    }

    public void setProd_UFCons(String prod_UFCons) {
        this.prod_UFCons = prod_UFCons;
    }

    public String getProd_qBCProd() {
        return prod_qBCProd;
    }

    public void setProd_qBCProd(String prod_qBCProd) {
        this.prod_qBCProd = prod_qBCProd;
    }

    public String getProd_vAliqProd() {
        return prod_vAliqProd;
    }

    public void setProd_vAliqProd(String prod_vAliqProd) {
        this.prod_vAliqProd = prod_vAliqProd;
    }

    public String getProd_CST_Cofins() {
        return prod_CST_Cofins;
    }

    public void setProd_CST_Cofins(String prod_CST_Cofins) {
        this.prod_CST_Cofins = prod_CST_Cofins;
    }

    public String getProd_vBC_confins() {
        return prod_vBC_confins;
    }

    public void setProd_vBC_confins(String prod_vBC_confins) {
        this.prod_vBC_confins = prod_vBC_confins;
    }

    public String getProd_qBCofins() {
        return prod_qBCofins;
    }

    public void setProd_qBCofins(String prod_qBCofins) {
        this.prod_qBCofins = prod_qBCofins;
    }

    public String getProd_vBCST_confins() {
        return prod_vBCST_confins;
    }

    public void setProd_vBCST_confins(String prod_vBCST_confins) {
        this.prod_vBCST_confins = prod_vBCST_confins;
    }

    public String getProd_vAliqSTCofins() {
        return prod_vAliqSTCofins;
    }

    public void setProd_vAliqSTCofins(String prod_vAliqSTCofins) {
        this.prod_vAliqSTCofins = prod_vAliqSTCofins;
    }

    public String getProd_vSTCofins() {
        return prod_vSTCofins;
    }

    public void setProd_vSTCofins(String prod_vSTCofins) {
        this.prod_vSTCofins = prod_vSTCofins;
    }

    public String getProd_CST_Pis() {
        return prod_CST_Pis;
    }

    public void setProd_CST_Pis(String prod_CST_Pis) {
        this.prod_CST_Pis = prod_CST_Pis;
    }

    public String getProd_vBC_Pis() {
        return prod_vBC_Pis;
    }

    public void setProd_vBC_Pis(String prod_vBC_Pis) {
        this.prod_vBC_Pis = prod_vBC_Pis;
    }

    public String getProd_qBPis() {
        return prod_qBPis;
    }

    public void setProd_qBPis(String prod_qBPis) {
        this.prod_qBPis = prod_qBPis;
    }

    public String getProd_vBCST_Pis() {
        return prod_vBCST_Pis;
    }

    public void setProd_vBCST_Pis(String prod_vBCST_Pis) {
        this.prod_vBCST_Pis = prod_vBCST_Pis;
    }

    public String getProd_vAliqSTPis() {
        return prod_vAliqSTPis;
    }

    public void setProd_vAliqSTPis(String prod_vAliqSTPis) {
        this.prod_vAliqSTPis = prod_vAliqSTPis;
    }

    public String getProd_vSTPis() {
        return prod_vSTPis;
    }

    public void setProd_vSTPis(String prod_vSTPis) {
        this.prod_vSTPis = prod_vSTPis;
    }

    public String getProd_UfOrigem() {
        return prod_UfOrigem;
    }

    public void setProd_UfOrigem(String prod_UfOrigem) {
        this.prod_UfOrigem = prod_UfOrigem;
    }

    public String getProd_UfDestino() {
        return prod_UfDestino;
    }

    public void setProd_UfDestino(String prod_UfDestino) {
        this.prod_UfDestino = prod_UfDestino;
    }

    public String getProd_vBCUFDest() {
        return prod_vBCUFDest;
    }

    public void setProd_vBCUFDest(String prod_vBCUFDest) {
        this.prod_vBCUFDest = prod_vBCUFDest;
    }

    public String getProd_vBCFCPUFDest() {
        return prod_vBCFCPUFDest;
    }

    public void setProd_vBCFCPUFDest(String prod_vBCFCPUFDest) {
        this.prod_vBCFCPUFDest = prod_vBCFCPUFDest;
    }

    public String getProd_pFCPUFDest() {
        return prod_pFCPUFDest;
    }

    public void setProd_pFCPUFDest(String prod_pFCPUFDest) {
        this.prod_pFCPUFDest = prod_pFCPUFDest;
    }

    public String getProd_pICMSUFDest() {
        return prod_pICMSUFDest;
    }

    public void setProd_pICMSUFDest(String prod_pICMSUFDest) {
        this.prod_pICMSUFDest = prod_pICMSUFDest;
    }

    public String getProd_pICMSInter() {
        return prod_pICMSInter;
    }

    public void setProd_pICMSInter(String prod_pICMSInter) {
        this.prod_pICMSInter = prod_pICMSInter;
    }

    public String getProd_pICMSInterPart() {
        return prod_pICMSInterPart;
    }

    public void setProd_pICMSInterPart(String prod_pICMSInterPart) {
        this.prod_pICMSInterPart = prod_pICMSInterPart;
    }

    public String getProd_vFCPUFDest() {
        return prod_vFCPUFDest;
    }

    public void setProd_vFCPUFDest(String prod_vFCPUFDest) {
        this.prod_vFCPUFDest = prod_vFCPUFDest;
    }

    public String getProd_vICMSUFDest() {
        return prod_vICMSUFDest;
    }

    public void setProd_vICMSUFDest(String prod_vICMSUFDest) {
        this.prod_vICMSUFDest = prod_vICMSUFDest;
    }

    public String getProd_vICMSUFReme() {
        return prod_vICMSUFReme;
    }

    public void setProd_vICMSUFReme(String prod_vICMSUFReme) {
        this.prod_vICMSUFReme = prod_vICMSUFReme;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NfceItems nfceItems = (NfceItems) o;
        return Objects.equals(id, nfceItems.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
