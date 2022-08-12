package com.example.leaveapplication.controller;

import com.example.leaveapplication.dto.LeaveTypeDTO;
import com.example.leaveapplication.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    @PostMapping(value = "createLeaveType/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createLeaveType(@RequestBody LeaveTypeDTO leaveTypeDTO){
        return ResponseEntity.ok(leaveTypeService.createLeaveType(leaveTypeDTO));
    }
}
