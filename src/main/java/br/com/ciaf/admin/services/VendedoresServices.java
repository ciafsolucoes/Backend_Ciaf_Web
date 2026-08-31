package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.Vendedores;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.VendedoresRepository;

@Service
public class VendedoresServices {
	
	@Autowired
	private VendedoresRepository repository;
	
	public List<Vendedores> findAll(){
		return repository.findAll();
	}
	
	public Vendedores findById(Long id) {
		Optional<Vendedores> vendedor = repository.findById(id);
		return vendedor.orElseThrow(() -> new ResourceNotFoundException("Vendedor com esse id não existe!"));
	}
	
	public Vendedores findByName(String name) {
		Optional<Vendedores> vendedor = repository.findByNameNative(name);
		return vendedor.orElseThrow(() -> new ResourceNotFoundException("Vendedor com esse nome não existe!"));
	}
	
	public Vendedores create(Vendedores vendedor) {
		return repository.save(vendedor);
	}
	
	public Vendedores update(Long id, Vendedores vendedor) {		
		vendedor.setId(id);
		return repository.save(vendedor);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
