package com.example.leaveapplication.controller;

import com.example.leaveapplication.entity.YearlyLeave;
import com.example.leaveapplication.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequestMapping("/api")
public class YearlyLeaveController {

    @Autowired
    private YearlyLeaveService yearlyLeaveService;

    @PostMapping("/create-yearly-leave-balance")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createYearlyLeave(@RequestBody YearlyLeave yearlyLeave){
        return ResponseEntity.ok(yearlyLeaveService.createYearlyLeaveBalance(yearlyLeave));
    }

    @GetMapping("/getLeaveBalance/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<YearlyLeave> getLeaveBalance(@PathVariable("id") Long id){
        return ResponseEntity.ok(yearlyLeaveService.getLeaveBalance(id));
    }
}
