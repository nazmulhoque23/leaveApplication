package com.example.leaveapplication.mappers;

import com.example.leaveapplication.dto.LeaveTypeDTO;
import com.example.leaveapplication.entity.LeaveType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeaveTypeMapStructMapper {
    public LeaveTypeDTO mapToDTO(LeaveType leaveType);
    public LeaveType mapToEntity(LeaveTypeDTO leaveTypeDTO);
}
