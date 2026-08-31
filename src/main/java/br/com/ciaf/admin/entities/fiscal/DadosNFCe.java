package br.com.ciaf.admin.entities.fiscal;


import br.com.swconsultoria.nfe.schemas.TUf;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dados_nfce")
public class DadosNFCe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int tipoEmissao;
    private int serie;
    private int numero;
    @Column(name = "tp_emis", length = 7)
    private String tpEmis;
    @Column(name = "tp_amb", length = 2)
    private String tpAmb;
    @Column(name = "finnfe", length = 2)
    private String finNFe;
    @Column(name = "ind_final", length = 2)
    private String indFinal;
    @Column(name = "ind_pres", length = 2)
    private String indPres;
    @Column(length = 3)
    private String modelo;
    @Column(length = 40)
    private String seller;
    private static LocalDateTime dataEmissao;
    private static LocalDateTime dataSaída;
    @Column(name = "valor_basevbc", length = 21)
    private String valorBaseVBC;
    @Column( name = "icmsvicms", length = 18)
    private String icmsVICMS;
    @Column(name = "icmsvicmsdeson", length = 18)
    private String icmsVICMSDeson;
    @Column(name = "valorpis", length = 18)
    private String ValorPIS;
    @Column(name = "valorcofins", length = 18)
    private String ValorCOFINS;
    @Column(name = "valornf", length = 28)
    private String ValorNF;
    @Column(name = "det_pag_tpag", length = 30)
    private String detPag_Tpag;
    @Column(name = "det_pag_vpag", length = 30)
    private String detPag_VPag;
    @Column(name = "pis_aliq_cst", length = 10)
    private String pisAliq_CST;
    @Column(name = "pis_aliq_vbc", length = 10)
    private String pisAliq_VBC;
    @Column(name = "pis_aliq_ppis", length = 10)
    private String pisAliq_PPIS;
    @Column(name = "pis_aliq_vpis", length = 10)
    private String pisAliq_VPIS;
    @Column(name = "cofins_aliq_pcofins", length = 10)
    private String cofinsAliq_PCOFINS;
    @Column(name = "cofins_aliq_vcofins", length = 10)
    private String cofinsAliq_VCOFINS;
    @Column(name = "icms_tot_vbc", length = 10)
    private String icmsTot_VBC;
    @Column(name = "icms_tot_vicms", length = 28)
    private String icmsTot_VICMS;
    @Column(name = "icms_tot_vprod", length = 28)
    private String icmsTot_VProd;
    @Column(name = "icms_tot_vpis", length = 28)
    private String icmsTot_VPIS;
    @Column(name = "icms_tot_vcofins", length = 28)
    private String icmsTot_VCOFINS;
    @Column(name = "icms_tot_vipi", length = 28)
    private String icmsTot_VIPI;
    @Column(name = "icms_tot_vipidevol", length = 28)
    private String icmsTot_VIPIDevol;
    @Column(name = "icms_tot_vicmsdeson", length = 28)
    private String icmsTot_VICMSDeson;
    @Column(name = "icms_tot_vbcst", length = 28)
    private String icmsTot_VBCST;
    @Column(name = "icms_tot_vst", length = 28)
    private String icmsTot_VST;
    @Column(name = "icms_tot_vfcp", length = 28)
    private String icmsTot_VFCP;
    @Column(name = "icms_tot_vfcpst", length = 28)
    private String icmsTot_VFCPST;
    @Column(name = "icms_tot_vfcpst_ret", length = 28)
    private String icmsTot_vFCPStRet;
    @Column(name = "icms_tot_v_frete", length = 28)
    private String icmsTot_vFrete;
    @Column(name = "icms_tot_v_seg", length = 28)
    private String icmsTot_vSeg;
    @Column(name = "icms_tot_v_desc", length = 21)
    private String icmsTot_vDesc;
    @Column(name = "icms_tot_vii", length = 28)
    private String icmsTot_vII;
    @Column(name = "icms_tot_voutro", length = 28)
    private String icmsTot_VOutro;
    @Column(name = "icms_tot_vtot_trib", length = 28)
    private String icmsTot_VTotTrib;
    @Column(name = "icms_tot_vnf", length = 28)
    private String icmsTot_VNF;
    @Column(name = "dest_xnome", length = 60)
    private String dest_XNome;
    @Column(name="dest_id_estrangeiro", length = 60)
    private String dest_idEstrangeiro;
    @Column(name="dest_indiedest", length = 18)
    private String dest_IndIEDest;
    @Column(name="dest_cpf", length = 18)
    private String dest_CPF;
    @Column(name="dest_cnpj", length = 18)
    private String dest_CNPJ;
    @Column(name="dest_ie", length = 18)
    private String dest_ie;
    @Column(name="dest_email", length = 60)
    private String dest_Email;
    @Column(name="dest_xlgr", length = 67)
    private String dest_XLgr;
    @Column(name="dest_nro", length = 18)
    private String dest_Nro;
    @Column(name="dest_xcpl", length = 20)
    private String dest_XCpl;
    @Column(name="dest_xbairro", length = 49)
    private String dest_XBairro;
    @Column(name="dest_cmun", length = 14)
    private String dest_CMun;
    @Column(name="dest_xmun", length = 28)
    private String dest_XMun;
    @Column(name="dest_uf", length = 3)
    private TUf dest_UF;
    @Column(name="dest_cep", length = 14)
    private String dest_CEP;
    @Column(name="dest_c_pais", length = 14)
    private String dest_cPais; // código do país@Column(name="dest_ ,length = 14)
    @Column(name = "dest_x_pais", length = 49)
    private String dest_xPais; // Nome do País
    @Column(name = "dest_fone", length = 16)
    private String dest_fone;
    @Column(name = "emit_cnpj", length = 18)
    private String emit_CNPJ;
    @Column(name = "emit_xnome", length = 18)
    private String emit_XNome;
    @Column(name = "emit_ie", length = 18)
    private String emit_IE;
    @Column(name = "emit_crt", length = 18)
    private String emit_CRT; //classificação de regime tributário
    @Column(name = "emit_xlgr", length = 60)
    private String emit_XLgr;
    @Column(name = "emit_nro", length = 14)
    private String emit_Nro;
    @Column(name = "emit_xcpl", length = 14)
    private String emit_XCpl;
    @Column(name = "emit_xbairro", length = 21)
    private String emit_XBairro;
    @Column(name = "emit_cmun", length = 14)
    private String emit_CMun;
    @Column(name = "emit_xmun", length = 28)
    private String emit_XMun;
    @Column(name = "emit_uf", length = 3)
    private String emit_UF;
    @Column(name = "emit_cep", length = 14)
    private String emit_CEP;
    @Column(name = "ide_cuf", length = 14)
    private String ide_CUF;
    @Column(name = "ide_cnf",length = 14)
    private String ide_CNF;
    @Column(name = "ide_nat_op",length = 14)
    private String ide_NatOp;
    @Column(name = "ide_mod",length = 3)
    private String ide_Mod;
    @Column(name = "ide_serie",length = 3)
    private String ide_Serie;
    @Column(name = "ide_nnf",length = 14)
    private String ide_NNF;
    @Column(name = "ide_dh_emi",length = 18)
    private String ide_DhEmi;
    @Column(name = "ide_tpnf",length = 14)
    private String ide_TpNF;
    @Column(name = "ide_id_dest",length = 14)
    private String ide_IdDest;
    @Column(name = "ide_cmunfg",length = 14)
    private String ide_CMunFG;
    @Column(name = "ide_tp_imp",length = 14)
    private String ide_TpImp;
    @Column(name = "ide_tp_emis",length = 14)
    private String ide_TpEmis;
    @Column(name = "ide_cdv", length = 14)
    private String ide_CDV;
    @Column(name = "ide_tp_amb", length = 14)
    private String ide_TpAmb;
    @Column(name = "ide_finnfe", length = 14)
    private String ide_FinNFe;
    @Column(name = "ide_ind_final", length = 9)
    private String ide_IndFinal;
    @Column(name = "ide_ind_pres", length = 14)
    private String ide_IndPres;
    @Column(name = "ide_proc_emi", length = 14)
    private String ide_ProcEmi;
    @Column(name = "ide_ver_proc", length = 14)
    private String ide_VerProc;
    @Column(name = "prod_vprod", length = 14)
    private String prod_VProd;
    @Column(length = 4)
    private String modFrete;
    @Column(length = 28)
    private String nome_transp;
    @Column(length = 18)
    private String cNPJ_transp;
    @Column(length = 18)
    private String cPF_transp;
    @Column(length = 18)
    private String ie_transp;
    @Column(length = 3)
    private String uf_transp;
    @Column(length = 7)
    private String veiculo_placa;
    @Column(length = 3)
    private String veiculo_uf;
    @Column( name = "x_ender_transp", length = 28)
    private String xEnder_transp;
    @Column(length = 28)
    private String xMun_transp;
    @Column(length = 18)
    private String qVol;
    @Column(length = 18)
    private String esp;
    @Column(length = 28)
    private String marca;
    @Column(length = 18)
    private String nVol;
    @Column(name = "pesol", length = 18)
    private String pesoL;
    @Column(name = "pesob", length = 18)
    private String pesoB;
    @Column(name = "transp_valor_frete", length = 18)
    private String transpValorFrete;
    @Column(name = "transp_valor_seguro", length = 18)
    private String transpValorSeguro;
    @Column(name = "inf_adic", length = 244)
    private String infAdic;
    @Column(name = "inf_ad_imp", length = 244)
    private String inf_ad_imp;
    @Column(name = "inf_ad_fisco", length = 244)
    private String infAdFisco;
    @Column(length = 49)
    private String chave_nfce;
    @Column(length = 14)
    private String protocolo;
    @Column(length = 2)
    private String finalidade;
    @Column(length = 2)
    private String tipo_danfe;
    @Column(length = 45)
    private String nfce_ref;
    @Column(length = 21)
    private String data_contingencia;
    @Column(length = 21)
    private String hora_contingencia;
    @Column(length = 2)
    private String indPag;  //Forma de Pagamento 0= a vista 1= à prazo
    @Column(length = 3)
    private String tPag; // Meio de pagamento
    private double vPag; // Valor do Pagamento
    @Column(length = 2)
    private String tpIntegra;
    @Column(length = 18)
    private String cnpj_op_cartao;
    @Column(length = 3)
    private String tBand; //Bandeira da operadora de cartão
    @Column(length = 21)
    private String cAut;
    private double vTroco;



    @OneToOne
    @JoinColumn(name = "notas_id")
    @JsonBackReference
    private NotasFiscaisConsumidor notas;

    @JsonManagedReference
    @OneToMany(mappedBy = "dados", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
    private List<NfceItems> items = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "dados_entrega", cascade = CascadeType.ALL, orphanRemoval = true)
    private EntregaNfce entrega;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DadosNFCe() {}
    public int getTipoEmissao() {
        return tipoEmissao;
    }
    public void setTipoEmissao(int tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }
    public int getSerie() {
        return serie;
    }
    public void setSerie(int serie) {
        this.serie = serie;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTpEmis() {
        return tpEmis;
    }

    public void setTpEmis(String tpEmis) {
        this.tpEmis = tpEmis;
    }

    public String getTpAmb() {
        return tpAmb;
    }

    public void setTpAmb(String tpAmb) {
        this.tpAmb = tpAmb;
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

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public static LocalDateTime getDataEmissao() {
        return dataEmissao;
    }
    public static void setDataEmissao(LocalDateTime dataEmissao) {
        DadosNFCe.dataEmissao = dataEmissao;
    }
    public static LocalDateTime getDataSaída() {
        return dataSaída;
    }
    public static void setDataSaída(LocalDateTime dataSaída) {
        DadosNFCe.dataSaída = dataSaída;
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
    public String getCofinaAliqAliq_PCOFINS() {
        return cofinsAliq_PCOFINS;
    }
    public void setCofinaAliqAliq_PCOFINS(String cofinsAliq_PCOFINS) {
        this.cofinsAliq_PCOFINS = cofinsAliq_PCOFINS;
    }

    public String getCofinaAliqAliq_VCOFINS() {
        return cofinsAliq_VCOFINS;
    }

    public void setCofinaAliqAliq_VCOFINS(String cofinsAliq_VCOFINS) {
        this.cofinsAliq_VCOFINS = cofinsAliq_VCOFINS;
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

    public String getIcmsTot_VST() {
        return icmsTot_VST;
    }

    public void setIcmsTot_VST(String icmsTot_VST) {
        this.icmsTot_VST = icmsTot_VST;
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

    public String getIcmsTot_vII() {
        return icmsTot_vII;
    }

    public void setIcmsTot_vII(String icmsTot_vII) {
        this.icmsTot_vII = icmsTot_vII;
    }

    public String getIcmsTot_VOutro() {
        return icmsTot_VOutro;
    }

    public void setIcmsTot_VOutro(String icmsTot_VOutro) {
        this.icmsTot_VOutro = icmsTot_VOutro;
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

    public String getDest_idEstrangeiro() {
        return dest_idEstrangeiro;
    }

    public void setDest_idEstrangeiro(String dest_idEstrangeiro) {
        this.dest_idEstrangeiro = dest_idEstrangeiro;
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

    public TUf getDest_UF() {
        return dest_UF;
    }

    public void setDest_UF(TUf dest_UF) {
        this.dest_UF = dest_UF;
    }

    public String getDest_CEP() {
        return dest_CEP;
    }

    public void setDest_CEP(String dest_CEP) {
        this.dest_CEP = dest_CEP;
    }

    public String getDest_cPais() {
        return dest_cPais;
    }

    public void setDest_cPais(String dest_cPais) {
        this.dest_cPais = dest_cPais;
    }

    public String getDest_xPais() {
        return dest_xPais;
    }

    public void setDest_xPais(String dest_xPais) {
        this.dest_xPais = dest_xPais;
    }

    public String getDest_fone() {
        return dest_fone;
    }

    public void setDest_fone(String dest_fone) {
        this.dest_fone = dest_fone;
    }

    public String getDest_Email() {
        return dest_Email;
    }

    public void setDest_Email(String dest_Email) {
        this.dest_Email = dest_Email;
    }

    public String getEmit_CNPJ() {
        return emit_CNPJ;
    }

    public String getEmit_XNome() {
        return emit_XNome;
    }

    public void setEmit_XNome(String emit_XNome) {
        this.emit_XNome = emit_XNome;
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
        this.emit_XLgr = emit_XLgr;//classificação de regime tributário
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

    public String getcNPJ_transp() {
        return cNPJ_transp;
    }

    public void setcNPJ_transp(String cNPJ_transp) {
        this.cNPJ_transp = cNPJ_transp;
    }

    public String getcPF_transp() {
        return cPF_transp;
    }

    public void setcPF_transp(String cPF_transp) {
        this.cPF_transp = cPF_transp;
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

    public String getqVol() {
        return qVol;
    }

    public void setqVol(String qVol) {
        this.qVol = qVol;
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

    public String getTranspValorFrete() {
        return transpValorFrete;
    }

    public void setTranspValorFrete(String transpValorFrete) {
        this.transpValorFrete = transpValorFrete;
    }

    public String getTranspValorSeguro() {
        return transpValorSeguro;
    }

    public void setTranspValorSeguro(String transpValorSeguro) {
        this.transpValorSeguro = transpValorSeguro;
    }

    public String getInfAdic() {
        return infAdic;
    }

    public void setInfAdic(String infAdic) {
        this.infAdic = infAdic;
    }

    public String getInf_ad_imp() {
        return inf_ad_imp;
    }

    public void setInf_ad_imp(String inf_ad_imp) {
        this.inf_ad_imp = inf_ad_imp;
    }

    public String getInfAdFisco() {
        return infAdFisco;
    }

    public void setInfAdFisco(String infAdFisco) {
        this.infAdFisco = infAdFisco;
    }

    public List<NfceItems> getItems() {
        return items;
    }

    public void setItems(List<NfceItems> items) {
        this.items = items;
    }

    public EntregaNfce getEntrega() {
        return entrega;
    }

    public void setEntrega(EntregaNfce entrega) {
        this.entrega = entrega;
    }

    public String getChave_nfce() {
        return chave_nfce;
    }

    public void setChave_nfce(String chave_nfce) {
        this.chave_nfce = chave_nfce;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getTipo_danfe() {
        return tipo_danfe;
    }

    public void setTipo_danfe(String tipo_danfe) {
        this.tipo_danfe = tipo_danfe;
    }

    public String getNfce_ref() {
        return nfce_ref;
    }

    public void setNfce_ref(String nfce_ref) {
        this.nfce_ref = nfce_ref;
    }

    public String getData_contingencia() {
        return data_contingencia;
    }

    public void setData_contingencia(String data_contingencia) {
        this.data_contingencia = data_contingencia;
    }

    public String getHora_contingencia() {
        return hora_contingencia;
    }

    public void setHora_contingencia(String hora_contingencia) {
        this.hora_contingencia = hora_contingencia;
    }

    public String getIndPag() {
        return indPag;
    }

    public void setIndPag(String indPag) {
        this.indPag = indPag;
    }

    public String gettPag() {
        return tPag;
    }

    public void settPag(String tPag) {
        this.tPag = tPag;
    }

    public double getvPag() {
        return vPag;
    }

    public void setvPag(double vPag) {
        this.vPag = vPag;
    }

    public String getTpIntegra() {
        return tpIntegra;
    }

    public void setTpIntegra(String tpIntegra) {
        this.tpIntegra = tpIntegra;
    }

    public String getCnpj_op_cartao() {
        return cnpj_op_cartao;
    }

    public void setCnpj_op_cartao(String cnpj_op_cartao) {
        this.cnpj_op_cartao = cnpj_op_cartao;
    }

    public String gettBand() {
        return tBand;
    }

    public void settBand(String tBand) {
        this.tBand = tBand;
    }

    public String getcAut() {
        return cAut;
    }

    public void setcAut(String cAut) {
        this.cAut = cAut;
    }

    public double getvTroco() {
        return vTroco;
    }

    public void setvTroco(double vTroco) {
        this.vTroco = vTroco;
    }

    @JsonIgnore
    public NotasFiscaisConsumidor getNotas() {
        return notas;
    }

    @JsonIgnore
    public void setNotas(NotasFiscaisConsumidor notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "DadosNFCe{" +
                "id=" + id +
                ", tipoEmissao=" + tipoEmissao +
                ", serie=" + serie +
                ", numero=" + numero +
                ", tpEmis='" + tpEmis + '\'' +
                ", tpAmb='" + tpAmb + '\'' +
                ", finNFe='" + finNFe + '\'' +
                ", indFinal='" + indFinal + '\'' +
                ", indPres='" + indPres + '\'' +
                ", modelo='" + modelo + '\'' +
                ", seller='" + seller + '\'' +
                ", valorBaseVBC='" + valorBaseVBC + '\'' +
                ", icmsVICMS='" + icmsVICMS + '\'' +
                ", icmsVICMSDeson='" + icmsVICMSDeson + '\'' +
                ", ValorPIS='" + ValorPIS + '\'' +
                ", ValorCOFINS='" + ValorCOFINS + '\'' +
                ", ValorNF='" + ValorNF + '\'' +
                ", detPag_Tpag='" + detPag_Tpag + '\'' +
                ", detPag_VPag='" + detPag_VPag + '\'' +
                ", pisAliq_CST='" + pisAliq_CST + '\'' +
                ", pisAliq_VBC='" + pisAliq_VBC + '\'' +
                ", pisAliq_PPIS='" + pisAliq_PPIS + '\'' +
                ", pisAliq_VPIS='" + pisAliq_VPIS + '\'' +
                ", cofinsAliq_PCOFINS='" + cofinsAliq_PCOFINS + '\'' +
                ", cofinsAliq_VCOFINS='" + cofinsAliq_VCOFINS + '\'' +
                ", icmsTot_VBC='" + icmsTot_VBC + '\'' +
                ", icmsTot_VICMS='" + icmsTot_VICMS + '\'' +
                ", icmsTot_VProd='" + icmsTot_VProd + '\'' +
                ", icmsTot_VPIS='" + icmsTot_VPIS + '\'' +
                ", icmsTot_VCOFINS='" + icmsTot_VCOFINS + '\'' +
                ", icmsTot_VNF='" + icmsTot_VNF + '\'' +
                ", dest_XNome='" + dest_XNome + '\'' +
                ", dest_idEstrangeiro='" + dest_idEstrangeiro + '\'' +
                ", dest_IndIEDest='" + dest_IndIEDest + '\'' +
                ", dest_CPF='" + dest_CPF + '\'' +
                ", dest_CNPJ='" + dest_CNPJ + '\'' +
                ", dest_XLgr='" + dest_XLgr + '\'' +
                ", dest_Nro='" + dest_Nro + '\'' +
                ", dest_XCpl='" + dest_XCpl + '\'' +
                ", dest_XBairro='" + dest_XBairro + '\'' +
                ", dest_CMun='" + dest_CMun + '\'' +
                ", dest_XMun='" + dest_XMun + '\'' +
                ", dest_UF=" + dest_UF +
                ", dest_CEP='" + dest_CEP + '\'' +
                ", dest_cPais='" + dest_cPais + '\'' +
                ", dest_xPais='" + dest_xPais + '\'' +
                ", dest_fone='" + dest_fone + '\'' +
                ", dest_Email='" + dest_Email + '\'' +
                ", emit_CNPJ='" + emit_CNPJ + '\'' +
                ", emit_IE='" + emit_IE + '\'' +
                ", emit_CRT='" + emit_CRT + '\'' +
                ", emit_XLgr='" + emit_XLgr + '\'' +
                ", emit_Nro='" + emit_Nro + '\'' +
                ", emit_XCpl='" + emit_XCpl + '\'' +
                ", emit_XBairro='" + emit_XBairro + '\'' +
                ", emit_CMun='" + emit_CMun + '\'' +
                ", emit_XMun='" + emit_XMun + '\'' +
                ", emit_UF='" + emit_UF + '\'' +
                ", emit_CEP='" + emit_CEP + '\'' +
                ", ide_CUF='" + ide_CUF + '\'' +
                ", ide_CNF='" + ide_CNF + '\'' +
                ", ide_NatOp='" + ide_NatOp + '\'' +
                ", ide_Mod='" + ide_Mod + '\'' +
                ", ide_Serie='" + ide_Serie + '\'' +
                ", ide_NNF='" + ide_NNF + '\'' +
                ", ide_DhEmi='" + ide_DhEmi + '\'' +
                ", ide_TpNF='" + ide_TpNF + '\'' +
                ", ide_IdDest='" + ide_IdDest + '\'' +
                ", ide_CMunFG='" + ide_CMunFG + '\'' +
                ", ide_TpImp='" + ide_TpImp + '\'' +
                ", ide_TpEmis='" + ide_TpEmis + '\'' +
                ", ide_CDV='" + ide_CDV + '\'' +
                ", ide_TpAmb='" + ide_TpAmb + '\'' +
                ", ide_FinNFe='" + ide_FinNFe + '\'' +
                ", ide_IndFinal='" + ide_IndFinal + '\'' +
                ", ide_IndPres='" + ide_IndPres + '\'' +
                ", ide_ProcEmi='" + ide_ProcEmi + '\'' +
                ", ide_VerProc='" + ide_VerProc + '\'' +
                ", prod_VProd='" + prod_VProd + '\'' +
                ", modFrete='" + modFrete + '\'' +
                ", nome_transp='" + nome_transp + '\'' +
                ", cNPJ_transp='" + cNPJ_transp + '\'' +
                ", cPF_transp='" + cPF_transp + '\'' +
                ", ie_transp='" + ie_transp + '\'' +
                ", uf_transp='" + uf_transp + '\'' +
                ", veiculo_placa='" + veiculo_placa + '\'' +
                ", veiculo_uf='" + veiculo_uf + '\'' +
                ", xEnder_transp='" + xEnder_transp + '\'' +
                ", xMun_transp='" + xMun_transp + '\'' +
                ", qVol='" + qVol + '\'' +
                ", esp='" + esp + '\'' +
                ", marca='" + marca + '\'' +
                ", nVol='" + nVol + '\'' +
                ", pesoL='" + pesoL + '\'' +
                ", pesoB='" + pesoB + '\'' +
                ", transpValorFrete='" + transpValorFrete + '\'' +
                ", transpValorSeguro='" + transpValorSeguro + '\'' +
                ", infAdic='" + infAdic + '\'' +
                ", inf_ad_imp='" + inf_ad_imp + '\'' +
                ", infAdFisco='" + infAdFisco + '\'' +
                ", chave_nfce='" + chave_nfce + '\'' +
                ", protocolo='" + protocolo + '\'' +
                ", finalidade='" + finalidade + '\'' +
                ", tipo_danfe='" + tipo_danfe + '\'' +
                ", nfce_ref='" + nfce_ref + '\'' +
                ", data_contingencia='" + data_contingencia + '\'' +
                ", hora_contingencia='" + hora_contingencia + '\'' +
                ", indPag='" + indPag + '\'' +
                ", tPag='" + tPag + '\'' +
                ", vPag=" + vPag +
                ", tpIntegra='" + tpIntegra + '\'' +
                ", cnpj_op_cartao='" + cnpj_op_cartao + '\'' +
                ", tBand='" + tBand + '\'' +
                ", cAut='" + cAut + '\'' +
                ", vTroco=" + vTroco +
                ", notas=" + notas +
                ", entrega=" + entrega +
                ", items=" + items +
                '}';
    }
}
