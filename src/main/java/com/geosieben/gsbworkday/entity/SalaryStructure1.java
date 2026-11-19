package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "salarystructure1")
public class SalaryStructure1 {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int salid;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "EID")
private EmployeeBasicInfo employeeBasicInfo;
@Column(name = "designation",columnDefinition = "VARBINARY(2000)")
private byte[] designation;
@Column(name = "basicPay",columnDefinition = "VARBINARY(2000)")
private byte[] basicPay;
@Column(name = "conveyance",columnDefinition = "VARBINARY(2000)")
private byte[] conveyance;
@Column(name = "medicalAllowence",columnDefinition = "VARBINARY(2000)")
private byte[] medicalAllowence;
@Column(name = "statutoryBonus",columnDefinition = "VARBINARY(2000)")
private byte[] statutoryBonus;
@Column(name = "otherAllowences",columnDefinition = "VARBINARY(2000)")
private byte[] otherAllowences;
@Column(name = "pfEmployeeShare",columnDefinition = "VARBINARY(2000)")
private byte[] pfEmployeeShare;
@Column(name = "pfEmployerShare",columnDefinition = "VARBINARY(2000)")
private byte[] pfEmployerShare;
@Column(name = "esiEmployeeShare",columnDefinition = "VARBINARY(2000)")
private byte[] esiEmployeeShare;
@Column(name = "esiEmployerShare",columnDefinition = "VARBINARY(2000)")
private byte[] esiEmployerShare;
@Column(name = "monthlyGratuty",columnDefinition = "VARBINARY(2000)")
private byte[] monthlyGratuty;
@Column(name = "pt",columnDefinition = "VARBINARY(2000)")
private byte[] pt;
@Column(name = "netPay",columnDefinition = "VARBINARY(2000)")
private byte[] netPay;
@Column(name = "monthlyCtc",columnDefinition = "VARBINARY(2000)")
private byte[] monthlyCtc;
@Column(name = "annualCtc",columnDefinition = "VARBINARY(2000)")
private byte[] annualCtc;
private int isCurrentSalary =0;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "addedBy")
private EmployeeBasicInfo addedBy;
private LocalDateTime createdAt =LocalDateTime.now();
private LocalDateTime updatedAt=LocalDateTime.now();
@Column(name="iv",columnDefinition = "VARBINARY(32)")
private byte[] iv;
public SalaryStructure1(EmployeeBasicInfo employeeBasicInfo, byte[] designation, byte[] basicPay, byte[] conveyance,
        byte[] medicalAllowence, byte[] statutoryBonus, byte[] otherAllowences, byte[] pfEmployeeShare,
        byte[] pfEmployerShare, byte[] esiEmployeeShare, byte[] esiEmployerShare, byte[] monthlyGratuty, byte[] pt,
        byte[] netPay, byte[] monthlyCtc, byte[] annualCtc, int isCurrentSalary, EmployeeBasicInfo addedBy,
        LocalDateTime createdAt, LocalDateTime updatedAt, byte[] iv) {
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
    this.monthlyGratuty = monthlyGratuty;
    this.pt = pt;
    this.netPay = netPay;
    this.monthlyCtc = monthlyCtc;
    this.annualCtc = annualCtc;
    this.isCurrentSalary = isCurrentSalary;
    this.addedBy = addedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.iv = iv;
}

}
