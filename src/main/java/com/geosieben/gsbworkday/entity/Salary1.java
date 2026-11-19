package com.geosieben.gsbworkday.entity;

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
@Table(name="salary1")
public class Salary1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int salaryId;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "salid")
    private SalaryStructure1 salaryStructure;
    @Column(nullable = false)
    private String month;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;
    @Column(name="noofdays",columnDefinition = "VARBINARY(2000)")
    private byte[] noofdays;
    @Column(name="lopdays",columnDefinition = "VARBINARY(2000)")
    private byte[] lopdays;
    @Column(name="payableDays",columnDefinition = "VARBINARY(2000)")
    private byte[] payableDays;
    @Column(name="basic",columnDefinition = "VARBINARY(2000)")
    private byte[] basic;
    @Column(name="conveyance",columnDefinition = "VARBINARY(2000)")
    private byte[] conveyance;
    @Column(name="medicalAllowence",columnDefinition = "VARBINARY(2000)")
    private byte[] medicalAllowence;
    @Column(name="statutoryBonus",columnDefinition = "VARBINARY(2000)")
    private byte[] statutoryBonus;
    @Column(name="otherAllowences",columnDefinition = "VARBINARY(2000)")
    private byte[] otherAllowences;
    @Column(name="otherEarnings",columnDefinition = "VARBINARY(2000)")
    private byte[] otherEarnings;
    @Column(name="pfEmployeeShare",columnDefinition = "VARBINARY(2000)")
    private byte[] pfEmployeeShare;
    @Column(name="pfEmployerShare",columnDefinition = "VARBINARY(2000)")
    private byte[] pfEmployerShare;
    @Column(name="esiEmployeeShare",columnDefinition = "VARBINARY(2000)")
    private byte[] esiEmployeeShare;
    @Column(name="esiEmployerShare",columnDefinition = "VARBINARY(2000)")
    private byte[] esiEmployerShare;
    @Column(name="pt",columnDefinition = "VARBINARY(2000)")
    private byte[] pt;
    @Column(name="gratuty",columnDefinition = "VARBINARY(2000)")
    private byte[] gratuty;
    @Column(name="gross",columnDefinition = "VARBINARY(2000)")
    private byte[] gross;
    @Column(name="netPayeble",columnDefinition = "VARBINARY(2000)")
    private byte[] netPayeble;
    private int status =0;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "processedBy")
    private EmployeeBasicInfo processedBy;
    private LocalDateTime createdOn=LocalDateTime.now();
    private LocalDateTime updatedOn =LocalDateTime.now();
    @Column(name="iv",columnDefinition = "VARBINARY(32)")
    private byte[] iv;
    public Salary1(SalaryStructure1 salaryStructure, String month, EmployeeBasicInfo employeeBasicInfo, byte[] noofdays,
            byte[] lopdays, byte[] payableDays, byte[] basic, byte[] conveyance, byte[] medicalAllowence,
            byte[] statutoryBonus, byte[] otherAllowences, byte[] otherEarnings, byte[] pfEmployeeShare,
            byte[] pfEmployerShare, byte[] esiEmployeeShare, byte[] esiEmployerShare, byte[] pt, byte[] gratuty,
            byte[] gross, byte[] netPayeble, int status, EmployeeBasicInfo processedBy, LocalDateTime createdOn,
            LocalDateTime updatedOn, byte[] iv) {
        this.salaryStructure = salaryStructure;
        this.month = month;
        this.employeeBasicInfo = employeeBasicInfo;
        this.noofdays = noofdays;
        this.lopdays = lopdays;
        this.payableDays = payableDays;
        this.basic = basic;
        this.conveyance = conveyance;
        this.medicalAllowence = medicalAllowence;
        this.statutoryBonus = statutoryBonus;
        this.otherAllowences = otherAllowences;
        this.otherEarnings = otherEarnings;
        this.pfEmployeeShare = pfEmployeeShare;
        this.pfEmployerShare = pfEmployerShare;
        this.esiEmployeeShare = esiEmployeeShare;
        this.esiEmployerShare = esiEmployerShare;
        this.pt = pt;
        this.gratuty = gratuty;
        this.gross = gross;
        this.netPayeble = netPayeble;
        this.status = status;
        this.processedBy = processedBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.iv = iv;
    }
    
}
