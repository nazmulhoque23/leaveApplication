package com.example.leaveapplication;

import com.example.leaveapplication.entity.Role;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.repository.RoleRepository;
import com.example.leaveapplication.repository.UserRepository;
import com.example.leaveapplication.utils.enums.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
///
@SpringBootApplication
public class LeaveapplicationApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveapplicationApplication.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(LeaveapplicationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        User adminUser = userRepository.findByUserName("admin");


        Role roleUser = roleRepo.findByRoleName(RoleEnum.USER);
        if(roleUser == null){
            roleUser = new Role(RoleEnum.USER);
        }

        Role roleManager = roleRepo.findByRoleName(RoleEnum.MANAGER);
        if(roleManager == null){
            roleManager = new Role(RoleEnum.MANAGER);
        }

        Role roleAdmin = roleRepo.findByRoleName(RoleEnum.ADMIN);
        if(roleAdmin == null){
            roleAdmin = new Role(RoleEnum.ADMIN);
        }

        roleRepo.save(roleUser);
        roleRepo.save(roleManager);
        roleRepo.save(roleAdmin);

        if(adminUser == null){
            LOGGER.warn("INITIALIZING ADMIN USER");
            User defaultAdmin = new User();

            defaultAdmin.setUserName("admin");
            defaultAdmin.setPassword(passwordEncoder.encode("admin123"));
            defaultAdmin.setRoles(Arrays.asList(roleAdmin));
            defaultAdmin.setEmail("admin@gmail.com");
            defaultAdmin.setManager(null);

            userRepository.save(defaultAdmin);

            LOGGER.warn("ADMIN INITIALIZED");
        }
    }


}
