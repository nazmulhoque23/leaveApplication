package com.example.leaveapplication.dto;

import com.example.leaveapplication.entity.LeaveType;
import com.example.leaveapplication.utils.enums.LeaveStatus;

import java.util.Date;

public interface LeaveApplicationProjection {
       Date getfrom_Date();
       Date getto_Date();
       String getRemark();
       LeaveStatus getStatus();
       //@Value("#{target.leave_type.name}")
       String getleaveType();




}
