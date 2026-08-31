package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "vendasitems")
public class VendasItems implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long product_id; //
	private Long ref;
	@Column(name="prod_ncm", length = 40)
	private String prod_NCM;
	@Column(name="prod_cest", length = 40)
	private String prod_CEST;
	@Column(name="prod_cfop", length = 21)
	private String prod_CFOP;
	@Column(name="prod_cean", length = 40)
	private String prod_CEAN; //
	@Column(name="prod_xprod", length = 60)
	private String prod_XProd; // descrição
	@Column(name="prod_qcom", length = 21)
	private Integer prod_QCom; // quantidade
	@Column(name="prod_ucom", length = 21)
	private String prod_UCom; // quantidade
	@Column(name = "prod_picms", length = 4)
	private String prod_PICMS;
	@Column(name="prod_vicms", length = 21)
	private String prod_VICMS;
	@Column(name = "prod_ppis", length = 4)
	private String prod_PPIS;
	@Column(name = "prod_vpis", length = 21)
	private String prod_VPIS;
	@Column(name = "prod_vcofins", length = 21)
	private String prod_VCOFINS;
    @Column(name = "prod_vun_com")
	private double prod_VUnCom;
	@Column(name="prod_vprod", length = 21)
	private String prod_VProd; // 15
    @Column(name = "prod_v_desc")
	private double prod_vDesc; // valor
	@Column(name = "total_price")
    private double totalPrice;
	private Long vendaid;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "venda_id")
	private Vendas venda;

	public VendasItems() {
	}

	public VendasItems(Long id, Long product_id, Long ref, String prod_NCM, String prod_CEST, String prod_CFOP, String prod_CEAN, String prod_XProd, Integer prod_QCom, String prod_PICMS, String prod_VICMS, String prod_PPIS, String prod_VPIS, String prod_VCOFINS, double prod_VUnCom, double totalPrice, Long vendaid, Vendas venda) {
		this.id = id;
		this.product_id = product_id;
		this.ref = ref;
		this.prod_NCM = prod_NCM;
		this.prod_CEST = prod_CEST;
		this.prod_CFOP = prod_CFOP;
		this.prod_CEAN = prod_CEAN;
		this.prod_XProd = prod_XProd;
		this.prod_QCom = prod_QCom;
		this.prod_PICMS = prod_PICMS;
		this.prod_VICMS = prod_VICMS;
		this.prod_PPIS = prod_PPIS;
		this.prod_VPIS = prod_VPIS;
		this.prod_VCOFINS = prod_VCOFINS;
		this.prod_VUnCom = prod_VUnCom;
		this.totalPrice = totalPrice;
		this.vendaid = vendaid;
		this.venda = venda;
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

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public Long getRef() {
		return ref;
	}

	public void setRef(Long ref) {
		this.ref = ref;
	}

	public String getProd_NCM() {
		return prod_NCM;
	}

	public void setProd_NCM(String prod_NCM) {
		this.prod_NCM = prod_NCM;
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

	public String getProd_CEAN() {
		return prod_CEAN;
	}

	public void setProd_CEAN(String prod_CEAN) {
		this.prod_CEAN = prod_CEAN;
	}

	public String getProd_XProd() {
		return prod_XProd;
	}

	public void setProd_XProd(String prod_XProd) {
		this.prod_XProd = prod_XProd;
	}

	public Integer getProd_QCom() {
		return prod_QCom;
	}

	public void setProd_QCom(Integer prod_QCom) {
		this.prod_QCom = prod_QCom;
	}

	public String getProd_UCom() {
		return prod_UCom;
	}

	public void setProd_UCom(String prod_UCom) {
		this.prod_UCom = prod_UCom;
	}

	public String getProd_PICMS() {
		return prod_PICMS;
	}

	public void setProd_PICMS(String prod_PICMS) {
		this.prod_PICMS = prod_PICMS;
	}

	public String getProd_VICMS() {
		return prod_VICMS;
	}
	public void setProd_VICMS(String prod_VICMS) {
		this.prod_VICMS = prod_VICMS;
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

	public double getProd_VUnCom() {
		return prod_VUnCom;
	}

	public void setProd_VUnCom(double prod_VUnCom) {
		this.prod_VUnCom = prod_VUnCom;
	}

	public String getProd_VProd() {
		return prod_VProd;
	}

	public void setProd_VProd(String prod_VProd) {
		this.prod_VProd = prod_VProd;
	}

	public double getProd_vDesc() {
		return prod_vDesc;
	}

	public void setProd_vDesc(double prod_vDesc) {
		this.prod_vDesc = prod_vDesc;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	public Long getVendaid() {
		return vendaid;
	}

	public void setVendaid(Long vendaid) {
		this.vendaid = vendaid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendasItems other = (VendasItems) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "VendasItems{" +
				"id=" + id +
				", product_id=" + product_id +
				", ref=" + ref +
				", prod_NCM='" + prod_NCM + '\'' +
				", prod_CEST='" + prod_CEST + '\'' +
				", prod_CFOP='" + prod_CFOP + '\'' +
				", prod_CEAN='" + prod_CEAN + '\'' +
				", prod_XProd='" + prod_XProd + '\'' +
				", prod_QCom=" + prod_QCom +
				", prod_PICMS='" + prod_PICMS + '\'' +
				", prod_VICMS='" + prod_VICMS + '\'' +
				", prod_PPIS='" + prod_PPIS + '\'' +
				", prod_VPIS='" + prod_VPIS + '\'' +
				", prod_VCOFINS='" + prod_VCOFINS + '\'' +
				", prod_VUnCom=" + prod_VUnCom +
				", totalPrice=" + totalPrice +
				", vendaid=" + vendaid +
				'}';
	}
}
