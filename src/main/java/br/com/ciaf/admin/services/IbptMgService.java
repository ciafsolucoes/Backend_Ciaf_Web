package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.IbptMg;
import br.com.ciaf.admin.repositories.IbptMgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IbptMgService {

    @Autowired
    private IbptMgRepository repository;

    public List<IbptMg> findAll(){
        List<IbptMg> ibptMgs = repository.findAll();
        return ibptMgs;
    }

    public IbptMg findById(Long id) {
        Optional<IbptMg> cfop = repository.findById(id);
        return cfop.orElseThrow(null);
    }
    public IbptMg findByCode(int code) {
        IbptMg ibpt = repository.findNcmByIbpt(code);
        return ibpt;
    }

    public IbptMg save(IbptMg ibpt) {
        return repository.save(ibpt);
    }

    public IbptMg update(Long id, IbptMg ibpt) {
        ibpt.setId(id);
        return repository.save(ibpt);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
