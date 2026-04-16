package com.geosieben.gsbworkday.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="salary")
public class Salary {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int salaryid;
@ManyToOne
@JoinColumn(name="salid")
private SalaryStructure salaryStructure;
private String month;
@ManyToOne
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
private BigDecimal monthlyGratuity;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal gross;
@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0")
private BigDecimal netPayeble;
private int status=0;
@ManyToOne
@JoinColumn(name = "processedBy")
private EmployeeBasicInfo processedBy;
private LocalDateTime createdOn=LocalDateTime.now();
private LocalDateTime updatedOn=LocalDateTime.now();
}
