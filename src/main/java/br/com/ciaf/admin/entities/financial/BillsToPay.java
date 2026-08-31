package br.com.ciaf.admin.entities.financial;

import br.com.ciaf.admin.entities.MyEmitenteId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="apagar")
public class BillsToPay extends MyEmitenteId implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 14)
	private String dateofoperation;
	@Column(length = 77)
	private String creditor;
	@Column(length = 60)
	private String type;
	@Column(length = 60)
	private String description;
	@Column(length = 15)
	private String duedate;
	@JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
	private LocalDate vencimento;
	@Column(length = 15)
	private Date due_date;
	@Column(length = 21)
	private String value;
	@Column(length = 21)
	private String paid_value;
	@Column(length = 21)
	private String addition;
	@Column(length = 21)
	private String discount;
	@Column(length = 22)
	private String final_value;
	private Double final_value_real;
	private Boolean fixedcost;
	private Boolean efetive;
	@Column(columnDefinition="TEXT")
	private String observation;
	
	public BillsToPay() {
	}

	public BillsToPay(Long id, String dateofoperation, String creditor, String type, String description, String duedate, LocalDate vencimento, Date due_date, String value, String paid_value, String addition, String discount, String final_value, Double final_value_real, Boolean fixedcost, Boolean efetive, String observation) {
		this.id = id;
		this.dateofoperation = dateofoperation;
		this.creditor = creditor;
		this.type = type;
		this.description = description;
		this.duedate = duedate;
		this.vencimento = vencimento;
		this.due_date = due_date;
		this.value = value;
		this.paid_value = paid_value;
		this.addition = addition;
		this.discount = discount;
		this.final_value = final_value;
		this.final_value_real = final_value_real;
		this.fixedcost = fixedcost;
		this.efetive = efetive;
		this.observation = observation;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDateofoperation() {
		return dateofoperation;
	}
	public void setDateofoperation(String dateofoperation) {
		this.dateofoperation = dateofoperation;
	}
	public String getCreditor() {
		return creditor;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setCreditor(String creditor) {
		this.creditor = creditor;
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

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
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

	public Boolean getFixedcost() {
		return fixedcost;
	}

	public void setFixedcost(Boolean fixedcost) {
		this.fixedcost = fixedcost;
	}

	public Boolean getEfetive() {
		return efetive;
	}

	public void setEfetive(Boolean efetive) {
		this.efetive = efetive;
	}

	public boolean isFixedcost() {
		return fixedcost;
	}

	public void setFixedcost(boolean fixedcost) {
		this.fixedcost = fixedcost;
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
		BillsToPay other = (BillsToPay) obj;
		return Objects.equals(id, other.id);
	}
			
}
