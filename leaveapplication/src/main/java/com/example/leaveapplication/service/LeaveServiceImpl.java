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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        LeaveBalance leaveBalance = leaveBalanceRepository.findLeaveBalanceByUserIdNotProjection(id);

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

        Long id =  userDetailsService.getCurrentUser().getId();

        List<LeaveApplication> findLeaveApplication = leaveRepo.findByUserId(id);
        for(LeaveApplication leaveapp:findLeaveApplication){
            if(leaveapp.getUser().getId().equals(id)){
                return leaveRepo.findAllLeaveByStatus(id, status);
            }

        }

        return leaveRepo.getAllByStatus(status);
    }

    @Override
    public List<LeaveApplicationProjection> getAllUserLeavesByType(String leaveType) {
        Long id =  userDetailsService.getCurrentUser().getId();

        List<LeaveApplication> findLeaveApplication = leaveRepo.findByUserId(id);
        for(LeaveApplication leaveapp:findLeaveApplication){
            if(leaveapp.getUser().getId().equals(id)){
                return leaveRepo.findAllLeaveByType(id, leaveType);
            }

        }

        return leaveRepo.getAllByLeaveType(leaveType);
    }



    @Transactional
    @Override
    public LeaveApplicationDTO approveorDenyUserLeave(Long id, LeaveApplicationDTO leaveDTO) {


        //LeaveApplication userLeaveRequest = leaveRepo.findByUserId(id);//.orElse(null);
        LeaveApplication userLeaveRequest = leaveRepo.findById(id).orElseThrow(()->{throw new RuntimeException("DATA NOT FOUND");});

        Long userId =  userLeaveRequest.getUser().getId();
        LeaveBalance projectedLeaveBalance = leaveBalanceRepository.findLeaveBalanceByUserIdNotProjection(userId);

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

        LocalDate fromDate = userLeaveRequest.getFromDate();
        LocalDate toDate = userLeaveRequest.getTo_date();
        Long day1 = Long.valueOf(fromDate.getDayOfMonth());
        Long day2 = Long.valueOf(toDate.getDayOfMonth());

        Long duration = day2 -day1;


        if(StatusMapper.mapLeaveStatus(leaveDTO.getStatus()).equals(LeaveStatus.APPROVED)){
            userLeaveRequest.setStatus(StatusMapper.mapLeaveStatus(leaveDTO.getStatus()));
            if(userLeaveRequest.getLeaveType().getName().equals("sick leave")){
                Long balance = projectedLeaveBalance.getSickLeaveDays() - duration;
                projectedLeaveBalance.setSickLeaveDays(balance);
                leaveBalanceRepository.save(projectedLeaveBalance);
            }

            else if (userLeaveRequest.getLeaveType().getName().equals("casual leave")){
                Long balance = projectedLeaveBalance.getCasualLeaveDays() - duration;
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

    @Override
    public List<LeaveApplicationProjection> getLeavesBetweenDates(LocalDate fromDate, LocalDate toDate) {

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy, MMM d, EEEE", Locale.forLanguageTag("sw-TZ"));


        return leaveRepo.findAllbetweenDates(fromDate, toDate);
    }

}
