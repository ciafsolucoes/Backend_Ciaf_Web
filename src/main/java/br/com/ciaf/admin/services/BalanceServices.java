package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.financial.Balance;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.BalanceRepository;

@Service
public class BalanceServices extends GeralService {
	
	@Autowired
	private BalanceRepository repository;
	
	public List<Balance> findAll(){
		return repository.findAll();
	}

	public List<Balance> findAllByEmitenteId(Long emitente_id){
		return findAllByEmitenteId(repository, emitente_id);
	}

	public Balance findById(Long id) {
		Optional<Balance> balance = repository.findById(id);
		return balance.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Balance create(Balance balance) {
		return repository.save(balance);
	}
	
	public Balance update(Long id, Balance balance) {		
		balance.setId(id);
		return repository.save(balance);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
