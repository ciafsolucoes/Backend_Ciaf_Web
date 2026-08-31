package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.financial.BudgetsBillsToReceive;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.BudgetsBillsToReceiveRepository;


@Service
public class BudgetsBillsToReceiveServices {
	
	@Autowired
	private BudgetsBillsToReceiveRepository repository;
	
	public List<BudgetsBillsToReceive> findAll(){		
		return repository.findAll();
	}
		
	public BudgetsBillsToReceive findById(Long id) {
		Optional<BudgetsBillsToReceive> credito = repository.findById(id);
		return credito.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Long findLastId() {
		return repository.findLastId();
	}	
		
	public List<BudgetsBillsToReceive> getAllCurrentMonth(){		
		return repository.findByCurrentMonth();
	}
			
	public BudgetsBillsToReceive create(BudgetsBillsToReceive credito) {
		return repository.save(credito);
	}
	
	public BudgetsBillsToReceive update(Long id, BudgetsBillsToReceive credito) {		
		credito.setId(id);
		return repository.save(credito);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
