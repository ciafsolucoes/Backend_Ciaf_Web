package br.com.ciaf.admin.config.dao;

import br.com.ciaf.admin.config.model.User;
import br.com.ciaf.admin.entities.Fornecedores;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT * from user where reset_token=:resetToken", nativeQuery = true)
    Optional<User> findByResetToken(String resetToken);

    @Query(value = "SELECT * from user where email=:email", nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Query(value = "SELECT username from user where email=:email", nativeQuery = true)
    String findUserNameByEmail(@Param("email") String email);

    @Query(value = "SELECT MAX(id) from user", nativeQuery = true)
    Long findLasId();

    @Query(value = "SELECT is_valid from user where username=:username", nativeQuery = true)
    boolean findIsValid(@Param("username") String email);

    @Query(value = "SELECT COUNT(*) FROM user WHERE emitente_id =:emitente_id", nativeQuery = true)
    int findByNumberOfUsers(@Param("emitente_id") Long emitenteId);

    @Query(value="SELECT * FROM user WHERE emitente_id =:emitente_id",  nativeQuery = true)
    List<User> findUsersByEmitenteId(@Param("emitente_id") Long emitenteId);


    @Override
    void deleteById(Long id);
}
