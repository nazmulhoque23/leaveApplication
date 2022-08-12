package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.User;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO createManager(UserDTO user);


    User findByEmail(String email);
    User findByName(String name);
}
