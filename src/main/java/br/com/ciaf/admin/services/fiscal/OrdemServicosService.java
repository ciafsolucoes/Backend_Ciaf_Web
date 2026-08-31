package br.com.ciaf.admin.services.fiscal;

import br.com.ciaf.admin.entities.fiscal.OrdemServicos;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.fiscal.OrdemServicosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdemServicosService {
    private final OrdemServicosRepository repository;

    public OrdemServicosService(OrdemServicosRepository repository) {
        this.repository = repository;
    }

    public List<OrdemServicos> findAll(Long client_id) {
        return repository.findByClientId(client_id);
    }

    public List<OrdemServicos> findAll() {
        return repository.findAll();
    }

    public OrdemServicos findById(Long id) {
        Optional<OrdemServicos> servicos = repository.findById(id);
        return servicos.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public OrdemServicos save(OrdemServicos servicos) {
        return repository.save(servicos);
    }

    public Long findLastId() {
        return repository.findLastId();
    }

    public OrdemServicos update(Long id, OrdemServicos servicos) {
        servicos.setId(id);
        return repository.save(servicos);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
