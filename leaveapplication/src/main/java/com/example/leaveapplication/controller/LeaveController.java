package com.example.leaveapplication.controller;

import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.dto.LeaveApplicationProjection;
import com.example.leaveapplication.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping( "/api/")
public class LeaveController {

    @Autowired
    LeaveService leaveService;

    @PostMapping(value = "userLeave/")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<LeaveApplicationDTO> createLeaveForm(@RequestBody LeaveApplicationDTO leaveApplicationDTO){
        System.out.println(leaveApplicationDTO.getLeaveType().getId());
        return ResponseEntity.ok(leaveService.createLeave(leaveApplicationDTO));
                //leaveService.createLeave(leaveDTO));
    }

    @GetMapping("getAllLeavesByStatus/{status}")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER')")
    public ResponseEntity<List<LeaveApplicationProjection>> getAllLeavesByStatus(@PathVariable("status") String status){
        return ResponseEntity.ok(leaveService.getAllLUserLeavesByStatus(status));

    }

    @GetMapping("getAllLeavesByType/{leaveType}")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER')")
    public ResponseEntity<List<LeaveApplicationProjection>> getAllLeavesByType(@PathVariable("leaveType") String leaveType){
        return ResponseEntity.ok(leaveService.getAllUserLeavesByType(leaveType));

    }

    @PutMapping("approveOrDenyLeave/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<LeaveApplicationDTO> approveUserRequestedLeave(@PathVariable Long id, @RequestBody LeaveApplicationDTO leaveDTO){
        System.out.println(leaveDTO);

        return ResponseEntity.ok(leaveService.approveorDenyUserLeave(id, leaveDTO));
    }

    @GetMapping("getLeaveBetweenDates/")
    public ResponseEntity<?> getAllLeavesBetweenDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String fromDateParam, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String toDateParam){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate fromDate = LocalDate.parse(fromDateParam, formatter);
        LocalDate toDate = LocalDate.parse(toDateParam, formatter);
        return ResponseEntity.ok(leaveService.getLeavesBetweenDates(fromDate, toDate));


    }


    /*@PostMapping
    public ResponseEntity<?> createEmployeeLeave(@RequestBody LeaveApplicationDTO leaveDTO){
        return ResponseEntity.ok(leaveService.createLeave(leaveDTO));
    }*/
}
