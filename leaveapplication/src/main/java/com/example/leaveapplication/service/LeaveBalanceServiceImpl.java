package com.example.leaveapplication.service;

import com.example.leaveapplication.configuration.CustomUserDetails;
import com.example.leaveapplication.configuration.CustomUserDetailsService;
import com.example.leaveapplication.dto.LeaveBalanceProjection;
import com.example.leaveapplication.entity.*;
import com.example.leaveapplication.repository.*;
import com.example.leaveapplication.utils.enums.LeaveStatus;
import com.example.leaveapplication.utils.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private YearlyLeaveRepository yearlyLeaveRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private CustomUserDetailsService userDetails;

    @Autowired
    LeaveRepository leaveRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveBalance createLeaveBalance(){


        //Role role = roleRepo.findByRoleName(RoleEnum.USER);
        List<User> users = userRepo.findAllByRoleId(Long.valueOf(1));

        int sickLeaveDays = yearlyLeaveRepository.findMaxDay("sick leave");
        int casualLeaveDays = yearlyLeaveRepository.findMaxDay("casual leave");


        LeaveBalance leaveBalance = new LeaveBalance();

        for(User user: users){

            //if(user.getRoles().equals(role)){
                leaveBalance.setUser(user);
                leaveBalance.setSickLeaveDays(sickLeaveDays);
                leaveBalance.setCasualLeaveDays(casualLeaveDays);
                leaveBalance.setYear("2022");

            leaveBalanceRepository.save(leaveBalance);

            //}
        }
        return leaveBalance;
    }

    @Override
    public List<LeaveBalanceProjection> showLeaveBalanceofUsers() {
        return  leaveBalanceRepository.getAll();

    }

    @Override
    public LeaveBalanceProjection showLeaveBalance() {
        Long currentUserId = userDetails.getCurrentUser().getId();
        String role = userDetails.getCurrentUser().getAuthorities().iterator().next().toString();

        return leaveBalanceRepository.findLeaveBalanceByUserId(currentUserId);

    }

    @Override
    public LeaveBalance updateLeaveBalance(){
        Long currentUserId = userDetails.getCurrentUser().getId();

        LeaveBalance leaveBalance = (LeaveBalance) leaveBalanceRepository.findLeaveBalanceByUserId(currentUserId);


        LeaveApplication leaveApplication = leaveRepo.findByUserId(currentUserId);

        LeaveType type = leaveTypeRepository.findByName("sick leave");
        LeaveType type2 = leaveTypeRepository.findByName("casual leave");

        LeaveStatus status = leaveApplication.getStatus();

        if(leaveApplication.getLeaveType().equals(type) && status.equals(LeaveStatus.APPROVED)) {
            int balance = leaveBalance.getSickLeaveDays() -1;
            //balance = balance -1;
            leaveBalanceRepository.save(leaveBalance);

        }
        else if(leaveApplication.getLeaveType().equals(type2)&&status.equals(LeaveStatus.APPROVED)){
            int balance = leaveBalance.getCasualLeaveDays() - 1;
            //balance = balance -1;
            leaveBalanceRepository.save(leaveBalance);
        }

        return null;
    }

}
