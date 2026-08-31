package br.com.ciaf.admin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ciaf.admin.entities.Persons;

public interface PersonsRepositories extends BaseRepository<Persons, Long> {
	@Query(value = "SELECT * from persons where emitente_id =:emitente_id ", nativeQuery = true)
	List<Persons> findByEmitente_id(@Param("emitente_id") String emitente_id);

	@Query(value = "SELECT * from persons where corporate =:corporate ", nativeQuery = true)
	Optional<Persons> findByNameNative(@Param("corporate") String corporate);

	@Query(value = "SELECT * from persons where cpf_cnpj =:cpf_cnpj", nativeQuery = true)
	List<Persons> findByCpfCnpj(@Param("cpf_cnpj") String cpf_cnpj);
	@Query(value = "SELECT * from persons where city =:city", nativeQuery = true)
	List<Persons> findByCity(@Param("city") String city);

	@Query(value = "SELECT * from persons WHERE SUBSTRING(birthday_date, 4,2) =:mes", nativeQuery = true)
	List<Persons> findMonthBirthday(@Param("mes") String mes);
}
