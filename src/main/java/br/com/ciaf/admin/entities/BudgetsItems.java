package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "budgetsitems")
public class BudgetsItems implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long product_id;
	private Long ref;
    @Column(name = "brand", length = 75)
    private String brand;
	@Column(length = 80)
	private String description;
    @Column(name = "prod_cprod", length = 21)
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

    @Column(name = "prod_vicmsdeson", length = 21)
    private String prod_vICMSDeson;
    @Column(name = "total_price", length = 21)
    private Double totalPrice; // 40
			
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "budget_id")
	private Budgets budget;
	
	public BudgetsItems(){}

	public Budgets getBudget() {
		return budget;
	}

	public void setBudget(Budgets budget) {
		this.budget = budget;
	}
			
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Long getRef() {
		return ref;
	}

	public void setRef(Long ref) {
		this.ref = ref;
	}

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProd_NCM() {
		return prod_NCM;
	}

	public void setProd_NCM(String prod_NCM) {
		this.prod_NCM = prod_NCM;
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

	public String getProd_UCom() {
		return prod_UCom;
	}

	public void setProd_UCom(String prod_UCom) {
		this.prod_UCom = prod_UCom;
	}

	public String getProd_VProd() {
		return prod_VProd;
	}

	public void setProd_VProd(String prod_VProd) {
		this.prod_VProd = prod_VProd;
	}

	public Double getProd_QCom() {
		return prod_QCom;
	}

	public void setProd_QCom(Double prod_QCom) {
		this.prod_QCom = prod_QCom;
	}

	public double getProd_VUnCom() {
		return prod_VUnCom;
	}

	public void setProd_VUnCom(double prod_VUnCom) {
		this.prod_VUnCom = prod_VUnCom;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setProd_VUnCom(Double prod_VUnCom) {
        this.prod_VUnCom = prod_VUnCom;
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

    public String getProd_pBC_ICMS() {
        return prod_pBC_ICMS;
    }

    public void setProd_pBC_ICMS(String prod_pBC_ICMS) {
        this.prod_pBC_ICMS = prod_pBC_ICMS;
    }

    public String getProd_pRedBC_ICMS() {
        return prod_pRedBC_ICMS;
    }

    public void setProd_pRedBC_ICMS(String prod_pRedBC_ICMS) {
        this.prod_pRedBC_ICMS = prod_pRedBC_ICMS;
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

    public String getProd_pIPI() {
        return prod_pIPI;
    }

    public void setProd_pIPI(String prod_pIPI) {
        this.prod_pIPI = prod_pIPI;
    }

    public String getProd_VIPI() {
        return prod_VIPI;
    }

    public void setProd_VIPI(String prod_VIPI) {
        this.prod_VIPI = prod_VIPI;
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

    public String getProd_vICMSDeson() {
        return prod_vICMSDeson;
    }

    public void setProd_vICMSDeson(String prod_vICMSDeson) {
        this.prod_vICMSDeson = prod_vICMSDeson;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
		BudgetsItems other = (BudgetsItems) obj;
		return Objects.equals(id, other.id);
	}
			
}
