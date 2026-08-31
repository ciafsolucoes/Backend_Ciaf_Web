package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.fiscal.NotasFiscaisConsumidor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NfceRepository extends BaseRepository<NotasFiscaisConsumidor, Long>{
    @Query(value = "SELECT number from notas_consumidor WHERE emitente_id = :emitente_id ORDER BY number DESC LIMIT 1", nativeQuery = true)
    int findInvoiceNumber(@Param("emitente_id") Long emitenteId);

    @Query(value = "SELECT * from notas_consumidor ORDER BY id DESC LIMIT 1", nativeQuery = true)
    NotasFiscaisConsumidor findLastInvoice();

    @Query(value = "SELECT * from notas_consumidor WHERE number =:number", nativeQuery = true)
    NotasFiscaisConsumidor findByNumber(@Param("number") int number);

    @Query(value = "SELECT id from notas_consumidor WHERE number =:number", nativeQuery = true)
    Long findIdInvoice(@Param("number") int number);

    @Query(value = "SELECT * FROM (SELECT * FROM notas_consumidor WHERE emitente_id = :emitente_id ORDER BY id DESC LIMIT 10) as sub ORDER BY id ASC", nativeQuery = true)
    List<NotasFiscaisConsumidor> findAllLast(@Param("emitente_id") Long emitenteId);
}
