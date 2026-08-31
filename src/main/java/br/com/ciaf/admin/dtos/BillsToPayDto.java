package br.com.ciaf.admin.dtos;

public class BillsToPayDto  {
	private Long emitente_id;
	private String creditor;
	private String type;
	private String description;
	private String duedate;
	private String value;
	private Boolean efetive;
	
	public BillsToPayDto() {}
	
	public BillsToPayDto(String creditor, String type, String description, String duedate, String value,
			Boolean efetive) {
		super();
		this.creditor = creditor;
		this.type = type;
		this.description = description;
		this.duedate = duedate;
		this.value = value;
		this.efetive = efetive;
	}

	public Long getEmitente_id() {
		return emitente_id;
	}

	public void setEmitente_id(Long emitente_id) {
		this.emitente_id = emitente_id;
	}

	public String getCreditor() {
		return creditor;
	}
	public void setCreditor(String creditor) {
		this.creditor = creditor;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Boolean getEfetive() {
		return efetive;
	}
	public void setEfetive(Boolean efetive) {
		this.efetive = efetive;
	}
	
	
}
