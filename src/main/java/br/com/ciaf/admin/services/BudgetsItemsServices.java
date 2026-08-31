package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.BudgetsItems;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.BudgetsItemsRepository;

@Service
public class BudgetsItemsServices {
	
	@Autowired
	private BudgetsItemsRepository repository;
	
	public List<BudgetsItems> findAll(){
		return repository.findAll();
	}	
	public BudgetsItems findById(Long id) {
		Optional<BudgetsItems> budgetItems = repository.findById(id);
		return budgetItems.orElseThrow(() -> new ResourceNotFoundException(id));
	}	
	public BudgetsItems create(BudgetsItems budgetItems) {
		return repository.save(budgetItems);
	}	
	public BudgetsItems update(Long id, BudgetsItems budgetItems) {		
		budgetItems.setId(id);
		return repository.save(budgetItems);
	}
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
