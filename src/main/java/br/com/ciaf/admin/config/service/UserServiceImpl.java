package br.com.ciaf.admin.config.service;

import java.util.*;

import br.com.ciaf.admin.config.dao.UserDao;
import br.com.ciaf.admin.config.model.Role;
import br.com.ciaf.admin.config.model.User;
import br.com.ciaf.admin.config.model.UserDto;
import br.com.ciaf.admin.config.model.UsersPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleService roleService;

    private final UserDao userDao;

    private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(RoleService roleService, UserDao userDao) {
        this.roleService = roleService;
        this.userDao = userDao;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }


    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public Long findLastId() {
        return userDao.findLasId();
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public List<User> findByEmitenteId(Long emitente_id) {
        return userDao.findUsersByEmitenteId(emitente_id);
    }

    @Override
    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public String findUserNamesByEmail(String email) {
        return userDao.findUserNameByEmail(email);
    }

    public Optional<User> findBuId(Long id) {
        return userDao.findById(id);
    }

    public User updateUser(Long id, User user){
        User oldUser = userDao.findByUsername(user.getUsername());
        if(user.getPassword().isBlank() || user.getPassword().isEmpty()){
            user.setPassword(oldUser.getPassword());
        }else{
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
        }
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);
        user.setId(id);
        return userDao.save(user);
    }
    @Override
    public User save(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
//        if(user.getEmail().split("@")[1].equals("ciaf2.com.br")){
//            role = roleService.findByName("ADMIN");
//            roleSet.add(role);
//        }
        user.setRoles(roleSet);
        return userDao.save(user);
    }

    @Transactional
    public void createUserWithRoles(UserDto createUserRequest) {
        log.info(
                "createUserWithRoles - inicio: username={}, email={}, emitenteId={}, roleIds={}, allowsPresente={}",
                createUserRequest != null ? createUserRequest.getUsername() : null,
                createUserRequest != null ? createUserRequest.getEmail() : null,
                createUserRequest != null ? createUserRequest.getEmitente_id() : null,
                createUserRequest != null ? createUserRequest.getRoleIds() : null,
                createUserRequest != null && createUserRequest.getAllows() != null
        );
        long newId = findLastId() != null ? findLastId() + 1 : 1;
        log.info("createUserWithRoles - proximo id calculado: {}", newId);
        User user = new User();
        user.setId(newId);
        user.setEmitente_id(createUserRequest.getEmitente_id());
        user.setEmpresa(createUserRequest.getEmpresa());
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(createUserRequest.getEmail());
        user.setPhone(createUserRequest.getPhone());
        user.setName(createUserRequest.getName());
        user.setBusinessTitle(createUserRequest.getBusinessTitle());
        user.setIs_valid(createUserRequest.isIs_valid());
        log.info(
                "createUserWithRoles - user mapeado: id={}, username={}, email={}, isValid={}",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getIs_valid()
        );
        UsersPermissions permissions = createUserRequest.getAllows();
        if (permissions == null) {
            log.warn("createUserWithRoles - allows veio nulo para username={}", user.getUsername());
        } else {
            permissions.setUser_allows(user);
            user.setAllows(permissions);
            log.info("createUserWithRoles - permissoes associadas ao usuario username={}", user.getUsername());
        }
        if (createUserRequest.getRoleIds() != null && !createUserRequest.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleService.findAllById(createUserRequest.getRoleIds()));
            user.setRoles(roles);
            log.info("createUserWithRoles - roles carregadas: quantidade={}, roleIdsSolicitados={}", roles.size(), createUserRequest.getRoleIds());
        } else {
            log.warn("createUserWithRoles - nenhuma role informada para username={}", user.getUsername());
        }
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        log.info("createUserWithRoles - senha codificada e usuario pronto para persistencia: username={}", user.getUsername());
        userDao.save(user);
        log.info("createUserWithRoles - usuario persistido com sucesso: id={}, username={}", user.getId(), user.getUsername());
    }
}
