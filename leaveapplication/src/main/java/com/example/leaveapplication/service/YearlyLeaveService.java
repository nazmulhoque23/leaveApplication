package com.example.leaveapplication.service;

import com.example.leaveapplication.entity.YearlyLeave;

import java.util.List;

public interface YearlyLeaveService {
    public YearlyLeave createYearlyLeaveBalance(YearlyLeave yearlyLeave);
    //public List<YearlyLeave> getLeaveBalance();
    public YearlyLeave getLeaveBalance(Long id);
}
