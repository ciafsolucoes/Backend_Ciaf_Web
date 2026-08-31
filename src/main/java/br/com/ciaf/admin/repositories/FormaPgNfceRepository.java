package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.fiscal.FormaPagamentoNFCe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FormaPgNfceRepository extends BaseRepository<FormaPagamentoNFCe, Long> {

    @Query(value = "SELECT * from forma_pg_nfce WHERE emitente_id =:emitente_id AND nota_number =:nota_number", nativeQuery = true)
    List<FormaPagamentoNFCe> findByNumber( @Param("emitente_id") Long emitente_id, @Param("nota_number") int nota_number);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM forma_pg_nfce WHERE emitente_id =:emitente_id AND nota_number =:nota_number", nativeQuery = true)
    void deleteAllByInvoiceNumber( @Param("emitente_id") Long emitente_id, @Param("nota_number") int nota_number);
}
