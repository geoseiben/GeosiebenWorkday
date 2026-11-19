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
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
@Table(name="salary")
public class Salary {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int salaryid;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="salid")
private SalaryStructure salaryStructure;
private String month;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="EID")
private EmployeeBasicInfo employeeBasicInfo;
@Column(columnDefinition = "DECIMAL(3,1) DEFAULT 0")
private BigDecimal noofdays;
@Column(columnDefinition = "DECIMAL(3,1) DEFAULT 0")
private BigDecimal lopDays;
@Column(columnDefinition = "DECIMAL(3,1) DEFAULT 0")
private BigDecimal payebaleDays;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal basic;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal conveyance;

@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal medicalAllowance;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal statutoryBonus;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal otherAllowance;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal otherEarnings;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal pfEmployeeShare;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal pfEmployerShare;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal esiEmployeeShare;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal esiEmployerShare;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal pt;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal monthlyGratuity;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal gross;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal netPayeble;
private int status=0;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "processedBy")
private EmployeeBasicInfo processedBy;
private LocalDateTime createdOn=LocalDateTime.now();
private LocalDateTime updatedOn=LocalDateTime.now();
public Salary(SalaryStructure salaryStructure, String month, EmployeeBasicInfo employeeBasicInfo, BigDecimal noofdays,
        BigDecimal lopDays, BigDecimal payebaleDays, BigDecimal basic, BigDecimal conveyance,
        BigDecimal medicalAllowance, BigDecimal statutoryBonus, BigDecimal otherAllowance, BigDecimal otherEarnings,
        BigDecimal pfEmployeeShare, BigDecimal pfEmployerShare, BigDecimal esiEmployeeShare,
        BigDecimal esiEmployerShare, BigDecimal pt, BigDecimal monthlyGratuity, BigDecimal gross, BigDecimal netPayeble,
        int status, EmployeeBasicInfo processedBy, LocalDateTime createdOn, LocalDateTime updatedOn) {
    this.salaryStructure = salaryStructure;
    this.month = month;
    this.employeeBasicInfo = employeeBasicInfo;
    this.noofdays = noofdays;
    this.lopDays = lopDays;
    this.payebaleDays = payebaleDays;
    this.basic = basic;
    this.conveyance = conveyance;
    this.medicalAllowance = medicalAllowance;
    this.statutoryBonus = statutoryBonus;
    this.otherAllowance = otherAllowance;
    this.otherEarnings = otherEarnings;
    this.pfEmployeeShare = pfEmployeeShare;
    this.pfEmployerShare = pfEmployerShare;
    this.esiEmployeeShare = esiEmployeeShare;
    this.esiEmployerShare = esiEmployerShare;
    this.pt = pt;
    this.monthlyGratuity = monthlyGratuity;
    this.gross = gross;
    this.netPayeble = netPayeble;
    this.status = status;
    this.processedBy = processedBy;
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
}


}
