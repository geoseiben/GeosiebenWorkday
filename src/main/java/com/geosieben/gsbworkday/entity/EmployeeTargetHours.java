package com.geosieben.gsbworkday.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="employeetargethours")
public class EmployeeTargetHours {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="EID")
private EmployeeBasicInfo employee;
private String month;
private int monthNum;
private int year;
@Column(columnDefinition = "DECIMAL(5,2)")
private BigDecimal extendedTarget;
@Column(columnDefinition = "DECIMAL(5,2)")
private BigDecimal targetHours;
@Column(columnDefinition = "DECIMAL(5,2)")
private BigDecimal carriedHours;
@Column(columnDefinition = "DECIMAL(5,2)")
private BigDecimal carriedAfterProcess;
private LocalDateTime createdAt=LocalDateTime.now();
private LocalDateTime updatedAt=LocalDateTime.now();
public EmployeeTargetHours(EmployeeBasicInfo employee, String month, int monthNum, int year, BigDecimal extendedTarget,
        BigDecimal targetHours, BigDecimal carriedHours, BigDecimal carriedAfterProcess, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    this.employee = employee;
    this.month = month;
    this.monthNum = monthNum;
    this.year = year;
    this.extendedTarget = extendedTarget;
    this.targetHours = targetHours;
    this.carriedHours = carriedHours;
    this.carriedAfterProcess = carriedAfterProcess;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
}

}
