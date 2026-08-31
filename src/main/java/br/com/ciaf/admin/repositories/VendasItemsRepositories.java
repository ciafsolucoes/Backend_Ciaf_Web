package br.com.ciaf.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ciaf.admin.entities.VendasItems;

public interface VendasItemsRepositories extends JpaRepository<VendasItems, Long>{
	
}

