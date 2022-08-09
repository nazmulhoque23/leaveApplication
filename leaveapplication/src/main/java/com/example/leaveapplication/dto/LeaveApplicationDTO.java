package com.example.leaveapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LeaveApplicationDTO {
    private Long id;
    private UserDTO userDTO;
    private Date fromDate;
    private Date toDate;
    private String status;
    private String remark;
    private LeaveTypeDTO leaveTypeDTO;
}
