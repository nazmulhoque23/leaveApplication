package com.example.leaveapplication.entity;

import com.example.leaveapplication.utils.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleEnum roleName;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<User> users;

    public Role(RoleEnum roleName){
        this.roleName = roleName;
    }

    public void setRoleName(RoleEnum roleName){
        this.roleName = roleName;
    }

    public RoleEnum getRoleName(){
        return roleName;
    }
}
