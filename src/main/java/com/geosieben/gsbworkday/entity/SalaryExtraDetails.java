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


@Entity
@Table(name="salaryextradetails")
public class SalaryExtraDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="EID")
private EmployeeBasicInfo employeeBasicInfo;
private String month;
@Column(columnDefinition = "decimal(3,1) default 0")
private BigDecimal lopindays;
@Column(columnDefinition = "decimal(8,2) default 0")
private BigDecimal extraPay;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="addedBy")
private EmployeeBasicInfo addedBy;
private LocalDateTime createdAt=LocalDateTime.now();


public SalaryExtraDetails() {
}
public SalaryExtraDetails(int id, EmployeeBasicInfo employeeBasicInfo, String month, BigDecimal lopindays,
        BigDecimal extraPay, EmployeeBasicInfo addedBy, LocalDateTime createdAt) {
    this.id = id;
    this.employeeBasicInfo = employeeBasicInfo;
    this.month = month;
    this.lopindays = lopindays;
    this.extraPay = extraPay;
    this.addedBy = addedBy;
    this.createdAt = createdAt;
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public EmployeeBasicInfo getEmployeeBasicInfo() {
    return employeeBasicInfo;
}
public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
    this.employeeBasicInfo = employeeBasicInfo;
}
public String getMonth() {
    return month;
}
public void setMonth(String month) {
    this.month = month;
}
public BigDecimal getLopindays() {
    return lopindays;
}
public void setLopindays(BigDecimal lopindays) {
    this.lopindays = lopindays;
}
public BigDecimal getExtraPay() {
    return extraPay;
}
public void setExtraPay(BigDecimal extraPay) {
    this.extraPay = extraPay;
}
public EmployeeBasicInfo getAddedBy() {
    return addedBy;
}
public void setAddedBy(EmployeeBasicInfo addedBy) {
    this.addedBy = addedBy;
}
public LocalDateTime getCreatedAt() {
    return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}

}
