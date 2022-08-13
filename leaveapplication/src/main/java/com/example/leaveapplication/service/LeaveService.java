package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.dto.LeaveApplicationProjection;
import com.example.leaveapplication.utils.enums.LeaveStatus;

import java.util.List;

public interface LeaveService {
    LeaveApplicationDTO createLeave(LeaveApplicationDTO leaveApplicationDTO);
    List<LeaveApplicationProjection> getAllLUserLeavesByStatus(String status);
    List<LeaveApplicationProjection> getAllUserLeavesByType(String leaveType);
    LeaveApplicationDTO approveorDenyUserLeave(Long id, LeaveApplicationDTO leaveDTO);

    //LeaveApplication createLeave(LeaveApplication leaveApplication);
    //LeaveApplication createLeave(LeaveApplication leaveApplication);

    //String adminAccess();

}
