package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.config.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ciaf.admin.entities.Emitente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public interface EmitenteRepository extends BaseRepository<Emitente, Long> {
//    @Query(value = "SELECT is_active from emitente ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
//    boolean findLastEmitente();

    @Query(value = "SELECT is_active from emitente WHERE id =:id ", nativeQuery = true)
    Boolean findIfActiveEmitente(@Param("id") Long id);

    @Query(value = "SELECT is_paid from emitente WHERE id =:id ", nativeQuery = true)
    Boolean findIfPaidEmitente(@Param("id") Long id);

    @Query(value = "SELECT id from emitente ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
    Long findLastEmitenteId();

    @Query(value = "SELECT * from emitente ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
    Optional<Emitente> findLastEmitenteComplete();

    @Query(value = "SELECT start from emitente WHERE id =:id", nativeQuery = true)
    LocalDate getStartDate(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Emitente e SET e.is_paid = true, e.paymentDate = :paymentDate WHERE e.id = :id")
    int updateIsPaidById(@Param("id") Long id, @Param("paymentDate") LocalDate paymentDate
    );

    @Transactional
    @Modifying
    @Query("UPDATE Emitente e SET e.is_paid = false WHERE e.id = :id")
    int updateAsNoPaidById(@Param("id") Long id);

    @Query(value = "SELECT senha_cert FROM emitente WHERE id =:id", nativeQuery = true)
    String getPassword(Long id);
}
