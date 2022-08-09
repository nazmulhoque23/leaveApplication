package com.example.leaveapplication.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yearly_leave")
public class YearlyLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Calendar year;

    @Column(name = "maximum_day")
    private int maxDay;


    @OneToOne
    private LeaveType leaveType;
}
