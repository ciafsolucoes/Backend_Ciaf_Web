package br.com.ciaf.admin.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ciaf.admin.entities.Vendedores;

public interface VendedoresRepository extends BaseRepository<Vendedores, Long> {
	@Query(value = "SELECT * from vendedores where name =:name ", nativeQuery = true)
	Optional<Vendedores> findByNameNative(@Param("name") String name);

	@Query(value = "SELECT * from vendedores where name =:name AND emitente_id =:emitente_id", nativeQuery = true)
	Optional<Vendedores> findByNameNativeEmitente(@Param("name") String name, @Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from vendedores where emitente_id =:emitente_id", nativeQuery = true)
	List<Vendedores> findByEmitenteId(@Param("emitente_id") Long emitente_id);
}
