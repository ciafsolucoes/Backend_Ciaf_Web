package br.com.ciaf.admin.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ciaf.admin.dtos.CashFlowDto;
import br.com.ciaf.admin.entities.financial.BillsToPay;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface BillsToPayRepository extends BaseRepository<BillsToPay, Long> {
	@Query(value = "SELECT * from apagar WHERE MONTH(created_at) = MONTH(now()) AND YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<BillsToPay> findByCurrentMonth();

	@Query(value = "SELECT * from apagar WHERE emitente_id = :emitente_id AND SUBSTRING(duedate, 4,2) =:mes OR CONCAT('0', SUBSTRING(duedate, 4,1)) =:mes", nativeQuery = true)
	List<BillsToPay> findByDueDateMonth(@Param("emitente_id") Long emitente_id, @Param("mes") String mes);

	@Query(value = "SELECT * from apagar WHERE SUBSTRING(duedate, 4,2) =:mes OR CONCAT('0', SUBSTRING(duedate, 4,1)) =:mes", nativeQuery = true)
	List<BillsToPay> findByMonth(@Param("mes") String mes);

	@Query(value = "SELECT * from apagar WHERE SUBSTRING(duedate, 4,2) =:mes OR CONCAT('0', SUBSTRING(duedate, 4,1))  =:mes AND efetive = false", nativeQuery = true)
	List<BillsToPay> findByDueDateMonthandNotEfetive(@Param("mes") String mes);

	@Query(value = "SELECT * from apagar pg WHERE pg.vencimento BETWEEN :data AND :data2 AND efetive = true", nativeQuery = true)
	List<BillsToPay> findByDueDateIntervalAndEfetive(@Param("data") LocalDate data, @Param("data2") LocalDate data2);

	@Query(value = "SELECT * from apagar pg WHERE emitente_id = :emitente_id AND pg.vencimento BETWEEN :data AND :data2", nativeQuery = true)
	List<BillsToPay> findByDueDateInterval(@Param("emitente_id") Long emitenteId, @Param("data") LocalDate data, @Param("data2") LocalDate data2);

	@Query(value = "SELECT * from apagar WHERE emitente_id = :emitente_id AND efetive = true", nativeQuery = true)
	List<BillsToPay> findByEfetive(@Param("emitente_id") Long emitenteId);

	@Query(value = "SELECT * from apagar WHERE emitente_id = :emitente_id AND efetive = false", nativeQuery = true)
	List<BillsToPay> findByNoEfetive(@Param("emitente_id") Long emitenteId);

	@Query(value = "SELECT new br.com.ciaf.admin.dtos.CashFlowDto("
		    + "p.creditor, p.description, p.duedate, p.value, p.efetive, p.type, b.balance, b.balanceforecast,"
		    + "r.devedor, r.description, r.duedate, r.value, r.efetive, r.type) "
		    + "FROM BillsToPay as p, Balance as b, BillsToReceive as r WHERE p.emitente_id =:emitente_id AND r.emitente_id =:emitente_id")
		List<CashFlowDto> findJoinReceive(@Param("emitente_id") Long emitente_id);


}
