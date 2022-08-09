package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.Role;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.mappers.UserMapStructMapper;
import com.example.leaveapplication.repository.UserRepository;
import com.example.leaveapplication.utils.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapStructMapper mapper;

    public UserDTO createUser(UserDTO userDTO) {
        /*if (userDTO.getManager() != null
                && userDTO.getManager().getId() != null
                && userRepo.findById(userDTO.getManager().getId()).isPresent()) {
            return null;// will implement an datanotfoundexception
        }
        UserDTO userDTO = new UserDTO();*/




        userDTO.setEmail(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setUserName(userDTO.getUserName());

        User user= mapper.mapToEntity(userDTO);
        User managerUser = new User(2L);

        user.setRoles(Arrays.asList(new Role(RoleEnum.USER)));
        user.setManager(managerUser);
        userRepo.save(user);

        UserDTO modifiedUser = mapper.mapToDTO(user);
        return modifiedUser;
    }
}
