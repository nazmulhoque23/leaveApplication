package com.example.leaveapplication.repository;

import com.example.leaveapplication.dto.LeaveBalanceProjection;
import com.example.leaveapplication.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    /*@Query(value = "select users.id from LeaveBalance left outer join users on leavebalance.user_id = users.id", nativeQuery = true)
    Long findByUserId();*/

    //@Query("select LeaveBalance.sickLeaveDays, LeaveBalance.casualLeaveDays, LeaveBalance.year, User.userName from LeaveBalance left outer join User on LeaveBalance.user= User")
    @Query(value = "select leavebalance.sick_leave, leavebalance.casual_leave, users.user_name, leavebalance.year  from leavebalance left join users on leavebalance.user_id =users.id", nativeQuery = true)
    List<LeaveBalanceProjection> getAll();
///
    @Query(value = "select leavebalance.sick_leave, leavebalance.casual_leave, leavebalance.year, users.user_name from leavebalance left join users on leavebalance.user_id =users.id where users.id = :id ", nativeQuery = true)
    LeaveBalanceProjection findLeaveBalanceByUserId(Long id);
}
