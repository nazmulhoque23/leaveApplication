package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.YearlyLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface YearlyLeaveRepository extends JpaRepository<YearlyLeave, Long> {
    List<YearlyLeave> findAll();
    YearlyLeave findAllById(Long id);
}
