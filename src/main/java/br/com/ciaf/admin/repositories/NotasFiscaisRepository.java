package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.fiscal.NotasFiscais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotasFiscaisRepository  extends BaseRepository<NotasFiscais, Long> {
//    @Query(value = "SELECT number from notas WHERE emitente_id = :emitente_id ORDER BY number DESC LIMIT 1", nativeQuery = true)
//    int findInvoiceNumber(@Param("emitente_id") Long emitenteId);

    @Query(value = "SELECT * from notas WHERE chave =:chave and emitente_id = :emitente_id", nativeQuery = true)
    NotasFiscais findByChave(@Param("chave") String chave, @Param("emitente_id") Long emitenteId);

    @Query(value = "SELECT * from notas WHERE number =:number AND emitente_id = :emitente_id", nativeQuery = true)
    NotasFiscais findByNumber(@Param("emitente_id") Long emitenteId, @Param("number") int number);

    @Query(value = "SELECT clientmail from notas WHERE chave =:chave and emitente_id = :emitente_id ", nativeQuery = true)
    String findEmailByKey(@Param("chave") String chave, @Param("emitente_id") Long emitenteId);

    @Query(value = "SELECT id from notas WHERE number =:number", nativeQuery = true)
    Long findIdInvoice(@Param("number") int number);



    @Query(value = "SELECT * FROM (SELECT * FROM notas WHERE emitente_id = :emitente_id ORDER BY id DESC LIMIT 10) as sub ORDER BY id ASC", nativeQuery = true)
    List<NotasFiscais> findAllLast(@Param("emitente_id") Long emitenteId);

    @Query("SELECT n FROM NotasFiscais n JOIN FETCH n.dadosnota WHERE n.id = :notaId")
    NotasFiscais findNotaWithDadosnota(@Param("notaId") Long notaId);

    @Query(value = "SELECT * FROM NotasFiscais WHERE emitente_id =:emitente_id", nativeQuery = true)
    List<NotasFiscais> findByEmitenteId(Long emitente_id);

    @Query(value = "SELECT * FROM notas WHERE id = :id AND emitente_id = :emitente_id", nativeQuery = true)
    List<NotasFiscais> findByIdAndEmitenteId(@Param("id") Long id, @Param("emitente_id") Long emitenteId);

    @Query(value = "SELECT * FROM notas ORDER BY id DESC LIMIT 1", nativeQuery = true)
    List<NotasFiscais> findLastNota();

}
