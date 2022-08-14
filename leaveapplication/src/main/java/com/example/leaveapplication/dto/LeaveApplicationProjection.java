package com.example.leaveapplication.dto;

import com.example.leaveapplication.entity.LeaveType;
import com.example.leaveapplication.utils.enums.LeaveStatus;

import java.time.LocalDate;

public interface LeaveApplicationProjection {
       LocalDate getfrom_Date();
       LocalDate getto_Date();
       String getRemark();
       LeaveStatus getStatus();
       //@Value("#{target.leave_type.name}")
       String getleaveType();




}
