package com.example.leaveapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "leavebalance")
public class LeaveBalance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sick_leave")
    private int sickLeaveDays;

    @Column(name = "casual_leave")
    private int casualLeaveDays;

    @Column(name = "year")
    private String year;

    /*@OneToOne
    @JoinColumn(name = "leave_application")
    private LeaveApplication leaveApplication;*/

    /*@Column(name = "sick_leave")
    private String sickLeave;

    @Column(name = "sick_leave_days")
    private int maxDay = 20;

    @Column(name = "casual_leave")
    private String casualLeave;

    @Column(name = "casual_leave_days")
    private int maxDay1 = 20;*/


}
