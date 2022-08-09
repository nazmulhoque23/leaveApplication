package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, Long> {

}
