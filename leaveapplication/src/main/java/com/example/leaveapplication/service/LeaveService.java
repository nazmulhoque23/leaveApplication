package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.entity.LeaveApplication;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LeaveService {
    LeaveApplicationDTO createLeave(LeaveApplicationDTO leaveApplicationDTO);
    LeaveApplicationDTO getAllLUserLeaves();
    LeaveApplicationDTO approveUserLeave(Long id, LeaveApplicationDTO leaveDTO);

    //LeaveApplication createLeave(LeaveApplication leaveApplication);
    //LeaveApplication createLeave(LeaveApplication leaveApplication);

    //String adminAccess();

}
