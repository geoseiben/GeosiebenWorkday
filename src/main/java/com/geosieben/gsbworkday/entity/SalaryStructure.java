package com.geosieben.gsbworkday.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="salarystructure")
public class SalaryStructure {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int salid;
@ManyToOne
@JoinColumn(name="EID")
private EmployeeBasicInfo employeeBasicInfo;
private String designation;
@Column(columnDefinition = "DECIMAL(8,2)")
private BigDecimal  basicPay=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  conveyance=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  medicalAllowence=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  statutoryBonus=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  otherAllowences=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  pfEmployeeShare=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  pfEmployerShare=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  esiEmployeeShare=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  esiEmployerShare=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  monthlyGratuity=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(6,2)")
private BigDecimal  pt=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(8,2)")
private BigDecimal  netPay=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(8,2)")
private BigDecimal  monthlyCtc=BigDecimal.valueOf(0);
@Column(columnDefinition = "DECIMAL(8,2)")
private BigDecimal  annualCtc;
private int isCurrentSalary=1;
@ManyToOne
@JoinColumn(name="addedBy")
private EmployeeBasicInfo addedBy;
private LocalDateTime createdAt=LocalDateTime.now();
}
