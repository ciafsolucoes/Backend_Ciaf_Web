package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MovimentacaoRepository extends BaseRepository<Movimentacao, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM movimentacao  WHERE nr_venda =:nr_venda", nativeQuery = true)
    void deletingBySale(int nr_venda);

    @Query( value = "SELECT * FROM movimentacao WHERE emitente_id =:emitente_id", nativeQuery = true)
    List<Movimentacao> findByEmitenteId(@Param("emitente_id") Long emitente_id);
}
