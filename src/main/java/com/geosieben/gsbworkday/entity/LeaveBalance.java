package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

@Entity
@Table(name="leavebalance")
public class LeaveBalance {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    private double casualleaves;
    private double sickleaves;
    private double restrictedholidays;
    private double earnedleaves;

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public LeaveBalance(EmployeeBasicInfo employeeBasicInfo, double earnedleaves, double restrictedholidays, double sickleaves, double casualleaves) {
        this.employeeBasicInfo = employeeBasicInfo;
        this.earnedleaves = earnedleaves;
        this.restrictedholidays = restrictedholidays;
        this.sickleaves = sickleaves;
        this.casualleaves = casualleaves;
    }


    public LeaveBalance() {

    }

    public double getCasualleaves() {
        return casualleaves;
    }

    public void setCasualleaves(double casualleaves) {
        this.casualleaves = casualleaves;
    }

    public double getSickleaves() {
        return sickleaves;
    }

    public void setSickleaves(double sickleaves) {
        this.sickleaves = sickleaves;
    }

    public double getRestrictedholidays() {
        return restrictedholidays;
    }

    public void setRestrictedholidays(double restrictedholidays) {
        this.restrictedholidays = restrictedholidays;
    }

    public double getEarnedleaves() {
        return earnedleaves;
    }

    public void setEarnedleaves(double earnedleaves) {
        this.earnedleaves = earnedleaves;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
}
