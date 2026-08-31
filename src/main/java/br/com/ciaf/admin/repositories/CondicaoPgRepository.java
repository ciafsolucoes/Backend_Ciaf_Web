package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.CondicaoPg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CondicaoPgRepository extends BaseRepository<CondicaoPg, Long> {

    @Query(value = "SELECT * FROM condicaopg WHERE emitente_id = 0 OR emitente_id =:emitente_id ", nativeQuery = true)
    List<CondicaoPg> findAllByEmitenteId(@Param("emitente_id") Long emitente_id);
}
