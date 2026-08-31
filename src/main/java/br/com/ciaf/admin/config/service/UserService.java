package br.com.ciaf.admin.config.service;

import br.com.ciaf.admin.config.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> findAll();
    User findOne(String username);
    String findUserNamesByEmail(String email);

}
