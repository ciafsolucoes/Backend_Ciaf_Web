package br.com.ciaf.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ciaf.admin.entities.BudgetsItems;

public interface BudgetsItemsRepository extends JpaRepository<BudgetsItems, Long> {
}
