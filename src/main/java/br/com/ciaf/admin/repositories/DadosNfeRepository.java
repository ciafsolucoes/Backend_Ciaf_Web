package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.fiscal.DadosNfe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DadosNfeRepository extends JpaRepository<DadosNfe, Long> {
    @Modifying
    @Query("DELETE FROM DadosNfe d WHERE d.notas.id = :notaId")
    void deleteByNotaId(@Param("notaId") Long notaId);

}
