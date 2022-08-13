package com.example.leaveapplication.controller;

import com.example.leaveapplication.dto.LeaveBalanceProjection;
import com.example.leaveapplication.entity.LeaveBalance;
import com.example.leaveapplication.service.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @PostMapping("createLeaveBalance/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createLeaveBalance(){
        return ResponseEntity.ok(leaveBalanceService.createLeaveBalance());
    }

    @GetMapping("getLeaveBalances/")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<?> getLeaveBalances(){
        return ResponseEntity.ok(leaveBalanceService.showLeaveBalanceofUsers());
    }

    @GetMapping("getLeaveBalanceofaUser/")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<LeaveBalanceProjection> getLeaveBalanceOfUser(){
        return ResponseEntity.ok(leaveBalanceService.showLeaveBalance());

    }


}
