package com.example.leaveapplication.controller;

import com.example.leaveapplication.dto.LeaveApplicationDTO;
import com.example.leaveapplication.entity.LeaveApplication;
import com.example.leaveapplication.repository.LeaveRepository;
import com.example.leaveapplication.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','MANAGER')")
    public ResponseEntity<LeaveApplicationDTO> getAllLeaves(){
        return ResponseEntity.ok(leaveService.getAllLUserLeaves());

    }


    @PutMapping("approveLeave/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<LeaveApplicationDTO> approveUserRequestedLeave(@PathVariable Long id, @RequestBody LeaveApplicationDTO leaveDTO){
        System.out.println(leaveDTO);

        return ResponseEntity.ok(leaveService.approveUserLeave(id, leaveDTO));
    }

    /*@PostMapping
    public ResponseEntity<?> createEmployeeLeave(@RequestBody LeaveApplicationDTO leaveDTO){
        return ResponseEntity.ok(leaveService.createLeave(leaveDTO));
    }*/
}
