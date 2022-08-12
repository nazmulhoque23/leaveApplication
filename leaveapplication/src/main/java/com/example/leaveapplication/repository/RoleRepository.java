package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.Role;
import com.example.leaveapplication.utils.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleEnum roleName);
}
