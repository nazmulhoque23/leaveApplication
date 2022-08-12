package com.example.leaveapplication.mappers;

import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.entity.LeaveApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeaveMapStructMapper {
    public LeaveApplicationDTO mapToDTO(LeaveApplication leaveApplication);
    public LeaveApplication mapToEntity(LeaveApplicationDTO leaveApplicationDTO);
}
