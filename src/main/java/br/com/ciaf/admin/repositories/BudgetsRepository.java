package br.com.ciaf.admin.repositories;

import java.time.LocalDate;
import java.util.List;

import br.com.ciaf.admin.entities.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ciaf.admin.entities.Budgets;
public interface BudgetsRepository extends BaseRepository<Budgets, Long> {
	@Query(value = "SELECT id from budgets ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Long findByIdNative();

	@Query(value = "SELECT * from budgets WHERE emitente_id=:emitente_id", nativeQuery = true)
	List<Budgets> findByEmitenteId(@Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from budgets where vendedor LIKE :vendedor% AND emitente_id =:emitente_id", nativeQuery = true)
	List<Budgets> findBySellerName(@Param("vendedor") String vendedor, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from budgets where client =:client AND emitente_id =:emitente_id", nativeQuery = true)
	List<Budgets> findByClientName(@Param("client") String client, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from budgets where client LIKE :client%", nativeQuery = true)
	List<Budgets> findByClientFirstName(@Param("client") String client);

	@Query(value = "SELECT * from budgets where nr_budget =:nr_budget", nativeQuery = true)
	Budgets findByNrBudget(@Param("nr_budget") Integer nr_budget);

	@Query(value = "SELECT * from budgets where data =:data", nativeQuery = true)
	List<Budgets> findByNameNative(@Param("data") String corporate);

	@Query(value = "SELECT * from budgets WHERE DAY(created_at) = DAY(now()) AND MONTH(created_at) = MONTH(now()) AND YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<Budgets> findByCurrentDay();

	@Query(value = "SELECT * from budgets WHERE MONTH(created_at) = MONTH(now()) AND YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<Budgets> findByCurrentMonth();

	@Query(value = "SELECT * from budgets WHERE YEAR(created_at) = YEAR(now())", nativeQuery = true)
	List<Budgets> findByCurrentYear();

	@Query(value = "SELECT * FROM budgets b WHERE STR_TO_DATE(REPLACE(b.data, '/', '-'), '%d-%m-%Y') BETWEEN :data AND :data2", nativeQuery = true)
	List<Budgets> findByDatesInterval(@Param("data") LocalDate data, @Param("data2") LocalDate data2);

	@Query(value ="SELECT nr_budget FROM budgets ORDER BY nr_budget DESC LIMIT 1", nativeQuery = true)
	Integer findLastNumber();
}
