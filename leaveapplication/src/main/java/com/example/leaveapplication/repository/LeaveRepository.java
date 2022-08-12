package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, Long> {
    /*@Query(value = "INSERT into leaveapplication(from_date,remark,status,to_date,leavetype_id,manager_id) values(?,?,?,?,?,?)", nativeQuery = true)
    LeaveApplication save(LeaveApplication leaveApplication);*/
}
