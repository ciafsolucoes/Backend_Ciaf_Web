package br.com.ciaf.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ciaf.admin.entities.financial.Balance;

public interface BalanceRepository extends BaseRepository<Balance, Long> {
}
