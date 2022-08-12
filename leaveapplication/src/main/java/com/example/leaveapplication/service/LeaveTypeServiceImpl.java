package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.LeaveTypeDTO;
import com.example.leaveapplication.entity.LeaveType;
import com.example.leaveapplication.mappers.LeaveTypeMapStructMapper;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService{

    @Autowired
    LeaveTypeRepository leaveTypeRepository;

    @Autowired
    LeaveTypeMapStructMapper leaveTypeMapper;

    @Override
    public LeaveTypeDTO createLeaveType(LeaveTypeDTO leaveTypeDTO) {
        /*if(leaveTypeDTO.getName()==null ||leaveTypeRepository.findByName(leaveTypeDTO.getName())!=null){
            return null;//create a custom exception
        }*/
        leaveTypeDTO.setName(leaveTypeDTO.getName());
        LeaveType leaveType = leaveTypeMapper.mapToEntity(leaveTypeDTO);
        leaveTypeRepository.save(leaveType);
        LeaveTypeDTO modifiedLeaveType = leaveTypeMapper.mapToDTO(leaveType);
        return modifiedLeaveType;
    }
}
