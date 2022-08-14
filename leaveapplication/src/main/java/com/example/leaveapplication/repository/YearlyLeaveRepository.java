package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.YearlyLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface YearlyLeaveRepository extends JpaRepository<YearlyLeave, Long> {
    List<YearlyLeave> findAll();
    YearlyLeave findAllById(Long id);

    @Query(value = "select maximum_day from yearly_leave left outer join Leavetype on yearly_leave.leave_type_id = Leavetype.id where Leavetype.name = :type ", nativeQuery = true)
    Long findMaxDay(String type);
}
