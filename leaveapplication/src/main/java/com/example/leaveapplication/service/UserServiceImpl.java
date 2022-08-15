package com.example.leaveapplication.service;

import com.example.leaveapplication.configuration.CustomUserDetailsService;
import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.LeaveBalance;
import com.example.leaveapplication.entity.Role;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.entity.YearlyLeave;
import com.example.leaveapplication.mappers.UserMapStructMapper;
import com.example.leaveapplication.repository.LeaveBalanceRepository;
import com.example.leaveapplication.repository.RoleRepository;
import com.example.leaveapplication.repository.UserRepository;
import com.example.leaveapplication.repository.YearlyLeaveRepository;
import com.example.leaveapplication.utils.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CustomUserDetailsService userDetails;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapStructMapper mapper;

    @Autowired
    private YearlyLeaveRepository yearlyLeaveRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

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

        userRepo.save(user);//assigning user to user repository



        //leave balance appointment
        Long sickLeaveDays = yearlyLeaveRepository.findMaxDay("sick leave");
        Long casualLeaveDays = yearlyLeaveRepository.findMaxDay("casual leave");

        LeaveBalance leaveBalance = new LeaveBalance();

        leaveBalance.setUser(user);
        leaveBalance.setSickLeaveDays(sickLeaveDays);



        LocalDate dt = LocalDate.now();
        Integer currentYear = dt.getYear();

        leaveBalance.setCasualLeaveDays(casualLeaveDays);
        leaveBalance.setYear(currentYear);


        leaveBalanceRepository.save(leaveBalance);//assigning leaveBalance to created user

        UserDTO modifiedUser = mapper.mapToDTO(user);
        return modifiedUser;
    }

    @Override
    public UserDTO createManager(UserDTO userDTO) {
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

    @Override
    public UserDTO changePassword(String oldPassword, String newPassword) {
        Long currentUser = userDetails.getCurrentUser().getId();

        User user = userRepo.findById(currentUser).orElseThrow(()->new RuntimeException("USER NOT FOUND"));

        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new RuntimeException("PASSWORD MISMATCH");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return mapper.mapToDTO(userRepo.save(user));
    }


}
