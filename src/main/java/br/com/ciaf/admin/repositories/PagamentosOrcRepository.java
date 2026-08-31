package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.PagamentosOrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PagamentosOrcRepository extends BaseRepository<PagamentosOrc, Long> {
    @Query(value = "SELECT * from budgets_pg where budgets_id =:budgets_id", nativeQuery = true)
    List<PagamentosOrc> findByVendaId(@Param("budgets_id") Long nota_number);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM budgets_pg where budgets_id =:budgets_id", nativeQuery = true)
    void deleteAllByVendaId(@Param("budgets_id") Long nota_number);


}
