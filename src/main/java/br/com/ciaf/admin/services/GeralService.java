package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.EntitySpecifications;
import br.com.ciaf.admin.entities.MyEmitenteId;
import br.com.ciaf.admin.repositories.BaseRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class GeralService {
    public <T extends MyEmitenteId> List<T> findAllByEmitenteId(BaseRepository<T, Long> repository, Long emitente_id) {
        Specification<T> spec = EntitySpecifications.hasEmitenteId(emitente_id);
        return repository.findAll(spec);
    }

    public <T extends MyEmitenteId> MyEmitenteId createEntityByEmitenteId(BaseRepository<T, Long> repository, Long emitente_id, T T) {
        Specification<T> spec = EntitySpecifications.hasEmitenteId(emitente_id);
        return repository.save(T);
    }
}
