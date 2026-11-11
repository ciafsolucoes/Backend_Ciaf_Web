package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.config.CustomLogoutSuccessHandler;
import br.com.ciaf.admin.config.TokenProvider;
import br.com.ciaf.admin.config.dao.UserDao;
import br.com.ciaf.admin.config.model.LoginUser;
import br.com.ciaf.admin.config.model.User;
import br.com.ciaf.admin.config.model.UserDto;
import br.com.ciaf.admin.config.service.*;
import br.com.ciaf.admin.services.EmailService;
import br.com.ciaf.admin.services.EntityService;
import br.com.ciaf.admin.services.CiafInterService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

//@Api(tags = "AuthenticationEndpoint")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private CiafInterService ciafInterService;
    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    ActiveUserStore activeUserStore;

    private final UserDao userDao;

    private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    private final EmailService emailService;
    @Autowired
    private EntityService entityService;

    public AuthController(UserDao userDao, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
    }


    @Autowired
    private void setActiveUserStore(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

    private AuthenticationManager authenticationManager;

    @Autowired
    private void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private void setAuthenticateManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private TokenProvider jwtTokenUtil;

    @Autowired
    private void setTokenProvider(TokenProvider tokenProvider) {
        this.jwtTokenUtil = tokenProvider;
    }

    private CustomLogoutSuccessHandler logoutSuccessHandler;

    private void setCustomLogoutSuccessHandler(CustomLogoutSuccessHandler logoutSuccessHandler) {
        this.logoutSuccessHandler = logoutSuccessHandler;
    }
    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    private SessionRegistry sessionRegistry;

    @Autowired
    private void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private void setMyLogoutSuccessHandler(MyLogoutSuccessHandler myLogoutSuccessHandler) {
        this.myLogoutSuccessHandler = myLogoutSuccessHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        HttpSession session = request.getSession(true);
        if (session != null) {
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("user", user);
        } else {
            System.out.println("No session by request");
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("new-session-by-api", user);

        }
    }

    @CrossOrigin
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> signin(@RequestBody LoginUser loginUser, HttpServletRequest request, HttpServletResponse response) {

        if (activeUserStore.getUsers().contains(loginUser.getUsername())) {
            Map<Object, Object> model = new HashMap<>();
            model.put("username", loginUser.getUsername());
            model.put("token", "");
            model.put("roles", "");
            return ResponseEntity.ok().body(model);
        }
        if(userServiceImpl.findUserNamesByEmail(loginUser.getUsername()) != null){
            loginUser.setUsername(userServiceImpl.findUserNamesByEmail(loginUser.getUsername()));
            return getResponseEntity(loginUser);
        }
        else{
            return getResponseEntity(loginUser);
        }

    }

    private ResponseEntity<?> getResponseEntity(@RequestBody LoginUser loginUser) {
        try {
            var username = loginUser.getUsername();
            var password = loginUser.getPassword();
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            String atualRoles = jwtTokenUtil.getRoles(token);
            User user = userDao.findByUsername(loginUser.getUsername());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("roles", atualRoles);
            model.put("emitente_id", user.getEmitente_id());
//            onAuthenticationSuccess(request, response, authentication);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");

        myLogoutSuccessHandler.onLogoutSuccess(req, response, authentication);
        return "Logout feito";
    }

    @CrossOrigin
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String getAtualRules(HttpServletRequest req) {
        String authToken = null;
        String header = req.getHeader(HEADER_STRING);
        authToken = header.replace(TOKEN_PREFIX, "");
        String atualRoles = jwtTokenUtil.getRoles(authToken);
        return atualRoles;
    }

    @CrossOrigin
    @RequestMapping(value = "/confirm-user", method = RequestMethod.POST)
    public ResponseEntity confirmUserAccess(@RequestBody LoginUser loginUser) {
        try {
            var username = loginUser.getUsername();
            var password = loginUser.getPassword();
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
            Map<Object, Object> model = new HashMap<>();
            if (authentication.isAuthenticated()) {
                model.put("username", username);
                model.put("response", true);
            } else {
                model.put("username", username);
                model.put("response", false);
            }
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            Map<Object, Object> model = new HashMap<>();
            model.put("username", "");
            model.put("response", false);
            return ResponseEntity.ok(model);
        }
    }


    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) {
        log.info(
                "POST /auth/register - inicio saveUser: username={}, email={}, emitenteId={}, empresa={}, roleIds={}, allowsPresente={}",
                user != null ? user.getUsername() : null,
                user != null ? user.getEmail() : null,
                user != null ? user.getEmitente_id() : null,
                user != null ? user.getEmpresa() : null,
                user != null ? user.getRoleIds() : null,
                user != null && user.getAllows() != null
        );
        try {
            userServiceImpl.createUserWithRoles(user);
            // empresa nova (cadastro pelo Ciaf Web): avisa o Ciaf Inter em segundo plano; nunca atrapalha nem falha o registro
            if (user != null && user.getEmpresa() != null && !user.getEmpresa().trim().isEmpty()) {
                ciafInterService.registrarClienteNovo(user.getEmpresa(), user.getUsername(), user.getEmail(), user.getPhone(), user.getEmitente_id());
            }
//            log.info(
//                    "POST /auth/register - usuario criado com sucesso: username={}, email={}",
//                    user != null ? user.getUsername() : null,
//                    user != null ? user.getEmail() : null
//            );
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            log.error(
                    "POST /auth/register - erro de integridade ao criar usuario: username={}, email={}, mensagem={}",
                    user != null ? user.getUsername() : null,
                    user != null ? user.getEmail() : null,
                    e.getMessage(),
                    e
            );
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constEx = (ConstraintViolationException) e.getCause();

                if (constEx.getConstraintName().contains("username_UNIQUE")) {
                    return new ResponseEntity<>("Username already in use", HttpStatus.OK);
                }
                if (constEx.getConstraintName().contains("email_UNIQUE")) {
                    return new ResponseEntity<>("email already in use", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error(
                    "POST /auth/register - erro inesperado ao criar usuario: username={}, email={}, mensagem={}",
                    user != null ? user.getUsername() : null,
                    user != null ? user.getEmail() : null,
                    e.getMessage(),
                    e
            );
            throw e;
        }
    }
    public void createDataBaseAndTables(String schema) {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "gr007879";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE " + schema);
            stmt.executeUpdate("CREATE TABLE default3.role (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(60), " +
                    "description VARCHAR(90))");

            stmt.executeUpdate("CREATE TABLE " + schema + ".user (" +
                    "id INT PRIMARY KEY, " +
                    "empresa VARCHAR(255), " +
                    "username VARCHAR(255) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "phone VARCHAR(255), " +
                    "businessTitle VARCHAR(255), " +
                    "is_valid Boolean)");

            stmt.executeUpdate("CREATE TABLE " + schema + ".user_roles (" +
                    "user_id INT NOT NULL, " +
                    "role_id INT NOT NULL," +
                    "FOREIGN KEY (user_id)" +
                    "REFERENCES teste1spring.user(id)," +
                    "FOREIGN KEY (role_id) " +
                    "REFERENCES teste1spring.role(id))");
            stmt.executeUpdate("CREATE TABLE " + schema + ".SERVICOS (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(60))");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin
    @GetMapping(path = {"/name"})
    public String findByName(HttpServletRequest req) {
        String token = jwtTokenUtil.resolveToken(req);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return username;
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> payload
    ) throws MessagingException, IOException {
        String email = payload.get("email");
        User user = userDao.findByEmail(email);
        if (user != null && user.getEmitente_id() != null) {
            String resetToken = UUID.randomUUID().toString();;
            user.setResetToken(resetToken);
            userDao.save(user);
//            String resetLink = "http://localhost:3000/user/reset-password?token=" + resetToken;
            String resetLink = "https://ciafonline.com.br/user/reset-password?token=" + resetToken;
            emailService.sendWithoutAttach(
                    user.getEmail(),
                    "Recuperação de Senha",
                    "Click no link abaixo para recuperar sua senha: \n" +resetLink
            );
            return "Enviamos um link de reset da senha para o seu email";
        } else {
            return "Não temos usuário com esse email no nosso banco de dados";
        }
//        return "testanbdo";
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, @RequestBody String newPassword) {
        Optional<User> user = userDao.findByResetToken(token);
        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setPassword(bcryptEncoder.encode(newPassword)); // Ideally hash the new password
            userEntity.setResetToken(null); // Invalidate token
            userDao.save(userEntity);
            return "Password successfully updated";
        } else {
            return "Invalid reset token";
        }

    }




        @CrossOrigin
    @PostMapping(path = {"/test-token"})
    public boolean testTokenValidate(@RequestBody String reqToken) {
        try {
            Date expiresAt = jwtTokenUtil.getExpirationDateFromToken(reqToken);
            return expiresAt.before(new Date());
        } catch (ExpiredJwtException e) {
//            System.out.println(" Token expired ");
            return true;
        } catch (SignatureException e) {
            System.out.println("Signature exception during token validation  =>" + e);
            return true;
        } catch (Exception e) {
            // the token is expired if we can't parse it
            System.out.println(" Some other exception in JWT parsing =>" + e);
            return true;
        }
    }


}
