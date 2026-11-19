package com.geosieben.gsbworkday.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="attendance")
public class Attendance {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY)
private int id;
private LocalDate date;
private String status;
private BigDecimal workingDay;
private BigDecimal leaveDays;
private String leaveType;
private LocalDateTime markedAt=LocalDateTime.now();
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="EID")
private EmployeeBasicInfo employeeBasicInfo;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="updatedBy")
private EmployeeBasicInfo updatedBy;
private LocalDateTime updatedOn=LocalDateTime.now();
public Attendance(LocalDate date, String status, BigDecimal workingDay, BigDecimal leaveDays, String leaveType,
        LocalDateTime markedAt, EmployeeBasicInfo employeeBasicInfo, EmployeeBasicInfo updatedBy,
        LocalDateTime updatedOn) {
    this.date = date;
    this.status = status;
    this.workingDay = workingDay;
    this.leaveDays = leaveDays;
    this.leaveType = leaveType;
    this.markedAt = markedAt;
    this.employeeBasicInfo = employeeBasicInfo;
    this.updatedBy = updatedBy;
    this.updatedOn = updatedOn;
}

}
