package br.com.ciaf.admin.dtos;

import java.util.List;

import br.com.ciaf.admin.entities.CaixaDiario;
import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.entities.financial.BillsToReceive;

public class VendaCompletaDto {
			
	private Vendas venda;	
	private List<BillsToReceive> billsToReceive;

	private CaixaDiario caixaDiario;
	
	public VendaCompletaDto() {}

	public VendaCompletaDto(Vendas venda, List<BillsToReceive> billsToReceive, CaixaDiario caixaDiario) {
		this.venda = venda;
		this.billsToReceive = billsToReceive;
		this.caixaDiario = caixaDiario;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public List<BillsToReceive> getBillsToReceive() {
		return billsToReceive;
	}

	public void setBillsToReceive(List<BillsToReceive> billsToReceive) {
		this.billsToReceive = billsToReceive;
	}

	public CaixaDiario getCaixaDiario() {
		return caixaDiario;
	}

	public void setCaixaDiario(CaixaDiario caixaDiario) {
		this.caixaDiario = caixaDiario;
	}
}
