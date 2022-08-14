package com.example.leaveapplication.repository;

import com.example.leaveapplication.dto.LeaveApplicationProjection;
import com.example.leaveapplication.entity.LeaveApplication;
import com.example.leaveapplication.utils.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, Long> {
    /*@Query(value = "INSERT into leaveapplication(from_date,remark,status,to_date,leavetype_id,manager_id) values(?,?,?,?,?,?)", nativeQuery = true)
    LeaveApplication save(LeaveApplication leaveApplication);*/
    //

    @Query(value = "select Leaveapplication.from_date, Leaveapplication.to_date, Leaveapplication.status,  Leaveapplication.remark, Leavetype.name as leaveType from Leaveapplication left outer join Leavetype on Leaveapplication.leave_type =  Leavetype.id where status =:status", nativeQuery = true)
    List<LeaveApplicationProjection> getAllByStatus(String status);

    @Query(value = "select Leaveapplication.from_date, Leaveapplication.to_date, Leaveapplication.status,  Leaveapplication.remark, Leavetype.name as leaveType from Leaveapplication left outer join Leavetype on Leaveapplication.leave_type =  Leavetype.id where Leavetype.name =:leaveType", nativeQuery = true)
    List<LeaveApplicationProjection> getAllByLeaveType(String leaveType);

    @Query(value = "select * from Leaveapplication left join users on Leaveapplication.user_id =users.id where users.id = :userId  ", nativeQuery = true)
    List<LeaveApplication> findByUserId(Long userId);

    LeaveApplication findByStatus(LeaveStatus status);
//Leaveapplication.from_date, Leaveapplication.to_date, Leaveapplication.status, Leaveapplication.remark, Leavetype.name as leaveType
    @Query(value = "select Leaveapplication.from_date, Leaveapplication.to_date, Leaveapplication.status,  Leaveapplication.remark, Leavetype.name as leaveType from Leaveapplication left outer join Leavetype on Leaveapplication.leave_type =  Leavetype.id where Leaveapplication.from_date>=:fromDate and Leaveapplication.to_date<=:toDate  ", nativeQuery = true)
    List<LeaveApplicationProjection> findAllbetweenDates(LocalDate fromDate, LocalDate toDate);
    //LeaveApplication findByUser(Long id);

    @Query(value = "select Leaveapplication.from_date, Leaveapplication.to_date, Leaveapplication.status, Leaveapplication.remark, Leavetype.name as leaveType from Leaveapplication join Leavetype on Leaveapplication.leave_type = Leavetype.id join Users on Leaveapplication.user_id =  Users.id where Users.id = :id and Leaveapplication.status =:status", nativeQuery = true)
    List<LeaveApplicationProjection> findAllLeaveByStatus(Long id, String status);

    @Query(value = "select Leaveapplication.from_date, Leaveapplication.to_date, Leaveapplication.status, Leaveapplication.remark, Leavetype.name as leaveType from Leaveapplication join Leavetype on Leaveapplication.leave_type = Leavetype.id join Users on Leaveapplication.user_id =  Users.id where Users.id = :id and Leavetype.name =:leaveType", nativeQuery = true)
    List<LeaveApplicationProjection> findAllLeaveByType(Long id, String leaveType);
}
