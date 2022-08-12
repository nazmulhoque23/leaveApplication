package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.Role;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.mappers.UserMapStructMapper;
import com.example.leaveapplication.repository.RoleRepository;
import com.example.leaveapplication.repository.UserRepository;
import com.example.leaveapplication.utils.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapStructMapper mapper;

    @Override
    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    @Override
    public User findByName(String name){
        return userRepo.findByUserName(name);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        /*if (userDTO.getManager() != null
                && userDTO.getManager().getId() != null
                && userRepo.findById(userDTO.getManager().getId()).isPresent()) {
            return null;// will implement an datanotfoundexception
        }
        UserDTO userDTO = new UserDTO();*/


        Role role = roleRepo.findByRoleName(RoleEnum.USER);

        userDTO.setEmail(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setUserName(userDTO.getUserName());
        userDTO.setManager(userDTO.getManager());

        User user= mapper.mapToEntityWithManager(userDTO);


        user.setRoles(Arrays.asList(role));

        //user.setManager(managerUser);
        userRepo.save(user);

        UserDTO modifiedUser = mapper.mapToDTO(user);
        return modifiedUser;
    }

    @Override
    public UserDTO createManager(UserDTO userDTO) {
        /*if (userDTO.getManager() != null
                && userDTO.getManager().getId() != null
                && userRepo.findById(userDTO.getManager().getId()).isPresent()) {
            return null;// will implement an datanotfoundexception
        }
        UserDTO userDTO = new UserDTO();*/


        Role role = roleRepo.findByRoleName(RoleEnum.MANAGER);

        userDTO.setEmail(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setUserName(userDTO.getUserName());
        userDTO.setManager(userDTO.getManager());

        User user= mapper.mapToEntityWithManager(userDTO);


        user.setRoles(Arrays.asList(role));

        //user.setManager(managerUser);
        userRepo.save(user);

        UserDTO modifiedUser = mapper.mapToDTO(user);
        return modifiedUser;
    }
}
