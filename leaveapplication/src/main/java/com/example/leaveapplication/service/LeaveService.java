package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.dto.LeaveApplicationProjection;
import com.example.leaveapplication.entity.LeaveApplication;
import com.example.leaveapplication.entity.LeaveBalance;
import com.example.leaveapplication.utils.enums.LeaveStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public interface LeaveService {
    LeaveApplicationDTO createLeave(LeaveApplicationDTO leaveApplicationDTO);
    List<LeaveApplicationProjection> getAllLUserLeavesByStatus(String status);
    List<LeaveApplicationProjection> getAllUserLeavesByType(String leaveType);
    LeaveApplicationDTO approveorDenyUserLeave(Long id, LeaveApplicationDTO leaveDTO);
    public List<LeaveApplicationProjection> getLeavesBetweenDates(LocalDate fromDateParam, LocalDate toDateParam);


}
//getLeaveBetweenDates/?fromDateParam=2022-02-01&toDateParam=2022-02-15