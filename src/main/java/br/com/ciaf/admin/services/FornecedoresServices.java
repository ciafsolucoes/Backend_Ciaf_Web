package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.Fornecedores;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.FornecedoresRepository;

@Service
public class FornecedoresServices {
	
	@Autowired
	private FornecedoresRepository repository;
	
	public List<Fornecedores> findAll(){
		return repository.findAll();
	}

	public List<Fornecedores> findByEmitenteId(Long id){
		return repository.findFornecedoresByEmitenteId(id);
	}
	
	public Fornecedores findById(Long id) {
		Optional<Fornecedores> fornecedores = repository.findById(id);
		return fornecedores.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Fornecedores create(Fornecedores fornecedores) {
		return repository.save(fornecedores);
	}
	
	public Fornecedores update(Long id, Fornecedores fornecedores) {		
		fornecedores.setId(id);
		return repository.save(fornecedores);
	}
	public Fornecedores updateByEmitenteId(Long id, Long emitente_id,  Fornecedores fornecedores) {
		fornecedores.setId(id);
		Fornecedores updateFornecedores = new Fornecedores();;
		if(fornecedores.getEmitente_id().equals(emitente_id)){
			repository.save(fornecedores);
			return fornecedores;
		}
		else {
			return updateFornecedores;
		}
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
