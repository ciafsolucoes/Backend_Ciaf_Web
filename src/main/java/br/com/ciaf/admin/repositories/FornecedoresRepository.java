package br.com.ciaf.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ciaf.admin.entities.Fornecedores;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FornecedoresRepository extends JpaRepository<Fornecedores, Long> {

    @Query(value="SELECT * FROM fornecedores WHERE emitente_id =:emitenteId",  nativeQuery = true)
    List<Fornecedores> findFornecedoresByEmitenteId(@Param("emitenteId") Long emitenteId);

    @Query(value="UPDATE fornecedores WHERE emitente_id =:emitenteId",  nativeQuery = true)
    void updateFornecedoresByEmitenteId(@Param("emitenteId") Long emitenteId);

//    @Query(value="SELECT f FROM Fornecedores f WHERE f.emitente_id =:emitenteId")
//    List<Fornecedores> findFornecedoresByEmitenteId(@Param("emitenteId") Long emitenteId);

}
