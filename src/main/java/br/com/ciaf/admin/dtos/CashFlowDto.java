package br.com.ciaf.admin.dtos;

import java.io.Serializable;

import br.com.ciaf.admin.entities.financial.Balance;


public class CashFlowDto implements Serializable{

	private static final long serialVersionUID = 1L;
//	private String creditor;
//	private String description;
//	private String duedate;
//	private String value;
//	private Boolean efetive;
//	private String type;
//	private double balanceforecast;
//	private double balance;
//	private String devedor;
//	private String description2;
//	private String duedate2;
//	private String value2;
//	private Boolean efetive2;
//	private String type2;
	private BillsToPayDto billsToPayDto;
	private Balance balance;
	private BillsToReceiveDto billsToReceiveDto;



//	public CashFlowDto(String creditor, String description, String duedate, String value, Boolean efetive, String type,
//			double balance, double balanceforecast, String devedor, String description2, String duedate2, String value2,
//			Boolean efetive2, String type2) {
//				
//			this.creditor = creditor;
//			this.description = description;
//			this.duedate = duedate;
//			this.value = value;
//			this.efetive = efetive;
//			this.type = type;		
//			this.balance = balance;
//			this.balanceforecast = balanceforecast;		
//			this.devedor = devedor;
//			this.description2 = description2;
//			this.duedate2 = duedate2;
//			this.value2 = value2;
//			this.efetive2 = efetive2;
//			this.type2 = type2;
//		
//	}

	public CashFlowDto(String creditor, String description, String duedate, String value, Boolean efetive, String type,
			double balance, double balanceforecast, String devedor, String description2, String duedate2, String value2,
			Boolean efetive2, String type2) {
		this.billsToPayDto = new BillsToPayDto();
		this.billsToPayDto.setCreditor(creditor);
		this.billsToPayDto.setDescription(description);
		this.billsToPayDto.setDuedate(duedate);
		this.billsToPayDto.setValue(value);
		this.billsToPayDto.setEfetive(efetive);
		this.balance = new Balance();
		this.balance.setBalance(balanceforecast);
		this.balance.setBalanceforecast(balanceforecast);
		this.billsToReceiveDto = new BillsToReceiveDto();
		this.billsToReceiveDto.setDevedor(devedor);
		this.billsToReceiveDto.setDescription(description2);
		this.billsToReceiveDto.setDuedate(duedate2);
		this.billsToReceiveDto.setValue(value2);
		this.billsToReceiveDto.setEfetive(efetive2);
		this.billsToReceiveDto.setType(type2);
	}
	
//	public CashFlowDto(String creditor, String description, String duedate, String value, Boolean efetive, String type,
//	double balance, double balanceforecast, String devedor, String description2, String duedate2, String value2,
//	Boolean efetive2, String type2) {
//	       this.ContasAPagar(creditor, description, duedate, value, efetive, type);
//	       this.ContasAReceber(creditor, description2, duedate2, value2, efetive2, type2);
//	       this.Balance(balance, balanceforecast);
//	}

//	public String getDevedor() {
//		return devedor;
//	}
//
//	public void setDevedor(String devedor) {
//		this.devedor = devedor;
//	}
//
//	public String getCreditor() {
//		return creditor;
//	}
//
//	public void setCreditor(String creditor) {
//		this.creditor = creditor;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public String getDuedate() {
//		return duedate;
//	}
//
//	public void setDuedate(String duedate) {
//		this.duedate = duedate;
//	}
//
//	public String getValue() {
//		return value;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public Boolean getEfetive() {
//		return efetive;
//	}
//
//	public void setEfetive(Boolean efetive) {
//		this.efetive = efetive;
//	}
//
//	public double getForecast() {
//		return balanceforecast;
//	}
//
//	public void setForecast(double balanceforecast) {
//		this.balanceforecast = balanceforecast;
//	}
//
//	public double getBalance() {
//		return balance;
//	}
//
//	public void setBalance(double balance) {
//		this.balance = balance;
//	}
//
//	public String getDescription2() {
//		return description2;
//	}
//
//	public void setDescription2(String description2) {
//		this.description2 = description2;
//	}
//
//	public String getDuedate2() {
//		return duedate2;
//	}
//
//	public void setDuedate2(String duedate2) {
//		this.duedate2 = duedate2;
//	}
//
//	public String getValue2() {
//		return value2;
//	}
//
//	public void setValue2(String value2) {
//		this.value2 = value2;
//	}
//
//	public Boolean getEfetive2() {
//		return efetive2;
//	}
//
//	public void setEfetive2(Boolean efetive2) {
//		this.efetive2 = efetive2;
//	}
//
//	public String getType2() {
//		return type2;
//	}
//
//	public void setType2(String type2) {
//		this.type2 = type2;
//	}

	public BillsToPayDto getBillsToPay() {
		return billsToPayDto;
	}

	public void setBillsToPay(BillsToPayDto billsToPayDto) {
		this.billsToPayDto = billsToPayDto;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public BillsToReceiveDto getBillsToReceive() {
		return billsToReceiveDto;
	}

	public void setBillsToReceive(BillsToReceiveDto billsToReceiveDto) {
		this.billsToReceiveDto = billsToReceiveDto;
	}
	
	public double convertValueTODouble(String value) {
		value = value.substring(3).replaceAll(",", ".");
		double result = Double.parseDouble(value);
		return result;
	}

}
