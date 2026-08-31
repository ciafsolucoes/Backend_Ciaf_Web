package br.com.ciaf.admin.entities.financial;

import br.com.ciaf.admin.entities.MyEmitenteId;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cashflow")
public class CashFlow extends MyEmitenteId implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String duedate;
	 private Date due_date; 
	 private String type;   //credit or debit
	 private String creditor;
	 private String devedor;
	 private Boolean paid;
	 private String forecast;
	 private String balance;
	 
	 public CashFlow() {}

	 public CashFlow(Long id, String duedate, String due_date, String type, String creditor, String devedor, Boolean paid,
			String forecast, String balance) throws ParseException {
		
		super();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		this.id = id;
		this.duedate = duedate;
		this.due_date = sdf1.parse(due_date);
		this.type = type;
		this.creditor = creditor;
		this.devedor = devedor;
		this.paid = paid;
		this.forecast = forecast;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setDue_date(String due_date) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		this.due_date = sdf1.parse(due_date);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public String getDevedor() {
		return devedor;
	}

	public void setDevedor(String devedor) {
		this.devedor = devedor;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public String getForecast() {
		return forecast;
	}

	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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
		CashFlow other = (CashFlow) obj;
		return Objects.equals(id, other.id);
	}
		 
	 
}
