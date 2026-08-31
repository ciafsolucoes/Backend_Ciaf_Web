package br.com.ciaf.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.ciaf.admin.entities.CaixaDiario;
import org.springframework.data.repository.query.Param;

public interface CaixaDiarioRepository extends BaseRepository<CaixaDiario, Long> {
	
	@Query(value = "SELECT * FROM caixadiario " +
			"WHERE emitente_id = :emitente_id " +
			"AND COALESCE(created_at, datamov_caixa) >= CURDATE() " +
			"AND COALESCE(created_at, datamov_caixa) < (CURDATE() + INTERVAL 1 DAY)", nativeQuery = true)
	List<CaixaDiario> findByCurrentDay(@Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * FROM caixadiario " +
			"WHERE emitente_id = :emitente_id " +
			"AND COALESCE(created_at, datamov_caixa) >= CURDATE() " +
			"AND COALESCE(created_at, datamov_caixa) < (CURDATE() + INTERVAL 1 DAY) " +
			"ORDER BY id DESC LIMIT 1", nativeQuery = true)
	List<CaixaDiario> findByCurrentDayOpenOrNot(@Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT saldo from caixadiario ORDER BY id DESC LIMIT 1", nativeQuery = true)
	double findLastSaldo();
	
	@Query(value = "SELECT id from caixadiario ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
	Long findLastId();

	@Query(value = "SELECT * from caixadiario WHERE emitente_id =:emitente_id ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
	CaixaDiario findLastMov(@Param("emitente_id") Long emitente_id);

	@Query(value = "SELECT * from caixadiario WHERE nr_venda =:nr_venda", nativeQuery = true)
	CaixaDiario findBySaleNumber(int nr_venda);

	@Query(value = "SELECT * from caixadiario WHERE emitente_id =:emitente_id AND nr_nfce =:nr_nfce", nativeQuery = true)
	CaixaDiario findByNfceNumber(@Param("nr_nfce") int nr_nfce, @Param("emitente_id") Long emitente_id);

	@Modifying
	@Query(value = "UPDATE caixadiario SET valor =:valor WHERE nr_venda =:nr_venda", nativeQuery = true)
	void updateMyEntity(double valor, int nr_venda);
}
