package br.com.ciaf.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ciaf.admin.entities.Products;

import java.util.List;

public interface ProductsRepositories extends BaseRepository<Products, Long>{
	@Query(value = "SELECT stock from products WHERE id =:id ", nativeQuery = true)
	int findStockById(@Param("id") long id);

	@Query(value = "SELECT * from products WHERE cprod =:cprod AND emitente_id =:emitente_id", nativeQuery = true)
	Products findUniqueCprod(@Param("cprod") String cprod, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from products WHERE cean =:cean AND emitente_id =:emitente_id", nativeQuery = true)
	Products findUniqueCean(@Param("cean") String cean, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from products WHERE stock <:valor AND emitente_id =:emitente_id", nativeQuery = true)
	List<Products> findProductsByStock (@Param("valor") int valor, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from products WHERE description LIKE CONCAT('%', :name, '%') AND emitente_id =:emitente_id", nativeQuery = true)
	List<Products> findProductsByName (@Param("name") String name, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from products WHERE stock >:valor AND emitente_id =:emitente_id", nativeQuery = true)
	List<Products> findProductsByBiggerStock (@Param("valor") int valor, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from products WHERE salevalue >:valor AND emitente_id =:emitente_id", nativeQuery = true)
	List<Products> findProductsBySmallerPrice (@Param("valor") double valor, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from products WHERE salevalue <:valor AND emitente_id =:emitente_id", nativeQuery = true)
	List<Products> findProductsByBiggerPrice (@Param("valor") double valor, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from products WHERE stock < stock_minimum AND emitente_id =:emitente_id", nativeQuery = true)
	List<Products> findProductsLowMinimumStock (@Param("emitente_id") Long emitente_id);

}
