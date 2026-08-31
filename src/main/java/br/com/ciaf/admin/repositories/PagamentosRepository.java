package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.Pagamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PagamentosRepository extends BaseRepository<Pagamentos, Long> {
    @Query(value = "SELECT * from pagamentos where venda_id =:venda_id", nativeQuery = true)
    List<Pagamentos> findByVendaId(@Param("venda_id") Long nota_number);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM pagamentos where venda_id =:venda_id", nativeQuery = true)
    void deleteAllByVendaId(@Param("venda_id") Long nota_number);


}
