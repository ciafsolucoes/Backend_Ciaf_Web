package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.Transportadoras;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.TransportadorasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportadorasService {

    @Autowired
    private TransportadorasRepository repository;

    public List<Transportadoras> findAll(){
        return repository.findAll();
    }

    public Transportadoras findById(Long id){
        Optional<Transportadoras> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Transportadoras create(Transportadoras entity){
        return repository.save(entity);
    }

    public Transportadoras update(Long id, Transportadoras entity){
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
