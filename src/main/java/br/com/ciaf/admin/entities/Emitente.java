package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "emitente")
public class Emitente implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 99, name = "emit_xnome")
	private String emit_XNome;
	@Column(length = 28, name = "emit_cnpj")
	private String emit_cnpj;
	@Column(length = 28, name = "emit_cpf")
	private String emit_cpf;
	@Column(length = 28, name = "emit_ie")
	private String emit_IE;
	@Column(length = 28, name = "emit_rg")
	private String emit_rg;
	@Column(length = 2, name = "emit_crt")
	private String emit_CRT;
	@Column(length = 99, name = "emit_xlgr")
	private String emit_XLgr;
	@Column(length = 35, name = "emit_nro")
	private String emit_Nro;
	@Column(length = 60, name = "emit_xcpl")
	private String emit_XCpl;
	@Column(length = 70, name = "emit_xbairro")
	private String emit_XBairro;
	@Column(length = 60, name = "emit_cmun")
	private String emit_CMun;
	@Column(length = 77, name = "emit_xmun")
	private String emit_XMun;
	@Column(length = 6, name = "emit_uf")
	private String emit_UF;
	@Column(length = 12, name = "emit_cep")
	private String emit_CEP;
	@Column(length = 99, name = "emit_email")
	private String emit_email;
	@Column(length = 21)
	private String phonenumber;
	@Column(length = 21)
	private String cellnumber;

	@Column(length = 99)
	private String senha_cert;

	@Column(name = "is_active")
	private Boolean is_active = false;

	private Boolean is_paid = false;

	@Column(name = "start")
	private LocalDate start = LocalDate.now();

	@Column(name = "payment_date")
	private LocalDate paymentDate;

	// to NFe
	private Integer serie;
	private Integer number_last;

	// to NFSE
	@Column(name = "inscricao_municipal", length = 20)
	private String inscricao_municipal;

	@Column(name = "nfse_op_simp_nac", length = 1)
	private String nfseOpSimpNac;

	@Column(name = "nfse_reg_esp_trib", length = 2)
	private String nfseRegEspTrib;

	@Column(name = "nfse_reg_ap_trib_sn", length = 2)
	private String nfseRegApTribSn;

	@Column(name = "nfse_c_nbs_padrao", length = 9)
	private String nfseCNbsPadrao;

	//params nfse
	// ----------------------------
	// NFS-e (SEFIN) - params usados no EmissaoParams (DpsMapper)
	// ----------------------------

	@Column(name = "nfse_tp_emit", length = 2)
	private String nfseTpEmit;

	@Column(name = "nfse_trib_issqn", length = 2)
	private String nfseTribIssqn;

	@Column(name = "nfse_tp_ret_issqn_nao_retido", length = 2)
	private String nfseTpRetIssqnNaoRetido;

	@Column(name = "nfse_tp_ret_issqn_retido", length = 2)
	private String nfseTpRetIssqnRetido;

	@Column(name = "nfse_ind_tot_trib", length = 2)
	private String nfseIndTotTrib;

	/**
	 * Regime Especial de Tributação para NFS-e (padrão ABRASF / NFe-Cidades)
	 * Valores: 1=Microempresa Municipal, 2=Estimativa, 3=Sociedade de Profissionais,
	 *          4=Cooperativa, 5=MEI, 6=ME/EPP
	 */
	@Column(name = "regime_especial_trib", length = 1)
	private String regime_especial_trib;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "LONGBLOB")
	private byte[] image;

	public Emitente() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmit_XNome() {
		return emit_XNome;
	}

	public void setEmit_XNome(String emit_XNome) {
		this.emit_XNome = emit_XNome;
	}

	public String getEmit_cnpj() {
		return emit_cnpj;
	}

	public void setEmit_cnpj(String emit_cnpj) {
		this.emit_cnpj = emit_cnpj;
	}

	public String getEmit_cpf() {
		return emit_cpf;
	}

	public void setEmit_cpf(String emit_cpf) {
		this.emit_cpf = emit_cpf;
	}

	public String getEmit_IE() {
		return emit_IE;
	}

	public void setEmit_IE(String emit_IE) {
		this.emit_IE = emit_IE;
	}

	public String getEmit_rg() {
		return emit_rg;
	}

	public void setEmit_rg(String emit_rg) {
		this.emit_rg = emit_rg;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmit_email() {
		return emit_email;
	}

	public void setEmit_email(String emit_email) {
		this.emit_email = emit_email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCellnumber() {
		return cellnumber;
	}

	public void setCellnumber(String cellnumber) {
		this.cellnumber = cellnumber;
	}

	public String getSenha_cert() {
		return senha_cert;
	}

	public void setSenha_cert(String senha_cert) {
		this.senha_cert = senha_cert;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public Boolean getIs_paid() {
		return is_paid;
	}

	public void setIs_paid(Boolean is_paid) {
		this.is_paid = is_paid;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public Integer getNumber_last() {
		return number_last;
	}

	public void setNumber_last(Integer number_last) {
		this.number_last = number_last;
	}

	public String getInscricao_municipal() {
		return inscricao_municipal;
	}

	public void setInscricao_municipal(String inscricao_municipal) {
		this.inscricao_municipal = inscricao_municipal;
	}

	public String getRegime_especial_trib() {
		return regime_especial_trib;
	}

	public void setRegime_especial_trib(String regime_especial_trib) {
		this.regime_especial_trib = regime_especial_trib;
	}

	public String getInscricaoMunicipal() {
		return inscricao_municipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricao_municipal = inscricaoMunicipal;
	}

	public String getNfseOpSimpNac() {
		return nfseOpSimpNac;
	}

	public void setNfseOpSimpNac(String nfseOpSimpNac) {
		this.nfseOpSimpNac = nfseOpSimpNac;
	}

	public String getNfseRegEspTrib() {
		return nfseRegEspTrib;
	}

	public void setNfseRegEspTrib(String nfseRegEspTrib) {
		this.nfseRegEspTrib = nfseRegEspTrib;
	}

	public String getNfseRegApTribSn() {
		return nfseRegApTribSn;
	}

	public void setNfseRegApTribSn(String nfseRegApTribSn) {
		this.nfseRegApTribSn = nfseRegApTribSn;
	}

	public String getNfseCNbsPadrao() {
		return nfseCNbsPadrao;
	}

	public void setNfseCNbsPadrao(String nfseCNbsPadrao) {
		this.nfseCNbsPadrao = nfseCNbsPadrao;
	}

	public String getNfseTpEmit() {
		return nfseTpEmit;
	}

	public void setNfseTpEmit(String nfseTpEmit) {
		this.nfseTpEmit = nfseTpEmit;
	}

	public String getNfseTribIssqn() {
		return nfseTribIssqn;
	}

	public void setNfseTribIssqn(String nfseTribIssqn) {
		this.nfseTribIssqn = nfseTribIssqn;
	}

	public String getNfseTpRetIssqnNaoRetido() {
		return nfseTpRetIssqnNaoRetido;
	}

	public void setNfseTpRetIssqnNaoRetido(String nfseTpRetIssqnNaoRetido) {
		this.nfseTpRetIssqnNaoRetido = nfseTpRetIssqnNaoRetido;
	}

	public String getNfseTpRetIssqnRetido() {
		return nfseTpRetIssqnRetido;
	}

	public void setNfseTpRetIssqnRetido(String nfseTpRetIssqnRetido) {
		this.nfseTpRetIssqnRetido = nfseTpRetIssqnRetido;
	}

	public String getNfseIndTotTrib() {
		return nfseIndTotTrib;
	}

	public void setNfseIndTotTrib(String nfseIndTotTrib) {
		this.nfseIndTotTrib = nfseIndTotTrib;
	}

	public String getRegimeEspecialTrib() {
		return regime_especial_trib;
	}

	public void setRegimeEspecialTrib(String regimeEspecialTrib) {
		this.regime_especial_trib = regimeEspecialTrib;
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
		Emitente other = (Emitente) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Emitente{" +
				"id=" + id +
				", emit_XNome='" + emit_XNome + '\'' +
				", emit_cnpj='" + emit_cnpj + '\'' +
				", emit_cpf='" + emit_cpf + '\'' +
				", emit_IE='" + emit_IE + '\'' +
				", emit_rg='" + emit_rg + '\'' +
				", emit_CRT='" + emit_CRT + '\'' +
				", emit_XLgr='" + emit_XLgr + '\'' +
				", emit_Nro='" + emit_Nro + '\'' +
				", emit_XCpl='" + emit_XCpl + '\'' +
				", emit_XBairro='" + emit_XBairro + '\'' +
				", emit_CMun='" + emit_CMun + '\'' +
				", emit_XMun='" + emit_XMun + '\'' +
				", emit_UF='" + emit_UF + '\'' +
				", emit_CEP='" + emit_CEP + '\'' +
				", emit_email='" + emit_email + '\'' +
				", is_active=" + is_active +
				", start=" + start +
				'}';
	}
}
