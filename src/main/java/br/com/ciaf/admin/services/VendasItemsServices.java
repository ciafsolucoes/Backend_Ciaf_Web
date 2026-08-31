package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.VendasItems;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.VendasItemsRepositories;

@Service
public class VendasItemsServices {
	
	@Autowired
	private VendasItemsRepositories repository;
	
	public List<VendasItems> findAll(){
		return repository.findAll();
	}
	
	public VendasItems findById(Long id) {
		Optional<VendasItems> items = repository.findById(id);
		return items.orElseThrow(() -> new ResourceNotFoundException(id));
	}
			
	public VendasItems create(VendasItems items) {
		return repository.save(items);
	}
	
	public VendasItems update(Long id, VendasItems items) {		
		items.setId(id);
		return repository.save(items);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
