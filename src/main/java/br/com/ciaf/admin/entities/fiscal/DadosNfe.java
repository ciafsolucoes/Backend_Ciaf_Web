package br.com.ciaf.admin.entities.fiscal;

import br.com.ciaf.admin.entities.Entrega;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dadosnfe")
public class DadosNfe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long nota_id;
    @Column(name = "tipo_emissao")
    private String tipoEmissao;
    @Column(length = 3)
    private String modelo;
    @Column(length = 44)
    private String seller;
    @Column(name = "nat_op", length = 80)
    private String natOp;
    @Column(name = "tpnf", length = 11)
    private String tpNF;
    @Column(name = "tp_emis", length = 11)
    private String tpEmis;
    @Column(name = "tp_imp", length = 3)
    private String tpImp;
    @Column(name = "id_dest", length = 10)
    private String idDest;
    @Column(name = "c_munfg", length = 7)
    private String cMunFG;
    @Column(name = "finnfe", length = 3)
    private String finNFe;
    @Column(name = "ind_final", length = 3)
    private String indFinal;
    @Column(name = "ind_pres", length = 3)
    private String indPres;
    @Column(name = "proc_emi", length = 3)
    private String procEmi;
    @Column(length = 7)
    private String verProc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataEmissao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataSaida;
    @Column(name = "valor_basevbc", length = 28)
    private String valorBaseVBC;
    @Column(length = 28)
    private String setVProd;
    @Column(name = "valorpis", length = 28)
    private String valorPIS;
    @Column(name = "valorcofins", length = 28)
    private String valorCOFINS;
    @Column(name = "valornf", length = 28)
    private String valorNF;
    @Column(name = "det_pag_tpag", length = 28)
    private String detPag_Tpag;
    @Column(name = "det_pag_vpag", length = 28)
    private String detPag_VPag;
    @Column(name = "transp_mod_frete", length = 28)
    private String transpModFrete;
    @Column(name = "transp_valor_frete", length = 28)
    private String transpValorFrete;
    @Column(name = "transp_valor_seguro", length = 18)
    private String transpValorSeguro;
    @Column(name = "pis_aliq_cst", length = 11)
    private String pisAliq_CST;
    @Column(name = "pis_aliq_vbc", length = 11)
    private String pisAliq_VBC;
    @Column(name = "pis_aliq_ppis", length = 7)
    private String pisAliq_PPIS;
    @Column(name = "pis_aliq_vpis", length = 7)
    private String pisAliq_VPIS;
    @Column(name = "cofins_aliq_pcofins", length = 7)
    private String cofinsAliq_PCOFINS;
    @Column(name = "cofins_aliq_vcofins", length = 7)
    private String cofinsAliq_VCOFINS;
    @Column(name = "icms_tot_vbc", length = 28)
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
    private String dest_UF;
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
    @Column(name = "prod_vprod", length = 21)
    private String prod_VProd;
    @Column(name = "ufsaida_pais", length = 14)
    private String uFSaidaPais;
    @Column(name = "x_loc_exporta", length = 28)
    private String xLocExporta;
    @Column(name = "mod_frete", length = 4)
    private String modFrete;
    @Column(name = "nome_transp", length = 210)
    private String nome_transp;
    @Column(name = "cnpj_transp", length = 18)
    private String cnpj_transp;
    @Column(name = "cpf_transp", length = 19)
    private String cpf_transp;
    @Column(name = "ie_transp", length = 18)
    private String ie_transp;
    @Column(name = "uf_transp", length = 3)
    private String uf_transp;
    @Column(name = "veiculo_placa", length = 7)
    private String veiculo_placa;
    @Column(name = "veiculo_uf", length = 3)
    private String veiculo_uf;
    @Column(name = "x_ender_transp", length = 100)
    private String xEnder_transp;
    @Column(name = "x_mun_transp", length = 100)
    private String xMun_transp;
    @Column(name = "q_vol", length = 18)
    private String qVol;
    @Column(name = "esp", length = 18)
    private String esp;
    @Column(length = 28)
    private String marca;
    @Column(name = "n_vol", length = 18)
    private String nVol;
    @Column(name = "pesol", length = 18)
    private String pesoL;
    @Column(name = "pesob", length = 18)
    private String pesoB;
    @Column(name = "inf_adic", length = 244)
    private String infAdic;
    @Column(name = "inf_ad_imp", length = 244)
    private String inf_ad_imp; //inf. adicionais de olho no imposto
    @Column(name = "inf_ad_fisco", length = 244)
    private String infAdFisco;
    @Column(name = "chave_nfe", length = 49)
    private String chave_nfe;
    @Column(name = "", length = 14)
    private String protocolo;
    @Column(name = "", length = 2)
    private String finalidade;
    @Column(name = "", length = 2)
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
    @Column(nullable = true)
    private Double vPag; // Valor do Pagamento
    @Column(length = 2)
    private String tpIntegra;
    @Column(length = 18)
    private String cnpj_op_cartao;
    @Column(length = 3)
    private String tBand; //Bandeira da operadora de cartão
    @Column(length = 21)
    private String cAut;
    @Column(name = "v_troco", nullable = true)
    private Double vTroco;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notas_id")
    private NotasFiscais notas;

    @JsonManagedReference
    @OneToMany(mappedBy = "dados", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
    private List<NotasItens> items = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "dadosentrega", cascade = CascadeType.ALL, orphanRemoval = true)
    private Entrega entrega;
    public DadosNfe() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNota_id() {
        return nota_id;
    }

    public void setNota_id(Long nota_id) {
        this.nota_id = nota_id;
    }

    public String getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(String tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
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

    public  LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getValorBaseVBC() {
        return valorBaseVBC;
    }

    public void setValorBaseVBC(String valorBaseVBC) {
        this.valorBaseVBC = valorBaseVBC;
    }

    public String getSetVProd() {
        return setVProd;
    }

    public void setSetVProd(String setVProd) {
        this.setVProd = setVProd;
    }

    public String getValorPIS() {
        return valorPIS;
    }

    public void setValorPIS(String valorPIS) {
        this.valorPIS = valorPIS;
    }

    public String getValorCOFINS() {
        return valorCOFINS;
    }

    public void setValorCOFINS(String valorCOFINS) {
        this.valorCOFINS = valorCOFINS;
    }

    public String getValorNF() {
        return valorNF;
    }

    public void setValorNF(String valorNF) {
        this.valorNF = valorNF;
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

    public String getTranspValorSeguro() {
        return transpValorSeguro;
    }

    public void setTranspValorSeguro(String transpValorSeguro) {
        this.transpValorSeguro = transpValorSeguro;
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

    public String getCofinsAliq_PCOFINS() {
        return cofinsAliq_PCOFINS;
    }

    public void setCofinsAliq_PCOFINS(String cofinsAliq_PCOFINS) {
        this.cofinsAliq_PCOFINS = cofinsAliq_PCOFINS;
    }

    public String getCofinsAliq_VCOFINS() {
        return cofinsAliq_VCOFINS;
    }

    public void setCofinsAliq_VCOFINS(String cofinsAliq_VCOFINS) {
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

    public String getuFSaidaPais() {
        return uFSaidaPais;
    }

    public void setuFSaidaPais(String uFSaidaPais) {
        this.uFSaidaPais = uFSaidaPais;
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

    public String getCnpj_transp() {
        return cnpj_transp;
    }

    public void setCnpj_transp(String cnpj_transp) {
        this.cnpj_transp = cnpj_transp;
    }

    public String getCpf_transp() {
        return cpf_transp;
    }

    public void setCpf_transp(String cpf_transp) {
        this.cpf_transp = cpf_transp;
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

    public String getChave_nfe() {
        return chave_nfe;
    }
    public void setChave_nfe(String chave_nfe) {
        this.chave_nfe = chave_nfe;
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

    public Double getvPag() {
        return vPag;
    }

    public void setvPag(Double vPag) {
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

    public Double getvTroco() {
        return vTroco;
    }

    public void setvTroco(Double vTroco) {
        this.vTroco = vTroco;
    }

    public NotasFiscais getNotas() {
        return notas;
    }

    public void setNotas(NotasFiscais notas) {
        this.notas = notas;
    }

    public List<NotasItens> getItems() {
        return items;
    }

    public void setItems(List<NotasItens> items) {
        this.items = items;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    @Override
    public String toString() {
        return "DadosNfe{" +
                "id=" + id +
                ", nota_id=" + nota_id +
                ", tipoEmissao='" + tipoEmissao + '\'' +
                ", modelo='" + modelo + '\'' +
                ", seller='" + seller + '\'' +
                ", natOp='" + natOp + '\'' +
                ", tpNF='" + tpNF + '\'' +
                ", tpEmis='" + tpEmis + '\'' +
                ", tpImp='" + tpImp + '\'' +
                ", idDest='" + idDest + '\'' +
                ", cMunFG='" + cMunFG + '\'' +
                ", finNFe='" + finNFe + '\'' +
                ", indFinal='" + indFinal + '\'' +
                ", indPres='" + indPres + '\'' +
                ", procEmi='" + procEmi + '\'' +
                ", verProc='" + verProc + '\'' +
                ", dataEmissao=" + dataEmissao +
                ", dataSaida=" + dataSaida +
                ", valorBaseVBC='" + valorBaseVBC + '\'' +
                ", setVProd='" + setVProd + '\'' +
                ", valorPIS='" + valorPIS + '\'' +
                ", valorCOFINS='" + valorCOFINS + '\'' +
                ", valorNF='" + valorNF + '\'' +
                ", detPag_Tpag='" + detPag_Tpag + '\'' +
                ", detPag_VPag='" + detPag_VPag + '\'' +
                ", transpModFrete='" + transpModFrete + '\'' +
                ", transpValorFrete='" + transpValorFrete + '\'' +
                ", transpValorSeguro='" + transpValorSeguro + '\'' +
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
                ", icmsTot_VIPI='" + icmsTot_VIPI + '\'' +
                ", icmsTot_VIPIDevol='" + icmsTot_VIPIDevol + '\'' +
                ", icmsTot_VICMSDeson='" + icmsTot_VICMSDeson + '\'' +
                ", icmsTot_VBCST='" + icmsTot_VBCST + '\'' +
                ", icmsTot_VST='" + icmsTot_VST + '\'' +
                ", icmsTot_VFCP='" + icmsTot_VFCP + '\'' +
                ", icmsTot_VFCPST='" + icmsTot_VFCPST + '\'' +
                ", icmsTot_vFCPStRet='" + icmsTot_vFCPStRet + '\'' +
                ", icmsTot_vFrete='" + icmsTot_vFrete + '\'' +
                ", icmsTot_vSeg='" + icmsTot_vSeg + '\'' +
                ", icmsTot_vDesc='" + icmsTot_vDesc + '\'' +
                ", icmsTot_vII='" + icmsTot_vII + '\'' +
                ", icmsTot_VOutro='" + icmsTot_VOutro + '\'' +
                ", icmsTot_VTotTrib='" + icmsTot_VTotTrib + '\'' +
                ", icmsTot_VNF='" + icmsTot_VNF + '\'' +
                ", dest_XNome='" + dest_XNome + '\'' +
                ", dest_idEstrangeiro='" + dest_idEstrangeiro + '\'' +
                ", dest_IndIEDest='" + dest_IndIEDest + '\'' +
                ", dest_CPF='" + dest_CPF + '\'' +
                ", dest_CNPJ='" + dest_CNPJ + '\'' +
                ", dest_ie='" + dest_ie + '\'' +
                ", dest_Email='" + dest_Email + '\'' +
                ", dest_XLgr='" + dest_XLgr + '\'' +
                ", dest_Nro='" + dest_Nro + '\'' +
                ", dest_XCpl='" + dest_XCpl + '\'' +
                ", dest_XBairro='" + dest_XBairro + '\'' +
                ", dest_CMun='" + dest_CMun + '\'' +
                ", dest_XMun='" + dest_XMun + '\'' +
                ", dest_UF='" + dest_UF + '\'' +
                ", dest_CEP='" + dest_CEP + '\'' +
                ", dest_cPais='" + dest_cPais + '\'' +
                ", dest_xPais='" + dest_xPais + '\'' +
                ", dest_fone='" + dest_fone + '\'' +
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
                ", uFSaidaPais='" + uFSaidaPais + '\'' +
                ", xLocExporta='" + xLocExporta + '\'' +
                ", modFrete='" + modFrete + '\'' +
                ", nome_transp='" + nome_transp + '\'' +
                ", cNPJ_transp='" + cnpj_transp + '\'' +
                ", cpf_transp='" + cpf_transp + '\'' +
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
                ", infAdic='" + infAdic + '\'' +
                ", inf_ad_imp='" + inf_ad_imp + '\'' +
                ", infAdFisco='" + infAdFisco + '\'' +
                ", chave_nfe='" + chave_nfe + '\'' +
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
                ", items=" + items +
                ", entrega=" + entrega +
                '}';
    }
}
