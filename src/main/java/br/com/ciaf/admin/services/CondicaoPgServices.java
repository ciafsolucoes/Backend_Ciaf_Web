package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.CondicaoPg;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.CondicaoPgRepository;

@Service
public class CondicaoPgServices extends GeralService {
	

	private final CondicaoPgRepository repository;

    public CondicaoPgServices(CondicaoPgRepository repository) {
        this.repository = repository;
    }

    public List<CondicaoPg> findAll(){
		return repository.findAll();
	}

	public List<CondicaoPg> findAllByEmitente(Long emitente_id){
//		return findAllByEmitenteId(repository, emitente_id);
		return repository.findAllByEmitenteId(emitente_id);
	}

	public CondicaoPg findById(Long id) {
		Optional<CondicaoPg> condicaoPg = repository.findById(id);
		return condicaoPg.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public CondicaoPg create(CondicaoPg condicaoPg) {
		return repository.save(condicaoPg);
	}
	
	public CondicaoPg update(Long id, CondicaoPg condicaoPg) {		
		condicaoPg.setId(id);
		return repository.save(condicaoPg);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
