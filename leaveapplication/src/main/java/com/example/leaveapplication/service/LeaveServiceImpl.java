package com.example.leaveapplication.service;

import com.example.leaveapplication.entity.LeaveApplication;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService{

    @Override
    public LeaveApplication createLeave(LeaveApplication leaveApplication) {
        if(leaveApplication.getLeaveType() == null){
            return null;
        }
        return null;
        //Long userId =
    }
}
