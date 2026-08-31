package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.ciaf.admin.enums.MovType;

@Entity
@Table(name = "caixadiario")
public class CaixaDiario extends MyEmitenteId implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date datamov_caixa;
	private boolean opened;
	private int codigo;
	private Integer nr_venda;
	private Integer nr_nfce;
	private double valor;
	@Column(length = 90)
	private String documento;
	@Column(length = 90)
	private String emitente;
	@Column(name ="cpf_cnpj", length = 21)
	private String cpfCnpj;
	@Column(length = 21)
	private String fone;
	@Column(length = 21)
	private String cellphone;
	private String observacao;
	private double saldo = 0.00;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime dateCreated;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "mov_type")
	private MovType movType;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "caixa", cascade = CascadeType.ALL)
	private Historico historico;
	
	@OneToOne
	@JoinColumn(name = "caixa_id")
	private Persons persons;
	
//	@JsonBackReference
//	@OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vendas_id")
//    private Vendas vendacaixa;
	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL, orphanRemoval = false, fetch=FetchType.EAGER)
//	private List<Vendas> venda = new ArrayList<>();
	
	public CaixaDiario(){
		this.datamov_caixa = new Date();
	}

	public CaixaDiario(Long id, Date datamov_caixa, boolean opened, int codigo, double valor, String documento, String emitente, String cpfCnpj, String fone, String cellphone, String observacao, double saldo, LocalDateTime dateCreated, LocalDateTime updatedAt, MovType movType, Historico historico, Persons persons) {
		this.id = id;
		this.datamov_caixa = datamov_caixa;
		this.opened = opened;
		this.codigo = codigo;
		this.valor = valor;
		this.documento = documento;
		this.emitente = emitente;
		this.cpfCnpj = cpfCnpj;
		this.fone = fone;
		this.cellphone = cellphone;
		this.observacao = observacao;
		this.saldo = saldo;
		this.dateCreated = dateCreated;
		this.updatedAt = updatedAt;
		this.movType = movType;
		this.historico = historico;
		this.persons = persons;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatamov_caixa() {
		return datamov_caixa;
	}

	public void setDatamov_caixa(Date datamov_caixa) {
		this.datamov_caixa = datamov_caixa;
	}

	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Integer getNr_venda() {
		return nr_venda;
	}

	public void setNr_venda(Integer nr_venda) {
		this.nr_venda = nr_venda;
	}

	public Integer getNr_nfce() {
		return nr_nfce;
	}

	public void setNr_nfce(Integer nr_nfce) {
		this.nr_nfce = nr_nfce;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
			
	public String getEmitente() {
		return emitente;
	}

	public void setEmitente(String emitente) {
		this.emitente = emitente;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
		
	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}
		
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
		
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public MovType getMovType() {
		return movType;
	}

	public void setMovType(MovType movType) {
		this.movType = movType;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	
//	public Vendas getVendacaixa() {
//		return vendacaixa;
//	}
//
//	public void setVendacaixa(Vendas vendacaixa) {
//		this.vendacaixa = vendacaixa;
//	}

	public Persons getPersons() {
		return persons;
	}

	public void setPersons(Persons persons) {
		this.persons = persons;
	}

	@Override
	public String toString() {
		return "CaixaDiario [id=" + id + ", datamov_caixa=" + datamov_caixa + ", opened=" + opened + ", codigo=" + codigo
				+ ", nr_venda=" + nr_venda + ", nr_nfce=" + nr_nfce + ", valor=" + valor + ", documento=" + documento
				+ ", emitente=" + emitente + ", cpfCnpj=" + cpfCnpj + ", fone=" + fone + ", cellphone=" + cellphone
				+ ", observacao=" + observacao + ", saldo=" + saldo + ", dateCreated=" + dateCreated + ", updatedAt="
				+ updatedAt + ", movType=" + movType + ", historico=" + historico + ", persons=" + persons + "]";
	}


}
