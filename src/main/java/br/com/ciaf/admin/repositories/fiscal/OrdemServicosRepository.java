package br.com.ciaf.admin.repositories.fiscal;

import br.com.ciaf.admin.entities.fiscal.OrdemServicos;
import br.com.ciaf.admin.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdemServicosRepository extends BaseRepository<OrdemServicos, Long> {

    @Query(value = "SELECT * FROM ord_serv WHERE client_id = :client_id", nativeQuery = true)
    List<OrdemServicos> findByClientId(@Param("client_id") Long client_id);

    @Query(value = "SELECT id FROM ord_serv ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Long findLastId();
}
