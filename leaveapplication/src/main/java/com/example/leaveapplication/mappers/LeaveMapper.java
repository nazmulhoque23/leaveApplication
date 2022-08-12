package com.example.leaveapplication.mappers;

import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.entity.LeaveApplication;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LeaveMapper implements LeaveMapStructMapper{

    @Autowired
    private UserMapStructMapper userMapper;

    @Autowired
    private LeaveTypeMapStructMapper leaveTypeMapper;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveApplicationDTO mapToDTO(LeaveApplication leaveApplication) {
        LeaveApplicationDTO leaveDTO = new LeaveApplicationDTO();
        leaveDTO.setId(leaveApplication.getId());
        if(leaveApplication.getUser()!=null){
            leaveDTO.setUserDTO(userMapper.mapToDTO(leaveApplication.getUser()));
        }

        if(leaveApplication.getLeaveType()!=null){
            leaveDTO.setLeaveType(leaveTypeMapper.mapToDTO(leaveApplication.getLeaveType()));
        }
        leaveDTO.setRemark(leaveApplication.getRemark());
        leaveDTO.setFromDate(leaveApplication.getFromDate());
        leaveDTO.setToDate(leaveApplication.getTo_date());
        leaveDTO.setStatus(String.valueOf(leaveApplication.getStatus()));

        return leaveDTO;
    }

    @Override
    public LeaveApplication mapToEntity(LeaveApplicationDTO leaveApplicationDTO) {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId(leaveApplicationDTO.getId());

        if(leaveApplicationDTO.getUserDTO() != null){
            leaveApplication.setUser(userMapper.mapToEntity(leaveApplicationDTO.getUserDTO()));
        }

        if(leaveApplicationDTO.getLeaveType()!=null){
            leaveApplication.setLeaveType(leaveTypeMapper.mapToEntity(leaveApplicationDTO.getLeaveType()));
                    //leaveTypeRepository.findByName(leaveApplicationDTO.getLeaveType().getName()));
                    //
        }

        leaveApplication.setRemark(leaveApplicationDTO.getRemark());
        leaveApplication.setFromDate(leaveApplicationDTO.getFromDate());
        leaveApplication.setTo_date(leaveApplicationDTO.getToDate());
        leaveApplication.setStatus(StatusMapper.mapLeaveStatus(leaveApplicationDTO.getStatus()));

        return leaveApplication;
    }
}
