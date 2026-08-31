package br.com.ciaf.admin.dtos;

public class BillsToReceiveDto {
	private Long emitente_id;
	private int nr_venda;
	private String devedor;
	private String type;
	private String description;
	private String duedate;
	private String value;
	private Boolean efetive;
	
	public BillsToReceiveDto() {}

	public Long getEmitente_id() {
		return emitente_id;
	}

	public void setEmitente_id(Long emitente_id) {
		this.emitente_id = emitente_id;
	}

	public int getNr_venda() {
		return nr_venda;
	}

	public void setNr_venda(int nr_venda) {
		this.nr_venda = nr_venda;
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
