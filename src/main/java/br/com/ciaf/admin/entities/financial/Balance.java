package br.com.ciaf.admin.entities.financial;

import br.com.ciaf.admin.entities.MyEmitenteId;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "balance")
public class Balance extends MyEmitenteId implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length = 16)
	private String data_saldo;
	private double balance = 0.0;
	private double balanceforecast = 0.0;
	
	public Balance() {}

	public Balance(long id, double balance, double balanceforecast) {
		super();
		this.id = id;
		this.balance = balance;
		this.balanceforecast = balanceforecast;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalanceforecast() {
		return balanceforecast;
	}

	public void setBalanceforecast(double balanceforecast) {
		this.balanceforecast = balanceforecast;
	}

	public String getData_saldo() {
		return data_saldo;
	}

	public void setData_saldo(String data_saldo) {
		this.data_saldo = data_saldo;
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
		Balance other = (Balance) obj;
		return id == other.id;
	}
	
}
