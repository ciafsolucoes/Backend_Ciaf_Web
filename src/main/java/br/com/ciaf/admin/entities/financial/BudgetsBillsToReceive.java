package br.com.ciaf.admin.entities.financial;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ciaf.admin.entities.MyEmitenteId;
import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.ciaf.admin.entities.Budgets;

@Entity
@Table(name = "orc_areceber")
public class BudgetsBillsToReceive extends MyEmitenteId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String dateofoperation;
	private String devedor;
	private String type;
	private String description;
	private String duedate;
	private Date due_date;
	private String value;
	private String paid_value;
	private String addition;
	private String discount;
	private String final_value;
	private boolean efetive;
	@Column(columnDefinition="TEXT")
	private String observation;
	
//	@JsonBackReference
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "budget2_id")
//	private Budgets budgettoreceive;	
	
	public BudgetsBillsToReceive() {		
	}
    	
	public BudgetsBillsToReceive(Long id, String dateofoperation, String devedor, String type, String description,
			String duedate, Date due_date, String value, String paid_value, String addition, String discount,
			String final_value, boolean efetive, String observation) {
		super();
		this.id = id;
		this.dateofoperation = dateofoperation;
		this.devedor = devedor;
		this.type = type;
		this.description = description;
		this.duedate = duedate;
		this.due_date = due_date;
		this.value = value;
		this.paid_value = paid_value;
		this.addition = addition;
		this.discount = discount;
		this.final_value = final_value;
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
		BudgetsBillsToReceive other = (BudgetsBillsToReceive) obj;
		return Objects.equals(id, other.id);
	}
			
}
