package br.com.ciaf.admin.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.ciaf.admin.entities.Vendas;

public interface VendasRepositories extends BaseRepository<Vendas, Long>{
	
	public List<Vendas> findAllByOrderByClientAsc();
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM vendas WHERE id =:id", nativeQuery = true)
	List<Vendas> deleteBySellerId(@Param("id") Long id);
	
	@Query(value = "SELECT * from vendas WHERE emitente_id =:emitente_id AND vendedor LIKE :vendedor%", nativeQuery = true)
	List<Vendas> findBySellerName(@Param("emitente_id") Long emitente_id, @Param("vendedor") String vendedor);
	
	@Query(value = "SELECT * from vendas where client =:client", nativeQuery = true)   
	List<Vendas> findByClientName(@Param("client") String client);
	
//	@Query(value = "SELECT MAX(valor) from vendas", nativeQuery = true)   
	@Query(value = "SELECT  MAX(valor) FROM vendas", nativeQuery = true)   
	public double findByBiggerValue();
	
	@Query(value = "SELECT * from vendas v ORDER BY v.valor DESC", nativeQuery = true)   
	List<Vendas> findALLBigger();
	
	@Query(value = "SELECT * from vendas v WHERE v.valor >:valor", nativeQuery = true)   
	List<Vendas> findBiggerValue(@Param("valor") double valor);
	
	@Query(value = "SELECT * from vendas where client LIKE :client%", nativeQuery = true)   
	List<Vendas> findByClientFirstName(@Param("client") String client);
	
	@Query(value = "SELECT * from vendas where nr_venda =:nr_venda", nativeQuery = true)
	Vendas findByNrVenda(@Param("nr_venda") Integer nr_venda);

	@Query(value = "SELECT * from vendas where data =:data", nativeQuery = true)
	List<Vendas> findByNameNative(@Param("data") String data);
	
	@Query(value = "SELECT * from vendas v where emitente_id =:emitente_id AND v.updated_at BETWEEN :data AND :data2", nativeQuery = true)
	List<Vendas> findByDatesInterval(@Param("emitente_id") Long emitente_id, @Param("data") LocalDateTime data, @Param("data2") LocalDateTime data2);
		
	@Query(value = "SELECT * from vendas WHERE emitente_id =:emitente_id AND DAY(updated_at) = DAY(now()) AND MONTH(updated_at) = MONTH(now()) AND YEAR(updated_at) = YEAR(now())", nativeQuery = true)
	List<Vendas> findByCurrentDay(@Param("emitente_id") Long emitente_id);
	
	@Query(value = "SELECT * from vendas WHERE emitente_id =:emitente_id and MONTH(updated_at) = MONTH(now()) AND YEAR(updated_at) = YEAR(now())", nativeQuery = true)
	List<Vendas> findByCurrentMonth(@Param("emitente_id") Long emitente_id);
	
	@Query(value = "SELECT * from vendas WHERE emitente_id =:emitente_id AND YEAR(updated_at) = YEAR(now())", nativeQuery = true)
	List<Vendas> findByCurrentYear(@Param("emitente_id") Long emitente_id);

	@Query(value ="SELECT id FROM vendas WHERE emitente_id =:emitente_id ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Long findLastId(@Param("emitente_id") Long emitente_id);

	@Query(value ="SELECT nr_venda FROM vendas WHERE emitente_id =:emitente_id ORDER BY nr_venda DESC LIMIT 1", nativeQuery = true)
	Integer findLastNumber(@Param("emitente_id") Long emitente_id);
}

