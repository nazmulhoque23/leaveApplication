package com.example.leaveapplication.service;

import com.example.leaveapplication.configuration.CustomUserDetailsService;
import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.dto.LeaveApplicationProjection;
import com.example.leaveapplication.dto.LeaveBalanceProjection;
import com.example.leaveapplication.entity.LeaveApplication;
import com.example.leaveapplication.entity.LeaveBalance;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.mappers.LeaveMapStructMapper;
import com.example.leaveapplication.mappers.LeaveTypeMapStructMapper;
import com.example.leaveapplication.mappers.StatusMapper;
import com.example.leaveapplication.repository.LeaveBalanceRepository;
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

    @Autowired
    LeaveBalanceService leaveBalanceService;

    @Autowired
    LeaveBalanceRepository leaveBalanceRepository;

    @Transactional
    @Override
    public LeaveApplicationDTO createLeave(LeaveApplicationDTO leaveApplicationDTO) {

        //SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");


        Long id =  userDetailsService.getCurrentUser().getId();

        LeaveBalance leaveBalance = leaveBalanceRepository.findLeaveBalanceByUserId(id);

        if(leaveBalance.getSickLeaveDays()<=0  && leaveBalance.getCasualLeaveDays()<=0){
            throw new RuntimeException("SORRY YOUR LEAVE DO NOT HAVE SUFFICIENT LEAVE BALANCES");
        }

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



    @Transactional
    @Override
    public LeaveApplicationDTO approveorDenyUserLeave(Long id, LeaveApplicationDTO leaveDTO) {


        //LeaveApplication userLeaveRequest = leaveRepo.findByUserId(id);//.orElse(null);
        LeaveApplication userLeaveRequest = leaveRepo.findById(id).orElseThrow(()->{throw new RuntimeException("DATA NOT FOUND");});

        LeaveBalance projectedLeaveBalance = leaveBalanceRepository.findLeaveBalanceByUserId(id);

        User userManager = userLeaveRequest.getUser().getManager();

        Long approverId = userDetailsService.getCurrentUser().getId();
        String approverRole = userDetailsService.getCurrentUser().getAuthorities().iterator().next().toString();

        if(!approverRole.equals("MANAGER")){
            if(approverId == userLeaveRequest.getUser().getId()||
                    userManager == null||userManager.getId()==null||
                    approverId != userManager.getId()){
                throw new RuntimeException("YOU DO NOT HAVE THE AUTHORITY TO APPROVE OR DENY THE REQUEST");//have to write custom exception
            }
        }

        if(StatusMapper.mapLeaveStatus(leaveDTO.getStatus()).equals(LeaveStatus.APPROVED)){
            userLeaveRequest.setStatus(StatusMapper.mapLeaveStatus(leaveDTO.getStatus()));
            if(userLeaveRequest.getStatus().equals(LeaveStatus.APPROVED)){
                int balance = projectedLeaveBalance.getSickLeaveDays()-1;
                projectedLeaveBalance.setSickLeaveDays(balance);
                leaveBalanceRepository.save(projectedLeaveBalance);
            }

            else if (userLeaveRequest.getStatus().equals("casual leave")){
                int balance = projectedLeaveBalance.getCasualLeaveDays()-1;
                projectedLeaveBalance.setCasualLeaveDays(balance);
                leaveBalanceRepository.save(projectedLeaveBalance);
            }
            leaveRepo.save(userLeaveRequest);
        }

        userLeaveRequest.setStatus(StatusMapper.mapLeaveStatus(leaveDTO.getStatus()));

        leaveRepo.save(userLeaveRequest);



        //leaveBalanceService.updateLeaveBalance();

        LeaveApplicationDTO modifiedLeaveDTO = leaveMapper.mapToDTO(userLeaveRequest);

        return modifiedLeaveDTO;
    }

}
