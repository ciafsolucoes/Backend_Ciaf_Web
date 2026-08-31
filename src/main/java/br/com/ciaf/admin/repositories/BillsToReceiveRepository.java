package br.com.ciaf.admin.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.ciaf.admin.entities.financial.BillsToReceive;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BillsToReceiveRepository extends BaseRepository<BillsToReceive, Long> {
	
	@Query(value = "SELECT * from areceber WHERE MONTH(created_at) = MONTH(now()) AND YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<BillsToReceive> findByCurrentMonth();

	@Query(value = "SELECT * from areceber WHERE emitente_id = :emitente_id AND SUBSTRING(duedate, 4,2) =:mes OR CONCAT('0', SUBSTRING(duedate, 4,1)) =:mes", nativeQuery = true)
	List<BillsToReceive> findByDueDateMonth(@Param("emitente_id") Long emitente_id, @Param("mes") String mes);

	@Query(value = "SELECT * from areceber pg WHERE emitente_id = :emitente_id AND pg.vencimento BETWEEN :data AND :data2", nativeQuery = true)
	List<BillsToReceive> findByDueDateInterval(@Param("emitente_id") Long emitente_id, @Param("data") LocalDate data, @Param("data2") LocalDate data2);

	@Query(value = "SELECT * from areceber pg WHERE pg.venda_id =:venda_id ", nativeQuery = true)
	List<BillsToReceive> findBySaleId(@Param("venda_id") Long venda_id);

	@Query(value = "SELECT * from areceber pg WHERE pg.nr_venda =:nr_venda ", nativeQuery = true)
	List<BillsToReceive> findBySaleNumber(@Param("nr_venda") Integer nr_venda);

	@Query(value = "SELECT * from areceber pg WHERE pg.nr_nfce =:nr_nfce ", nativeQuery = true)
	List<BillsToReceive> findByNfceNumber(@Param("nr_nfce") int nr_nfce);

	@Query(value = "SELECT * from areceber WHERE emitente_id = :emitente_id AND efetive = true", nativeQuery = true)
	List<BillsToReceive> findByEfetive(@Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from areceber WHERE emitente_id = :emitente_id AND efetive = false", nativeQuery = true)
	List<BillsToReceive> findByNoEfetive(@Param("emitente_id") Long emitente_id);

	@Modifying
	@Query(value = "DELETE FROM areceber WHERE venda_id =:venda_id", nativeQuery = true)
	void deleteByVendaId(@Param("venda_id") Long venda_id);

	@Modifying
	@Query(value = "DELETE FROM areceber WHERE nr_venda =:nr_venda", nativeQuery = true)
	void deleteByNrVenda(@Param("nr_venda") Integer nr_venda);

	@Modifying
	@Query(value = "DELETE FROM areceber WHERE nr_nfce =:nr_nfce", nativeQuery = true)
	void deleteByNrNfce(@Param("nr_nfce") Integer nr_nfce);

}
