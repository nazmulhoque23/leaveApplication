package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    LeaveType findByName(String name);
    List<LeaveType> findByNameContaining(String name);
}
