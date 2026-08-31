package br.com.ciaf.admin.config.service;

import br.com.ciaf.admin.config.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleService extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
