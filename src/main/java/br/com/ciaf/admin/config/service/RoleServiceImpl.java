//package br.com.ciaf.admin.config.service;
//import br.com.ciaf.admin.config.model.Role;
//import br.com.ciaf.admin.config.dao.RoleDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//@Service(value = "roleService")
//public class RoleServiceImpl implements RoleService{
//    @Autowired
//    private RoleDao roleDao;
//
//    @Override
//    public Role findByName(String name) {
//        Role role = roleDao.findRoleByName(name);
//        return role;
//    }
//
//    @Override
//    public List<Role> findAll() {
//        return List.of();
//    }
//
//    @Override
//    public List<Role> findAll(Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public Page<Role> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<Role> findAllById(Iterable<Long> longs) {
//        return List.of();
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(Role entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Role> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public <S extends Role> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> List<S> saveAll(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public Optional<Role> findById(Long aLong) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends Role> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<Role> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public Role getOne(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public Role getById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Role> List<S> findAll(Example<S> example) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Role> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Role, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//}
