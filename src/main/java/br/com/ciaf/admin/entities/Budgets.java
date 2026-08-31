package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "budgets")
public class Budgets extends MyEmitenteId implements Serializable  {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer nr_budget;
	private Long client_id;
	private Long receipts_id;
	@Column( length = 80)
	private String client;
	@Column( length = 30)
	private String client_cell;
	@Column( length = 66)
	private String client_email;
	@Column( length = 66)
	private String vendedor;
	@Column( length = 33)
	private String data;
	@Column( length = 66)
	private String status;
	@Column( length = 80)
	private String prazo;
	@Column(length = 35)
	private String validade;
	@Column(length = 90)
	private String aprovado_por;
	@Column(length = 35)
	private String data_aprovacao;
	@Column(columnDefinition = "TEXT")
	private String observacao;
	private Double valor;
	private Double desconto;
	private Boolean parcelado;
	private Boolean saved_b;
	private boolean pdf;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime dateCreated;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@JsonManagedReference
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<BudgetsItems> itens = new ArrayList<>();

//	@JsonManagedReference
//	@OneToOne(mappedBy = "paybudget", cascade = CascadeType.ALL)
//	private BudgetsPagamentos mybudgetspg;

	public Budgets() {
	}
		
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNr_budget() {
		return nr_budget;
	}

	public void setNr_budget(Integer nr_budget) {
		this.nr_budget = nr_budget;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public Long getReceipts_id() {
		return receipts_id;
	}

	public void setReceipts_id(Long receipts_id) {
		this.receipts_id = receipts_id;
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

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getClient_cell() {
		return client_cell;
	}
	public void setClient_cell(String client_cell) {
		this.client_cell = client_cell;
	}
	public String getClient_email() {
		return client_email;
	}
	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}	
	public String getData() {
		return data;
	}	
	public void setData(String data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrazo() {
		return prazo;
	}
	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}
	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getAprovado_por() {
		return aprovado_por;
	}

	public void setAprovado_por(String aprovado_por) {
		this.aprovado_por = aprovado_por;
	}

	public String getData_aprovacao() {
		return data_aprovacao;
	}

	public void setData_aprovacao(String data_aprovacao) {
		this.data_aprovacao = data_aprovacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Boolean getParcelado() {
		return parcelado;
	}

	public void setParcelado(Boolean parcelado) {
		this.parcelado = parcelado;
	}

	public Boolean getSaved_b() {
		return saved_b;
	}

	public void setSaved_b(Boolean saved_b) {
		this.saved_b = saved_b;
	}

	public List<BudgetsItems> getItens() {
		return itens;
	}

	public void setItens(List<BudgetsItems> itens) {
		this.itens = itens;
	}
//
//	public BudgetsPagamentos getMybudgetspg() {
//		return mybudgetspg;
//	}

//	public void setMybudgetspg(BudgetsPagamentos mybudgetspg) {
//		this.mybudgetspg = mybudgetspg;
//	}

	public boolean isPdf() {
		return pdf;
	}

	public void setPdf(boolean pdf) {
		this.pdf = pdf;
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
		Budgets other = (Budgets) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Budgets{" +
				"id=" + id +
				", nr_budget=" + nr_budget +
				", client_id=" + client_id +
				", receipts_id=" + receipts_id +
				", client='" + client + '\'' +
				", client_cell='" + client_cell + '\'' +
				", client_email='" + client_email + '\'' +
				", vendedor='" + vendedor + '\'' +
				", data='" + data + '\'' +
				", status='" + status + '\'' +
				", prazo='" + prazo + '\'' +
				", validade='" + validade + '\'' +
				", aprovado_por='" + aprovado_por + '\'' +
				", data_aprovacao='" + data_aprovacao + '\'' +
				", observacao='" + observacao + '\'' +
				", valor=" + valor +
				", desconto=" + desconto +
				", parcelado=" + parcelado +
				", saved_b=" + saved_b +
				", pdf=" + pdf +
				", dateCreated=" + dateCreated +
				", updatedAt=" + updatedAt +
				", itens=" + itens +
				'}';
	}
}
