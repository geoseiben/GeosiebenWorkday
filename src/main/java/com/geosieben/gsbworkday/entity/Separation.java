package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="separation")
public class Separation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int separationid;
    @ManyToOne
    @JoinColumn(name="EID")
    private EmployeeBasicInfo employeeBasicInfo;
    private String separationType;
    private LocalDate resignationDate=LocalDate.now();
    private LocalDate lastWorkingDay=null;
    private String ReasonforLeaving;
    private String description;
    private int status=0;
    private int eligibleForReHire;
    @ManyToOne
    @JoinColumn(name="ApprovedBy")
    private EmployeeBasicInfo ApprovedBy;
    private LocalDate ApprovedOn;
    @ManyToOne
    @JoinColumn(name="appliedBy")
    private EmployeeBasicInfo appliedBy;


    
    public Separation() {
    }
    public Separation(int separationid, EmployeeBasicInfo employeeBasicInfo, String separationType,
            LocalDate resignationDate, LocalDate lastWorkingDay, String reasonforLeaving, String description,
            int status, int eligibleForReHire, EmployeeBasicInfo approvedBy, LocalDate approvedOn,
            EmployeeBasicInfo appliedBy) {
        this.separationid = separationid;
        this.employeeBasicInfo = employeeBasicInfo;
        this.separationType = separationType;
        this.resignationDate = resignationDate;
        this.lastWorkingDay = lastWorkingDay;
        ReasonforLeaving = reasonforLeaving;
        this.description = description;
        this.status = status;
        this.eligibleForReHire = eligibleForReHire;
        ApprovedBy = approvedBy;
        ApprovedOn = approvedOn;
        this.appliedBy = appliedBy;
    }
    public int getSeparationid() {
        return separationid;
    }
    public void setSeparationid(int separationid) {
        this.separationid = separationid;
    }
    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }
    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
    public String getSeparationType() {
        return separationType;
    }
    public void setSeparationType(String separationType) {
        this.separationType = separationType;
    }
    public LocalDate getResignationDate() {
        return resignationDate;
    }
    public void setResignationDate(LocalDate resignationDate) {
        this.resignationDate = resignationDate;
    }
    public LocalDate getLastWorkingDay() {
        return lastWorkingDay;
    }
    public void setLastWorkingDay(LocalDate lastWorkingDay) {
        this.lastWorkingDay = lastWorkingDay;
    }
    public String getReasonforLeaving() {
        return ReasonforLeaving;
    }
    public void setReasonforLeaving(String reasonforLeaving) {
        ReasonforLeaving = reasonforLeaving;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getEligibleForReHire() {
        return eligibleForReHire;
    }
    public void setEligibleForReHire(int eligibleForReHire) {
        this.eligibleForReHire = eligibleForReHire;
    }
    public EmployeeBasicInfo getApprovedBy() {
        return ApprovedBy;
    }
    public void setApprovedBy(EmployeeBasicInfo approvedBy) {
        ApprovedBy = approvedBy;
    }
    public LocalDate getApprovedOn() {
        return ApprovedOn;
    }
    public void setApprovedOn(LocalDate approvedOn) {
        ApprovedOn = approvedOn;
    }
    public EmployeeBasicInfo getAppliedBy() {
        return appliedBy;
    }
    public void setAppliedBy(EmployeeBasicInfo appliedBy) {
        this.appliedBy = appliedBy;
    }

    
}
