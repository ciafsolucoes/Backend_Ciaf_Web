package br.com.ciaf.admin.config.model;

import java.util.Set;

public class UserDto {

//    private Long id;
    private Long emitente_id;
    private String empresa;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private String businessTitle;
    private UsersPermissions allows;
    private boolean is_valid;
    private Set<Long> roleIds;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Long getEmitente_id() {
        return emitente_id;
    }

    public void setEmitente_id(Long emitente_id) {
        this.emitente_id = emitente_id;
    }

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

    public UsersPermissions getAllows() {
        return allows;
    }

    public void setAllows(UsersPermissions allows) {
        this.allows = allows;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public User getUserFromDto(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);
        user.setBusinessTitle(businessTitle);
        user.setIs_valid(is_valid);
        return user;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "emitente_id=" + emitente_id +
                ", empresa='" + empresa + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", businessTitle='" + businessTitle + '\'' +
                ", allows=" + allows +
                ", is_valid=" + is_valid +
                ", roleIds=" + roleIds +
                '}';
    }
}
