package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "products")
public class Products extends MyEmitenteId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "register_date", length = 21)
	private String register_date;
	@Column(name = "brand", length = 35)
	private String brand;
	@Column(name = "gtin_valid", length = 35)
	private String gtin_valid;
	@Column(name = "cprod", length = 28)
	private String cprod; // 01
	@Column(name = "cean", length = 28)
	private String cean; // 02
	@Column(name = "description", length = 80)
	private String description; // prod_XProd; 03
	@Column(name = "ncm", length = 14)
	private String ncm; // 04
	@Column(name = "prod_nve", length = 10)
	private String prod_NVE;  // 05
	@Column(name = "cest")
	private String cest; // 06 Código Especificador da Substituição Tributária
	@Column(name = "indescala", length = 4)
	private String indescala; // 07
	@Column(name = "prod_cnpjfab", length = 16)
	private String prod_CNPJFab;   // 08
	@Column(name = "prod_c_benef", length = 14)
	private String prod_cBenef;    // 09
	@Column(name = "prod_extipi", length = 7)
	private String prod_EXTIPI;    // 10
	@Column(name = "cfop", length = 6)
	private String cfop;  // 11
	@Column(name = "cfop_fora", length = 6)
	private String cfop_fora;  // 11
	@Column(name = "prod_ucom", length = 21)
	private String prod_UCom; // 12
	@Column(name = "prod_qcom", length = 21)
	private Double prod_QCom; // 13 quantidade
	@Column(name = "prod_vun_com", length = 21)
	private String prod_VUnCom;
	@Column(name = "prod_vprod", length = 21)
	private String prod_VProd;// 14
	@Column(name = "valordevenda", length = 21)
	private String valordevenda; // 15
	@Column(name = "prod_ceantrib", length = 16)
	private String prod_CEANTrib;	// 16
	@Column(name = "prod_utrib", length = 21)
	private String prod_UTrib;  // 17
	@Column(name = "prod_qtrib", length = 14)
	private String prod_QTrib; // 18
	@Column(name = "prod_vun_trib", length = 21)
	private String prod_VUnTrib;    // 19
	@Column(name = "prod_v_frete", length = 21)
	private String prod_vFrete;     // 20
	@Column(name = "prod_v_seg", length = 21)
	private String prod_vSeg;     	// 21
	@Column(name = "prod_v_desc", length = 21)
	private String prod_vDesc;   	//22
	@Column(name = "prod_v_outro", length = 21)
	private String prod_vOutro;     //23
	@Column(length = 21)
	private String cst_csosn; // 24 ou csosn código situação e operaçao do simples nacional
	@Column(length = 5)
	private Double red_base_icms; // 25 porcentagem
	@Column(name = "prod_vbcicms", length = 21)
	private String prod_VBCIcms;
	@Column(length = 5)
	private String prod_PCIcms; //27 % INVERSAMENTE PROPORCIONAL prod_RedIcms
	@Column(name = "prod_ind_tot", length = 7)
	private String prod_IndTot;
	@Column(name="prod_vicms", length = 21)
	private String prod_VICMS;

    @Column(name = "prod_p_credsn", length = 5)
    private String prod_pCredSN;

    @Column(name = "prod_v_credicmssn", length = 21)
    private String prod_vCredICMSSN;

	@Column(length = 6)
	private Double icms;

    @Column(length = 6)
	private Double pis; // porcentagem ok

	@Column(length = 6)
	private Double ipi; // porcentagem 0k
	@Column(length = 21)
	private String prod_VIPI;
	@Column(length = 7)
	private String origem;
	@Column(name = "prod_modbc", length = 7)
	private String prod_modBC;
	@Column(name = "prod_cst", length = 7)
	private String prod_CST;
	private String fcp; // percentual
	@Column(name = "prod_vfcp", length = 21)
	private String prod_vFCP;
	@Column(length = 21)
	private String unit;
	private Double purchasevalue;
	private Double total_p_value; // valor total em preço de compra
    @Column(name = "gain_percentage")
	private Double gainPercentage; //
	@Column(length = 28)
	private String valordecompra; //
	private Double salevalue;
	private Double total_s_value; // valor total em preço de venda
	private Double stock;
	private Double stock_minimum; //
	@Column(length = 14)
	private String weight;
	@Column(length = 14)
	private String liquid_weight;
	@Column(length = 21)
	private String cst_ipi;
	@Column(length = 21)
	private String cst_cofins;
	@Column(length = 21)
	private String cst_pis;
	private Double cus_div;
	private Double m_obra;
	private Double mva; // margem valor agregado
	private Double porcento_lucro;
	private Double peso_bruto;
	private Double peso_liquido;
//	@Column(length = 28)
//	private String ean_valido_nfe;
	private Double commission;  //
	@Column(length = 10)
	private String status;
	@Column(name = "prod_c_prodanp", length = 21)
	private String prod_cProdANP;
	@Column(name = "prod_descanp", length = 21)
	private String prod_descANP;
	// combustíveis
	@Column(name = "prod_pglp", length = 21)
	private String prod_pGLP;
	@Column(name = "prod_pgnn", length = 21)
	private String prod_pGNn;
	@Column(name = "prod_pgni", length = 21)
	private String prod_pGNi;
	@Column(name = "prod_v_part", length = 21)
	private String prod_vPart;
	@Column(name = "prod_codif", length = 21)
	private String prod_CODIF;
	@Column(name = "prod_q_temp", length = 21)
	private String prod_qTemp;
	@Column(name = "prod_ufcomb", length = 4)
	private String prod_UFComb;
	@Column(name = "prod_qbcprod", length = 21)
	private String prod_qBCProd;
	@Column(name = "prod_v_aliq_prod", length = 21)
	private String prod_vAliqProd;
	@Column(name = "cnpj_fabricante", length = 21)
	private String cnpj_fabricante;
	@Column(name = "factory_name", length = 21)
	private String factory_name;

	@Column(name = "tag_sub_trib_imp", nullable = true)
	private Boolean tag_sub_trib_imp;
	@Column(columnDefinition = "TEXT")
	private String observations; //

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

	// cofins
	@Column(name="prod_cst_cofins", length = 6)
	private String prod_CST_Cofins;
	@Column(name="prod_vbc_confins", length = 21)
	private String prod_vBC_confins;
	@Column(name="cofins", length = 10)
	private String cofins;
	@Column(name="prod_vcofins", length = 21)
	private String prod_VCOFINS;
	@Column(name="prod_qbcofins", length = 18)
	private String prod_qBCofins;
	@Column(name="prod_vbcst_confins", length = 21)
	private String prod_vBCST_confins;
	@Column(name="prod_v_aliqstcofins", length = 18)
	private String prod_vAliqSTCofins;
	@Column(name="prod_vstcofins", length = 21)
	private String prod_vSTCofins;

	// partilha
	@Column(name="prod_uf_origem", length = 3)
	private String prod_UfOrigem;
	@Column(name ="prod_uf_destino", length = 3)
	private String prod_UfDestino;
	@Column(name="prod_vbcufdest", length = 21)
	private String prod_vBCUFDest;
	@Column(name="prod_vbcfcpufdest", length = 21)
	private String prod_vBCFCPUFDest;
	@Column(name="prod_pfcpufdest", length = 10)
	private String prod_pFCPUFDest;
	@Column(name="prod_picmsufdest", length = 10)
	private String prod_pICMSUFDest;
	@Column(name="prod_picmsinter", length = 10)
	private String prod_pICMSInter;
	@Column(name="prod_picmsinter_part", length = 10)
	private String prod_pICMSInterPart;
	@Column(name="prod_vfcpufdest", length = 21)
	private String prod_vFCPUFDest;
	@Column(name="prod_vicmsufdest", length = 21)
	private String prod_vICMSUFDest;
	@Column(name="prod_vicmsufreme", length = 21)
	private String prod_vICMSUFReme;




	public Products() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getGtin_valid() {
		return gtin_valid;
	}
	public void setGtin_valid(String gtin_valid) {
		this.gtin_valid = gtin_valid;
	}
	public String getCprod() {
		return cprod;
	}
	public void setCprod(String cprod) {
		this.cprod = cprod;
	}
	public String getCean() {
		return cean;
	}

	public void setCean(String cean) {
		this.cean = cean;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getProd_NVE() {
		return prod_NVE;
	}

	public void setProd_NVE(String prod_NVE) {
		this.prod_NVE = prod_NVE;
	}

	public String getCest() {
		return cest;
	}

	public void setCest(String cest) {
		this.cest = cest;
	}

	public String getIndescala() {
		return indescala;
	}

	public void setIndescala(String indescala) {
		this.indescala = indescala;
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

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getCfop_fora() {
		return cfop_fora;
	}

	public void setCfop_fora(String cfop_fora) {
		this.cfop_fora = cfop_fora;
	}

	public Boolean getTag_sub_trib_imp() {
		return tag_sub_trib_imp;
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

	public String getProd_UCom() {
		return prod_UCom;
	}

	public void setProd_UCom(String prod_UCom) {
		this.prod_UCom = prod_UCom;
	}

	public Double getProd_QCom() {
		return prod_QCom;
	}

	public void setProd_QCom(Double prod_QCom) {
		this.prod_QCom = prod_QCom;
	}

	public String getProd_VUnCom() {
		return prod_VUnCom;
	}

	public void setProd_VUnCom(String prod_VUnCom) {
		this.prod_VUnCom = prod_VUnCom;
	}

	public String getProd_VProd() {
		return prod_VProd;
	}

	public void setProd_VProd(String prod_VProd) {
		this.prod_VProd = prod_VProd;
	}

	public String getValordevenda() {
		return valordevenda;
	}

	public void setValordevenda(String valordevenda) {
		this.valordevenda = valordevenda;
	}

	public String getProd_CEANTrib() {
		return prod_CEANTrib;
	}

	public void setProd_CEANTrib(String prod_CEANTrib) {
		this.prod_CEANTrib = prod_CEANTrib;
	}

	public String getProd_UTrib() {
		return prod_UTrib;
	}

	public void setProd_UTrib(String prod_UTrib) {
		this.prod_UTrib = prod_UTrib;
	}

	public String getProd_QTrib() {
		return prod_QTrib;
	}

	public void setProd_QTrib(String prod_QTrib) {
		this.prod_QTrib = prod_QTrib;
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

	public String getCst_csosn() {
		return cst_csosn;
	}

	public void setCst_csosn(String cst_csosn) {
		this.cst_csosn = cst_csosn;
	}

	public Double getRed_base_icms() {
		return red_base_icms;
	}

	public void setRed_base_icms(Double red_base_icms) {
		this.red_base_icms = red_base_icms;
	}

	public String getProd_VBCIcms() {
		return prod_VBCIcms;
	}

	public void setProd_VBCIcms(String prod_VBCIcms) {
		this.prod_VBCIcms = prod_VBCIcms;
	}

	public String getProd_PCIcms() {
		return prod_PCIcms;
	}

	public void setProd_PCIcms(String prod_PCIcms) {
		this.prod_PCIcms = prod_PCIcms;
	}

	public String getProd_IndTot() {
		return prod_IndTot;
	}

	public void setProd_IndTot(String prod_IndTot) {
		this.prod_IndTot = prod_IndTot;
	}

	public String getProd_VICMS() {
		return prod_VICMS;
	}

	public void setProd_VICMS(String prod_VICMS) {
		this.prod_VICMS = prod_VICMS;
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

    public Double getIcms() {
		return icms;
	}

	public void setIcms(Double icms) {
		this.icms = icms;
	}

	public Double getPis() {
		return pis;
	}

	public void setPis(Double pis) {
		this.pis = pis;
	}


	public Double getIpi() {
		return ipi;
	}

	public void setIpi(Double ipi) {
		this.ipi = ipi;
	}

	public String getProd_VIPI() {
		return prod_VIPI;
	}

	public void setProd_VIPI(String prod_VIPI) {
		this.prod_VIPI = prod_VIPI;
	}


	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
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

	public String getFcp() {
		return fcp;
	}

	public void setFcp(String fcp) {
		this.fcp = fcp;
	}

	public String getProd_vFCP() {
		return prod_vFCP;
	}

	public void setProd_vFCP(String prod_vFCP) {
		this.prod_vFCP = prod_vFCP;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPurchasevalue() {
		return purchasevalue;
	}

	public void setPurchasevalue(Double purchasevalue) {
		this.purchasevalue = purchasevalue;
	}

	public Double getTotal_p_value() {
		return total_p_value;
	}

	public void setTotal_p_value(Double total_p_value) {
		this.total_p_value = total_p_value;
	}

	public Double getGainPercentage() {
		return gainPercentage;
	}

	public void setGainPercentage(Double gainPercentage) {
		this.gainPercentage = gainPercentage;
	}

	public String getValordecompra() {
		return valordecompra;
	}

	public void setValordecompra(String valordecompra) {
		this.valordecompra = valordecompra;
	}

	public Double getSalevalue() {
		return salevalue;
	}

	public void setSalevalue(Double salevalue) {
		this.salevalue = salevalue;
	}

	public Double getTotal_s_value() {
		return total_s_value;
	}
	public void setTotal_s_value(Double total_s_value) {
		this.total_s_value = total_s_value;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Double getStock_minimum() {
		return stock_minimum;
	}

	public void setStock_minimum(Double stock_minimum) {
		this.stock_minimum = stock_minimum;
	}

	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLiquid_weight() {
		return liquid_weight;
	}
	public void setLiquid_weight(String liquid_weight) {
		this.liquid_weight = liquid_weight;
	}

	public String getCst_ipi() {
		return cst_ipi;
	}

	public void setCst_ipi(String cst_ipi) {
		this.cst_ipi = cst_ipi;
	}

	public String getCst_cofins() {
		return cst_cofins;
	}

	public void setCst_cofins(String cst_cofins) {
		this.cst_cofins = cst_cofins;
	}

	public String getCst_pis() {
		return cst_pis;
	}

	public void setCst_pis(String cst_pis) {
		this.cst_pis = cst_pis;
	}

	public Double getCus_div() {
		return cus_div;
	}

	public void setCus_div(Double cus_div) {
		this.cus_div = cus_div;
	}

	public Double getM_obra() {
		return m_obra;
	}

	public void setM_obra(Double m_obra) {
		this.m_obra = m_obra;
	}

	public Double getMva() {
		return mva;
	}

	public void setMva(Double mva) {
		this.mva = mva;
	}

	public Double getPorcento_lucro() {
		return porcento_lucro;
	}

	public void setPorcento_lucro(Double porcento_lucro) {
		this.porcento_lucro = porcento_lucro;
	}

	public Double getPeso_bruto() {
		return peso_bruto;
	}

	public void setPeso_bruto(Double peso_bruto) {
		this.peso_bruto = peso_bruto;
	}

	public Double getPeso_liquido() {
		return peso_liquido;
	}

	public void setPeso_liquido(Double peso_liquido) {
		this.peso_liquido = peso_liquido;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getProd_UFComb() {
		return prod_UFComb;
	}
	public void setProd_UFComb(String prod_UFComb) {
		this.prod_UFComb = prod_UFComb;
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

	public String getCnpj_fabricante() {
		return cnpj_fabricante;
	}

	public void setCnpj_fabricante(String cnpj_fabricante) {
		this.cnpj_fabricante = cnpj_fabricante;
	}

	public String getFactory_name() {
		return factory_name;
	}

	public void setFactory_name(String factory_name) {
		this.factory_name = factory_name;
	}

	public Boolean isTag_sub_trib_imp() {
		return tag_sub_trib_imp;
	}

	public void setTag_sub_trib_imp(Boolean tag_sub_trib_imp) {
		this.tag_sub_trib_imp = tag_sub_trib_imp;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
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

	public String getCofins() {
		return cofins;
	}

	public void setCofins(String cofins) {
		this.cofins = cofins;
	}

	public String getProd_VCOFINS() {
		return prod_VCOFINS;
	}

	public void setProd_VCOFINS(String prod_VCOFINS) {
		this.prod_VCOFINS = prod_VCOFINS;
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

	public String getProd_UfOrigem() {
		return prod_UfOrigem;
	}

	public void setProd_UfOrigem(String prod_UfOrigem) {
		this.prod_UfOrigem = prod_UfOrigem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Products{" +
				"id=" + id +
				", register_date='" + register_date + '\'' +
				", brand='" + brand + '\'' +
				", gtin_valid='" + gtin_valid + '\'' +
				", cprod='" + cprod + '\'' +
				", cean='" + cean + '\'' +
				", description='" + description + '\'' +
				", ncm='" + ncm + '\'' +
				", prod_NVE='" + prod_NVE + '\'' +
				", cest='" + cest + '\'' +
				", indescala='" + indescala + '\'' +
				", prod_CNPJFab='" + prod_CNPJFab + '\'' +
				", prod_cBenef='" + prod_cBenef + '\'' +
				", prod_EXTIPI='" + prod_EXTIPI + '\'' +
				", cfop='" + cfop + '\'' +
				", prod_UCom='" + prod_UCom + '\'' +
				", prod_QCom=" + prod_QCom +
				", prod_VUnCom='" + prod_VUnCom + '\'' +
				", valordevenda='" + valordevenda + '\'' +
				", prod_CEANTrib='" + prod_CEANTrib + '\'' +
				", prod_UTrib='" + prod_UTrib + '\'' +
				", prod_QTrib='" + prod_QTrib + '\'' +
				", prod_VUnTrib='" + prod_VUnTrib + '\'' +
				", prod_vFrete='" + prod_vFrete + '\'' +
				", prod_vSeg='" + prod_vSeg + '\'' +
				", prod_vDesc='" + prod_vDesc + '\'' +
				", prod_vOutro='" + prod_vOutro + '\'' +
				", cst_csosn='" + cst_csosn + '\'' +
				", red_base_icms=" + red_base_icms +
				", prod_VBCIcms='" + prod_VBCIcms + '\'' +
				", prod_PCIcms='" + prod_PCIcms + '\'' +
				", prod_IndTot='" + prod_IndTot + '\'' +
				", prod_VICMS='" + prod_VICMS + '\'' +
				", icms=" + icms +
				", pis=" + pis +
				", prod_VPIS='" + prod_VPIS + '\'' +
				", ipi=" + ipi +
				", prod_VIPI='" + prod_VIPI + '\'' +
				", cofins=" + cofins +
				", prod_VCOFINS='" + prod_VCOFINS + '\'' +
				", origem='" + origem + '\'' +
				", prod_modBC='" + prod_modBC + '\'' +
				", fcp='" + fcp + '\'' +
				", prod_vFCP='" + prod_vFCP + '\'' +
				", unit='" + unit + '\'' +
				", purchasevalue=" + purchasevalue +
				", total_p_value=" + total_p_value +
				", gainPercentage=" + gainPercentage +
				", valordecompra='" + valordecompra + '\'' +
				", salevalue=" + salevalue +
				", total_s_value=" + total_s_value +
				", stock=" + stock +
				", stock_minimum=" + stock_minimum +
				", weight='" + weight + '\'' +
				", liquid_weight='" + liquid_weight + '\'' +
				", cst_ipi='" + cst_ipi + '\'' +
				", cst_cofins='" + cst_cofins + '\'' +
				", cst_pis='" + cst_pis + '\'' +
				", cus_div=" + cus_div +
				", m_obra=" + m_obra +
				", mva=" + mva +
				", porcento_lucro=" + porcento_lucro +
				", peso_bruto=" + peso_bruto +
				", peso_liquido=" + peso_liquido +
				", commission=" + commission +
				", status='" + status + '\'' +
				", prod_cProdANP='" + prod_cProdANP + '\'' +
				", prod_descANP='" + prod_descANP + '\'' +
				", prod_pGLP='" + prod_pGLP + '\'' +
				", prod_pGNn='" + prod_pGNn + '\'' +
				", prod_pGNi='" + prod_pGNi + '\'' +
				", prod_vPart='" + prod_vPart + '\'' +
				", prod_CODIF='" + prod_CODIF + '\'' +
				", prod_qTemp='" + prod_qTemp + '\'' +
				", prod_UFComb='" + prod_UFComb + '\'' +
				", prod_qBCProd='" + prod_qBCProd + '\'' +
				", prod_vAliqProd='" + prod_vAliqProd + '\'' +
				", cnpj_fabricante='" + cnpj_fabricante + '\'' +
				", factory_name='" + factory_name + '\'' +
				", tag_sub_trib_imp=" + tag_sub_trib_imp +
				", observations='" + observations + '\'' +
				'}';
	}
}






