package br.com.ciaf.admin.dtos;

import java.util.List;

import br.com.ciaf.admin.entities.Budgets;
import br.com.ciaf.admin.entities.financial.BudgetsBillsToReceive;

public class CompleteBudgetDto {
			
	private Budgets budget;	
	private List<BudgetsBillsToReceive> budgetsBillsToreceive;
										
	
	public CompleteBudgetDto() {}
		
	public CompleteBudgetDto(Budgets budget, List<BudgetsBillsToReceive>  budgetsBillsToreceive) {
		super();
		this.budget = budget;
		this.budgetsBillsToreceive = budgetsBillsToreceive;
	}
	public Budgets getBudget() {
		return budget;
	}

	public void setBudget(Budgets budget) {
		this.budget = budget;
	}

	public List<BudgetsBillsToReceive> getBudgetsBillsToreceive() {
		return budgetsBillsToreceive;
	}

	public void setBudgetsBillsToreceive(List<BudgetsBillsToReceive> budgetsBillsToreceive) {
		this.budgetsBillsToreceive = budgetsBillsToreceive;
	}

	

			
}
