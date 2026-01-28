package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.time.LocalDate;

@Entity
@Table(name = "employee_statutory_info")
public class EmployeeStatutoryInfo {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    private String aadhar;
    private String pan;
    private String uan;
    private String pfNo;
    private LocalDate pfStartDate;
    private String esiNo;
    private String onSurity;
    @OneToOne(cascade = CascadeType.MERGE)    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeStatutoryInfo(String aadhar, String pan, String uan, String pfNo, LocalDate pfStartDate, String esiNo, String onSurity, EmployeeBasicInfo employeeBasicInfo) {
        this.aadhar = aadhar;
        this.pan = pan;
        this.uan = uan;
        this.pfNo = pfNo;
        this.pfStartDate = pfStartDate;
        this.esiNo = esiNo;
        this.onSurity = onSurity;
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public EmployeeStatutoryInfo() {

    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getUan() {
        return uan;
    }

    public void setUan(String uan) {
        this.uan = uan;
    }

    public String getPfNo() {
        return pfNo;
    }

    public void setPfNo(String pfNo) {
        this.pfNo = pfNo;
    }

    public LocalDate getPfStartDate() {
        return pfStartDate;
    }

    public void setPfStartDate(LocalDate pfStartDate) {
        this.pfStartDate = pfStartDate;
    }

    public String getEsiNo() {
        return esiNo;
    }

    public void setEsiNo(String esiNo) {
        this.esiNo = esiNo;
    }

    public String getOnSurity() {
        return onSurity;
    }

    public void setOnSurity(String onSurity) {
        this.onSurity = onSurity;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
}
