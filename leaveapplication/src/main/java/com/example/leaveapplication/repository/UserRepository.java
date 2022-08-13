package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.Role;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.utils.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User findByUserName(String userName);
    User findByEmail(String email);

    @Query(value = "select * from users inner join user_role on users.id=user_role.user_id  where user_role.role_id =:roleId ", nativeQuery = true)
    List<User> findAllByRoleId(Long roleId);

}
