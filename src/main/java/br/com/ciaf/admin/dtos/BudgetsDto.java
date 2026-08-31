package br.com.ciaf.admin.dtos;


import br.com.ciaf.admin.entities.BudgetsItems;

public class BudgetsDto {

	private int indice;
	private int nr_budget;
	private BudgetsItems items;


	public BudgetsDto() {}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public int getNr_budget() {
		return nr_budget;
	}

	public void setNr_budget(int nr_budget) {
		this.nr_budget = nr_budget;
	}

	public BudgetsItems getItems() {
		return items;
	}

	public void setItems(BudgetsItems items) {
		this.items = items;
	}
}

