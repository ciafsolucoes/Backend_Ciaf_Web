package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.Historico;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.HistoricoRepository;

@Service
public class HistoricoServices {
	
	@Autowired
	private HistoricoRepository repository;
	
	public List<Historico> findAll(){
		return repository.findAll();
	}
	
	public Historico findById(Long id) {
		Optional<Historico> historico = repository.findById(id);
		return historico.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public String findByCodigo(int codigo) {
		String nome = repository.findByCodigo(codigo);
		return nome;
	}
	
	public Historico create(Historico historico) {
		return repository.save(historico);
	}
	
	public Historico update(Long id, Historico historico) {		
		historico.setId(id);
		return repository.save(historico);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
