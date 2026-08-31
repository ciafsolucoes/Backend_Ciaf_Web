package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import br.com.ciaf.admin.entities.financial.BillsToReceive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.financial.CashFlow;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.CashFlowRepository;


@Service
public class CashFlowServices extends GeralService {
	
	@Autowired
	private CashFlowRepository repository;
	
	public List<CashFlow> findAll(){		
		return repository.findAll();
	}

	public List<CashFlow> findAllWithEmitenteId(Long emitente_id){
		return findAllByEmitenteId(repository, emitente_id);
	}

	public List<CashFlow> findAllData(String data){
		return repository.findByNameNative(data);
	}
	
	public CashFlow findById(Long id) {
		Optional<CashFlow> cashFlow = repository.findById(id);
		return cashFlow.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public List<CashFlow> getAllCurrentDay(){		
		return repository.findByCurrentDay();
	}
	
	public List<CashFlow> getAllCurrentMonth(){		
		return repository.findByCurrentMonth();
	}
	
	
	public List<CashFlow> getAllCurrentYear(){		
		return repository.findByCurrentYear();
	}
	
	public CashFlow create(CashFlow cashFlow) {
		return repository.save(cashFlow);
	}
	
	public CashFlow update(Long id, CashFlow cashFlow) {		
		cashFlow.setId(id);
		return repository.save(cashFlow);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
