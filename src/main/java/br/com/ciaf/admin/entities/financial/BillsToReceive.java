package br.com.ciaf.admin.entities.financial;

import br.com.ciaf.admin.entities.MyEmitenteId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "areceber")
public class BillsToReceive extends MyEmitenteId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long venda_id;
	@Column(nullable = true)
	private Integer nr_venda;
	@Column(nullable = true)
	private Integer nr_nfce;
	@Column(length = 16)
	private String dateofoperation;
	@Column(length = 77)
	private String devedor;
	@Column(length = 60)
	private String type;
	@Column(length = 90)
	private String description;
	@Column(length = 16)
	private String duedate;
	@JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
	private LocalDate vencimento;
	@Column(length = 22)
	private String value;
	@Column(length = 22)
	private String paid_value;
	@Column(length = 21)
	private String addition;
	@Column(length = 21)
	private String discount;
	@Column(length = 22)
	private String final_value;
	private Double final_value_real;
	private boolean efetive;
	private boolean fixed_cost;
	@Column(columnDefinition="TEXT")
	private String observation;
	
	public BillsToReceive() {		
	}

	public BillsToReceive(Long id, Long venda_id, String dateofoperation, String devedor, String type, String description, String duedate, LocalDate vencimento, String value, String paid_value, String addition, String discount, String final_value, Double final_value_real, boolean efetive, boolean fixedCost, String observation) {
		this.id = id;
		this.venda_id = venda_id;
		this.dateofoperation = dateofoperation;
		this.devedor = devedor;
		this.type = type;
		this.description = description;
		this.duedate = duedate;
		this.vencimento = vencimento;
		this.value = value;
		this.paid_value = paid_value;
		this.addition = addition;
		this.discount = discount;
		this.final_value = final_value;
		this.final_value_real = final_value_real;
		this.efetive = efetive;
		this.fixed_cost = fixedCost;
		this.observation = observation;
	}

	public BillsToReceive(Long id, Long venda_id, Integer nr_venda, Integer nr_nfce, String dateofoperation, String devedor, String type, String description, String duedate, LocalDate vencimento, String value, String paid_value, String addition, String discount, String final_value, Double final_value_real, boolean efetive, boolean fixedCost, String observation) {
		this.id = id;
		this.venda_id = venda_id;
		this.nr_venda = nr_venda;
		this.nr_nfce = nr_nfce;
		this.dateofoperation = dateofoperation;
		this.devedor = devedor;
		this.type = type;
		this.description = description;
		this.duedate = duedate;
		this.vencimento = vencimento;
		this.value = value;
		this.paid_value = paid_value;
		this.addition = addition;
		this.discount = discount;
		this.final_value = final_value;
		this.final_value_real = final_value_real;
		this.efetive = efetive;
		this.fixed_cost = fixedCost;
		this.observation = observation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVenda_id() {
		return venda_id;
	}

	public void setVenda_id(Long venda_id) {
		this.venda_id = venda_id;
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

	public String getDateofoperation() {
		return dateofoperation;
	}

	public void setDateofoperation(String dateofoperation) {
		this.dateofoperation = dateofoperation;
	}
		
	public String getDevedor() {
		return devedor;
	}

	public void setDevedor(String devedor) {
		this.devedor = devedor;
	}
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPaid_value() {
		return paid_value;
	}

	public void setPaid_value(String paid_value) {
		this.paid_value = paid_value;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getFinal_value() {
		return final_value;
	}

	public void setFinal_value(String final_value) {
		this.final_value = final_value;
	}

	public Double getFinal_value_real() {
		return final_value_real;
	}

	public void setFinal_value_real(Double final_value_real) {
		this.final_value_real = final_value_real;
	}

	public boolean isEfetive() {
		return efetive;
	}

	public void setEfetive(boolean efetive) {
		this.efetive = efetive;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public boolean isFixedCost() {
		return fixed_cost;
	}

	public void setFixedCost(boolean fixedCost) {
		this.fixed_cost = fixedCost;
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
		BillsToReceive other = (BillsToReceive) obj;
		return Objects.equals(id, other.id);
	}
			
}
