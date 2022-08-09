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
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "leaveType")
    private List<LeaveApplication> leaveApplication;

    @OneToOne(mappedBy = "leaveType")
    private YearlyLeave yearlyLeave;

    public List<LeaveApplication> getLeaveApplications() {
        return leaveApplication;
    }

    public void setLeaveApplication(List<LeaveApplication> leaveApplication) {
        this.leaveApplication = leaveApplication;
    }
}