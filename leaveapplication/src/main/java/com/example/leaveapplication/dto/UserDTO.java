package com.example.leaveapplication.dto;

import com.example.leaveapplication.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("userName")
    private String userName;

    @NotNull
    @JsonProperty("password")
    private String password;

    private UserDTO manager;

    public UserDTO(Long id){
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*public void setManager(UserDTO manager) {
        this.manager = manager;
    }*/

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    //private UserDTO manager;
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public UserDTO getManager() {
        return manager;
    }




}
