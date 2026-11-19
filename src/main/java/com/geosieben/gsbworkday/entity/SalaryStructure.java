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
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="salarystructure")
public class SalaryStructure {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int salid;
@ManyToOne(cascade = CascadeType.MERGE)
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
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="addedBy")
private EmployeeBasicInfo addedBy;
private LocalDateTime createdAt=LocalDateTime.now();
public SalaryStructure(EmployeeBasicInfo employeeBasicInfo, String designation, BigDecimal basicPay,
        BigDecimal conveyance, BigDecimal medicalAllowence, BigDecimal statutoryBonus, BigDecimal otherAllowences,
        BigDecimal pfEmployeeShare, BigDecimal pfEmployerShare, BigDecimal esiEmployeeShare,
        BigDecimal esiEmployerShare, BigDecimal monthlyGratuity, BigDecimal pt, BigDecimal netPay,
        BigDecimal monthlyCtc, BigDecimal annualCtc, int isCurrentSalary, EmployeeBasicInfo addedBy,
        LocalDateTime createdAt) {
    this.employeeBasicInfo = employeeBasicInfo;
    this.designation = designation;
    this.basicPay = basicPay;
    this.conveyance = conveyance;
    this.medicalAllowence = medicalAllowence;
    this.statutoryBonus = statutoryBonus;
    this.otherAllowences = otherAllowences;
    this.pfEmployeeShare = pfEmployeeShare;
    this.pfEmployerShare = pfEmployerShare;
    this.esiEmployeeShare = esiEmployeeShare;
    this.esiEmployerShare = esiEmployerShare;
    this.monthlyGratuity = monthlyGratuity;
    this.pt = pt;
    this.netPay = netPay;
    this.monthlyCtc = monthlyCtc;
    this.annualCtc = annualCtc;
    this.isCurrentSalary = isCurrentSalary;
    this.addedBy = addedBy;
    this.createdAt = createdAt;
}

}
