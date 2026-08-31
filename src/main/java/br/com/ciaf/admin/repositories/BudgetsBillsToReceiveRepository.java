package br.com.ciaf.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ciaf.admin.entities.financial.BudgetsBillsToReceive;

public interface BudgetsBillsToReceiveRepository extends JpaRepository<BudgetsBillsToReceive, Long> {
	
	@Query(value = "SELECT * from orc_areceber WHERE MONTH(created_at) = MONTH(now()) AND YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<BudgetsBillsToReceive> findByCurrentMonth();
		
	@Query(value ="SELECT id FROM orc_areceber ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Long findLastId();

}
