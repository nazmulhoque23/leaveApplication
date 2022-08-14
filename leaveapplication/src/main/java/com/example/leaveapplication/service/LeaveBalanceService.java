package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.LeaveBalanceProjection;
import com.example.leaveapplication.entity.LeaveBalance;

import java.util.Date;
import java.util.List;

public interface LeaveBalanceService {
    public LeaveBalance createLeaveBalance();

    public List<LeaveBalanceProjection> showLeaveBalanceofUsers();

    //public LeaveBalanceProjection showLeaveBalance();

    public LeaveBalanceProjection showLeaveBalance();


    //public LeaveBalance updateLeaveBalance();
    //public LeaveBalance updateLeaveBalance(LeaveBalance leaveBalance);
}
