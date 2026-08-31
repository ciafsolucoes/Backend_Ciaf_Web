package br.com.ciaf.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ciaf.admin.entities.financial.CashFlow;

public interface CashFlowRepository extends BaseRepository<CashFlow, Long>{
	
	@Query(value = "SELECT * from vendas where duedate =:duedate", nativeQuery = true)   
	List<CashFlow> findByNameNative(@Param("duedate") String duedate);
		
	@Query(value = "SELECT * from vendas WHERE DAY(due_date) = DAY(now()) AND MONTH(created_at) = MONTH(now()) AND YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<CashFlow> findByCurrentDay();
	
	@Query(value = "SELECT * from vendas WHERE MONTH(due_date) = MONTH(now()) AND YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<CashFlow> findByCurrentMonth();
	
	@Query(value = "SELECT * from vendas WHERE YEAR(due_date) = YEAR(now())", nativeQuery = true)
	List<CashFlow> findByCurrentYear();
}

