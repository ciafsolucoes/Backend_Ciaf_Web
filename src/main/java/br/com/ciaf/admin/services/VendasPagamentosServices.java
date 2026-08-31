package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.VendasPagamentos;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.VendasPagamentosRepositories;

@Service
public class VendasPagamentosServices {
	
	@Autowired
	private VendasPagamentosRepositories repository;
	
	public List<VendasPagamentos> findAll(){
		return repository.findAll();
	}	
	public VendasPagamentos findById(Long id) {
		Optional<VendasPagamentos> items = repository.findById(id);
		return items.orElseThrow(() -> new ResourceNotFoundException(id));
	}			
	public VendasPagamentos create(VendasPagamentos items) {
		return repository.save(items);
	}	
	public VendasPagamentos update(Long id, VendasPagamentos items) {		
		items.setId(id);
		return repository.save(items);
	}	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
