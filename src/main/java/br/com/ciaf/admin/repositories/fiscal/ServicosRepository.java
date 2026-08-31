package br.com.ciaf.admin.repositories.fiscal;

import br.com.ciaf.admin.entities.fiscal.OrdemServicos;
import br.com.ciaf.admin.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicosRepository extends BaseRepository<OrdemServicos, Long> {

    @Query(value = "SELECT * FROM servicos WHERE client_id =:client_id", nativeQuery = true)
    List<OrdemServicos> findByClientId(@Param("client_id") Long client_id);
}
