package br.com.ciaf.admin.dtos;

import br.com.ciaf.admin.entities.Movimentacao;
import br.com.ciaf.admin.entities.VendasItems;
import br.com.ciaf.admin.entities.financial.BillsToReceive;

public class ProductAndBillsDto {
    private VendasItems[] products;

	private boolean needBillsToReceive;

    private BillsToReceive[] billsToReceives;

	private Movimentacao[] movimentacao;
    						 
   
    public VendasItems[] getProducts() {
		return products;
	}
	public void setProducts(VendasItems[] products) {
		this.products = products;
	}
	public BillsToReceive[] getBillsToReceives() {
		return billsToReceives;
	}

	public void setBillsToReceives(BillsToReceive[] billsToReceives) {
		this.billsToReceives = billsToReceives;
	}

	public boolean isNeedBillsToReceive() {
		return needBillsToReceive;
	}

	public void setNeedBillsToReceive(boolean needBillsToReceive) {
		this.needBillsToReceive = needBillsToReceive;
	}

	public Movimentacao[] getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao[] movimentacao) {
		this.movimentacao = movimentacao;
	}
}
