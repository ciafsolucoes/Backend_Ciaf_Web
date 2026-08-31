package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.IbptMg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IbptMgRepository extends JpaRepository<IbptMg, Long> {

    @Query(value = "SELECT * FROM ibpt_mg WHERE codigo =:codigo", nativeQuery = true)
    IbptMg findNcmByIbpt(@Param("codigo") int codigo);
}
