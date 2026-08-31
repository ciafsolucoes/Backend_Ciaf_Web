package br.com.ciaf.admin.services.fiscal;

import br.com.ciaf.admin.entities.fiscal.OrdemServicos;
import br.com.ciaf.admin.repositories.fiscal.ServicosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicosService {
    private final ServicosRepository repository;

    public ServicosService(ServicosRepository repository) {
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
        return servicos.orElseThrow(null);
    }

    public OrdemServicos save(OrdemServicos servicos) {
        return repository.save(servicos);
    }

    public OrdemServicos update(Long id, OrdemServicos servicos) {
        servicos.setId(id);
        return repository.save(servicos);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
