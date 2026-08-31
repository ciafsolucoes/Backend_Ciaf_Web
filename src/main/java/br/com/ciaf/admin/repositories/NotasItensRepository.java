package br.com.ciaf.admin.repositories;

import br.com.ciaf.admin.entities.fiscal.NotasItens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotasItensRepository extends JpaRepository<NotasItens, Long> {
}
