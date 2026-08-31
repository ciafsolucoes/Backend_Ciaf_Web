package br.com.ciaf.admin.master.repositories;

import br.com.ciaf.admin.entities.Emitente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MasterEmitenteRepository extends JpaRepository<Emitente, Long> {
    @Query(value = "SELECT senha_cert FROM emitente WHERE id = :id", nativeQuery = true)
    String getPassword(@Param("id") Long id);
}
