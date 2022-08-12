package com.example.leaveapplication.mappers;

import com.example.leaveapplication.dto.LeaveTypeDTO;
import com.example.leaveapplication.entity.LeaveType;
import org.springframework.stereotype.Component;

@Component
public class LeaveTypeMapper implements LeaveTypeMapStructMapper{
    @Override
    public LeaveTypeDTO mapToDTO(LeaveType leaveType) {
        LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();

        leaveTypeDTO.setId(leaveType.getId());
        leaveTypeDTO.setName(leaveType.getName());
        return leaveTypeDTO;
    }

    @Override
    public LeaveType mapToEntity(LeaveTypeDTO leaveTypeDTO) {
        LeaveType leaveType = new LeaveType();

        leaveType.setId(leaveTypeDTO.getId());
        leaveType.setName(leaveTypeDTO.getName());
        return leaveType;
    }
}
