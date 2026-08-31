package br.com.ciaf.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ciaf.admin.entities.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {	
	@Query(value = "SELECT nome from historico where codigo =:codigo", nativeQuery = true)
	String findByCodigo(@Param("codigo") int codigo);	
}
