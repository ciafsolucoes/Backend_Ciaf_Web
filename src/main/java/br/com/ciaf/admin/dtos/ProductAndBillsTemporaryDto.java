package br.com.ciaf.admin.dtos;

import br.com.ciaf.admin.entities.Movimentacao;
import br.com.ciaf.admin.entities.VendasItems;
import br.com.ciaf.admin.entities.financial.BillsToReceive;

public class ProductAndBillsTemporaryDto {
	private VendasItems[] products;

	private BillsToReceive[] billsToReceives;

	private VendasItems[] temporaryProducts;

	private BillsToReceive[] temporaryBillsToReceives;

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

	public VendasItems[] getTemporaryProducts() {
		return temporaryProducts;
	}

	public void setTemporaryProducts(VendasItems[] temporaryProducts) {
		this.temporaryProducts = temporaryProducts;
	}

	public BillsToReceive[] getTemporaryBillsToReceives() {
		return temporaryBillsToReceives;
	}

	public void setTemporaryBillsToReceives(BillsToReceive[] temporaryBillsToReceives) {
		this.temporaryBillsToReceives = temporaryBillsToReceives;
	}

	public Movimentacao[] getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao[] movimentacao) {
		this.movimentacao = movimentacao;
	}
}
