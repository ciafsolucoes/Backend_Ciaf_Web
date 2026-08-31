package br.com.ciaf.admin.config.dao;

import br.com.ciaf.admin.config.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface RoleDao extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}
