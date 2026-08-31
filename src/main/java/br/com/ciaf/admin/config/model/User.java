package br.com.ciaf.admin.config.model;

import br.com.ciaf.admin.entities.MyEmitenteId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
@Entity
@Table(name = "user")
public class User extends MyEmitenteId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
//    private Long emitente_id;
    @Column(length = 90)
    private String empresa;
    @Column(length = 42)
    private String username;
    @Column(length = 66)
//    @JsonIgnore
    private String password;
    @Column(length = 90)
    private String email;
    @Column(length = 42)
    private String phone;
    @Column(length = 60)
    private String name;
    @Column(name = "business_title", length = 28)
    private String businessTitle;
    @Column(name = "is_valid")
    private Boolean is_valid;
    @Column(length = 49)
    private String logo_name;
    @Column(length = 49)
    private String cert_name;
    @Column(name = "reset_token")
    private String resetToken;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    @JsonManagedReference
    @OneToOne(mappedBy = "user_allows", cascade = CascadeType.ALL)
    private UsersPermissions allows;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public Long getEmitente_id() {
//        return emitente_id;
//    }
//
//    public void setEmitente_id(Long emitente_id) {
//        this.emitente_id = emitente_id;
//    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public Boolean getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(Boolean is_valid) {
        this.is_valid = is_valid;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getLogo_name() {
        return logo_name;
    }

    public void setLogo_name(String logo_name) {
        this.logo_name = logo_name;
    }

    public String getCert_name() {
        return cert_name;
    }

    public void setCert_name(String cert_name) {
        this.cert_name = cert_name;
    }

    public UsersPermissions getAllows() {
        return allows;
    }

    public void setAllows(UsersPermissions allows) {
        this.allows = allows;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", empresa=" + empresa + ", username=" + username + ", password=" + password + ", email=" + email + ", phone=" + phone + ", name=" + name + ", businessTitle=" + businessTitle + ", is_valid=" + is_valid + ", roles=" + roles + ", allows=" + allows + '}';
    }
}
