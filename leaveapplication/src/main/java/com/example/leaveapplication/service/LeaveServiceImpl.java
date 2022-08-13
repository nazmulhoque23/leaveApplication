package com.example.leaveapplication.service;

import com.example.leaveapplication.configuration.CustomUserDetailsService;
import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.dto.LeaveApplicationProjection;
import com.example.leaveapplication.entity.LeaveApplication;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.mappers.LeaveMapStructMapper;
import com.example.leaveapplication.mappers.LeaveTypeMapStructMapper;
import com.example.leaveapplication.mappers.StatusMapper;
import com.example.leaveapplication.repository.LeaveRepository;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import com.example.leaveapplication.repository.UserRepository;
import com.example.leaveapplication.utils.enums.LeaveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private LeaveRepository leaveRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    LeaveMapStructMapper leaveMapper;

    @Autowired
    LeaveTypeMapStructMapper leaveTypeMapper;

    @Transactional
    @Override
    public LeaveApplicationDTO createLeave(LeaveApplicationDTO leaveApplicationDTO) {

        //SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");


        Long id =  userDetailsService.getCurrentUser().getId();
        leaveApplicationDTO.setFromDate(leaveApplicationDTO.getFromDate());
        leaveApplicationDTO.setRemark(leaveApplicationDTO.getRemark());
        leaveApplicationDTO.setToDate(leaveApplicationDTO.getToDate());

        leaveApplicationDTO.setStatus(String.valueOf(LeaveStatus.PENDING));
        leaveApplicationDTO.setLeaveType(leaveApplicationDTO.getLeaveType());
        LeaveApplication userLeaveApplication = leaveMapper.mapToEntity(leaveApplicationDTO);

        User user = new User();
        user.setId(id);

        //userLeaveApplication.setLeaveType(leaveApplicationDTO.getLeaveType());

        userLeaveApplication.setUser(user);
        leaveRepo.save(userLeaveApplication);

        LeaveApplicationDTO modifiedLeaveApplication = leaveMapper.mapToDTO(userLeaveApplication);
        return modifiedLeaveApplication;

    }

    @Override
    public List<LeaveApplicationProjection> getAllLUserLeavesByStatus(String status) {
        return leaveRepo.getAllByStatus(status);
    }

    @Override
    public List<LeaveApplicationProjection> getAllUserLeavesByType(String leaveType) {
        return leaveRepo.getAllByLeaveType(leaveType);
    }

    @Autowired
    LeaveBalanceService leaveBalanceService;

    @Transactional
    @Override
    public LeaveApplicationDTO approveorDenyUserLeave(Long id, LeaveApplicationDTO leaveDTO) {


        LeaveApplication userLeaveRequest = leaveRepo.findByUserId(id);//.orElse(null);

        User userManager = userLeaveRequest.getUser().getManager();

        Long approverId = userDetailsService.getCurrentUser().getId();
        String approverRole = userDetailsService.getCurrentUser().getAuthorities().iterator().next().toString();

        if(!approverRole.equals("MANAGER")){
            if(approverId == userLeaveRequest.getUser().getId()||
                    userManager == null||userManager.getId()==null||
                    approverId != userManager.getId()){
                return null;//have to write custom exception
            }
        }

        userLeaveRequest.setStatus(StatusMapper.mapLeaveStatus(leaveDTO.getStatus()));

        leaveRepo.save(userLeaveRequest);

        //leaveBalanceService.updateLeaveBalance();

        LeaveApplicationDTO modifiedLeaveDTO = leaveMapper.mapToDTO(userLeaveRequest);

        return modifiedLeaveDTO;
    }

}
