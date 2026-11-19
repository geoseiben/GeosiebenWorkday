package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="salaryextradetails1")
public class SalaryExtraDetails1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "EID",nullable = false)
    private EmployeeBasicInfo employeeBasicInfo;
    private String month;
    private byte[] lopindays;
    private byte[] extraPay;
    private byte[] iv;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "addedBy")
    private EmployeeBasicInfo addedBy;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updatedOn=LocalDateTime.now();
    public SalaryExtraDetails1(EmployeeBasicInfo employeeBasicInfo, String month, byte[] lopindays, byte[] extraPay,
            byte[] iv, EmployeeBasicInfo addedBy, LocalDateTime createdAt, LocalDateTime updatedOn) {
        this.employeeBasicInfo = employeeBasicInfo;
        this.month = month;
        this.lopindays = lopindays;
        this.extraPay = extraPay;
        this.iv = iv;
        this.addedBy = addedBy;
        this.createdAt = createdAt;
        this.updatedOn = updatedOn;
    }

}
