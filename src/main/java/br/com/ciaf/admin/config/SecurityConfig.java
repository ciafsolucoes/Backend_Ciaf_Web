package br.com.ciaf.admin.config;

import br.com.ciaf.admin.config.service.ActiveUserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;
    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private TokenProvider tokenProvider;

    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        Connection myConnection = dataSource.getConnection();
        return dataSource.getConnection();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/auth/logout")
                .sessionRegistry(sessionRegistry());
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers(
                                    "/auth/signin",
                                    "/auth/forgot-password",
                                    "/auth/reset-password",
                                    "/auth/name",
                                    "/auth/test-token",
                                    "/logout",
                                    "/photos/**",
                                    "/files/**",
                                    "/auth/register",
                                    "/users/loggedUsers",
                                    "/actuator/health",
                                    "/emitente",
                                    "/print/**"
                            )
                            .permitAll()
                            .antMatchers(HttpMethod.GET, "/users/name").hasAnyRole("USER", "ADMIN")//
                            .antMatchers(HttpMethod.POST, "/users/resetkey").hasRole("ADMIN")
                            .antMatchers("/auth/register").hasRole("ADMIN")
                            .antMatchers(HttpMethod.GET, "/emitente/{id}").access("isAuthenticated() or hasIpAddress('127.0.0.1') or hasIpAddress('::1')")
                            .antMatchers(HttpMethod.GET,  "/users/list1").hasRole("ADMIN");
                })
                .authorizeRequests()
                .antMatchers(
                        "/auth/signin",
                        "/auth/name",
                        "/auth/test-token",
                        "/logout",
                        "/sendemail/register",
                        "/sendemail/register-adm",
                        "/photos/**",
                        "/auth/register",
                        "/users/loggedUsers"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/auth/logout")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.origins:}") String extraOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = new ArrayList<>(Arrays.asList("http://localhost:3000","https://ciafonline.com.br", "http://127.0.0.1:8082/"));
        // Origens extras (separadas por virgula) via variavel APP_CORS_ORIGINS, ex.: https://ciafweb.ciafonline.com.br
        for (String o : extraOrigins.split(",")) { if (!o.isBlank()) origins.add(o.trim()); }
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }

}
