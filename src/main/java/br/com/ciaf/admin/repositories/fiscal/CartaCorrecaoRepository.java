package br.com.ciaf.admin.repositories.fiscal;

import br.com.ciaf.admin.entities.fiscal.CartaCorrecao;
import br.com.ciaf.admin.repositories.BaseRepository;
import org.apache.xml.utils.ObjectPool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartaCorrecaoRepository extends BaseRepository<CartaCorrecao, Long> {
    @Query( value = "SELECT nrcarta FROM cartanfe WHERE emitente_id =:emitente_id ORDER BY nrcarta DESC LIMIT 1", nativeQuery = true)
    int findLastNumber(@Param("emitente_id") Long emitente_id);

    @Query(value ="SELECT * FROM cartanfe WHERE emitente_id =:emitente_id", nativeQuery = true)
    List<CartaCorrecao>  findCartaCorrecaoByEmitente(@Param("emitente_id") Long emitente_id);

    @Query(value = "SELECT * FROM cartanfe WHERE id =:id AND emitente_id =:emitente_id", nativeQuery = true)
    List<CartaCorrecao> findByIdOfTheEmitenteId(@Param("id") Long id, @Param("emitente_id") Long emitente_id);
}
