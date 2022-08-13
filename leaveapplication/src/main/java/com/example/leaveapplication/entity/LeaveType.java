package com.example.leaveapplication.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "leavetype")
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    /*@OneToOne(mappedBy = "leaveType")
    private LeaveApplication leaveApplication;

    @OneToOne(mappedBy = "leaveType")
    private YearlyLeave yearlyLeave;*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public LeaveApplication getLeaveApplication() {
        return leaveApplication;
    }

    public YearlyLeave getYearlyLeave() {
        return yearlyLeave;
    }

    public void setYearlyLeave(YearlyLeave yearlyLeave) {
        this.yearlyLeave = yearlyLeave;
    }*/
}