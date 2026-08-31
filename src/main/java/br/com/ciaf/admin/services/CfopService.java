package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.Cfop;
import br.com.ciaf.admin.repositories.CfopRepository;

@Service
public class CfopService {
	private final CfopRepository repository;

    public CfopService(CfopRepository repository) {
        this.repository = repository;
    }

    public List<Cfop> findAll(){
		List<Cfop> cfops = repository.findAll();
		return cfops;
	}
	
	public Cfop findById(Long id) {
		Optional<Cfop> cfop = repository.findById(id);
		return cfop.orElseThrow(null);
	}
	public String findByCode(int code) {
		String cfop = repository.findCfopCode(code);
		return cfop;
	}
	
	public Cfop save(Cfop cfop) {
	   return repository.save(cfop);
	}
	
	public Cfop update(Long id, Cfop cfop) {		
		cfop.setId(id);
		return repository.save(cfop);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
