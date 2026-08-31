package br.com.ciaf.admin.dtos;

import java.util.List;

import br.com.ciaf.admin.entities.financial.BillsToReceive;

public class ContasAPagarDto {
     
	private List<BillsToReceive> billsToreceive;

	public ContasAPagarDto(List<BillsToReceive> billsToreceive) {
		super();
		this.billsToreceive = billsToreceive;
	}

	public List<BillsToReceive> getBillsToreceive() {
		return billsToreceive;
	}

	public void setBillsToreceive(List<BillsToReceive> billsToreceive) {
		this.billsToreceive = billsToreceive;
	}	
	
}
