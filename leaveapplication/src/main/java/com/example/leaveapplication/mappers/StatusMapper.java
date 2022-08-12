package com.example.leaveapplication.mappers;

import com.example.leaveapplication.utils.enums.LeaveStatus;

public class StatusMapper {
    public static LeaveStatus mapLeaveStatus(String status){
        if(status == null){
            return LeaveStatus.PENDING;
        }

        if(status.equals(String.valueOf(LeaveStatus.APPROVED))){
            return LeaveStatus.APPROVED;
        }

        else if(status.equals(String.valueOf(LeaveStatus.REJECTED))){
            return LeaveStatus.REJECTED;
        }
        else{
            return LeaveStatus.PENDING;
        }
    }
}
