package com.example.leaveapplication.service;

import com.example.leaveapplication.entity.LeaveType;
import com.example.leaveapplication.entity.YearlyLeave;
import com.example.leaveapplication.mappers.LeaveTypeMapStructMapper;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import com.example.leaveapplication.repository.YearlyLeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearlyLeaveServiceImpl implements YearlyLeaveService{

    @Autowired
    private YearlyLeaveRepository yearlyLeaveRepo;

    @Autowired
    private LeaveTypeMapStructMapper leaveTypeMapper;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public YearlyLeave createYearlyLeaveBalance(YearlyLeave yearlyLeave) {
        yearlyLeave.setYear(yearlyLeave.getYear());
        yearlyLeave.setMaxDay(yearlyLeave.getMaxDay());

        LeaveType leaveType = leaveTypeRepository.findByName(yearlyLeave.getLeaveType().getName());
        //String name = leaveTypeRepository.findByName(leaveType.getName());
        yearlyLeave.setLeaveType(leaveType);

        yearlyLeaveRepo.save(yearlyLeave);
        return yearlyLeave;

    }

    @Override
    public YearlyLeave getLeaveBalance(Long id) {
        return yearlyLeaveRepo.findAllById(id);
    }

    /*@Override
    public List<YearlyLeave> getLeaveBalance() {
        return yearlyLeaveRepo.findAll();
    }*/



}
