package br.com.ciaf.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ciaf.admin.entities.Cfop;

public interface CfopRepository extends JpaRepository<Cfop, Long>{
	@Query(value = "SELECT descricao from cfop where codigo =:codigo", nativeQuery = true)
	String findCfopCode(@Param("codigo") int codigo);
}
