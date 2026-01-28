package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee_joining_info")
public class EmployeeJoiningInfo {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    @Column(name="dateOfJoining")
    private LocalDate dateOfJoining;
    @Column(name="confirmationDate")
    private LocalDate confirmationDate;
    @Column(name="status")
    private String status;
    @Column(name="workmail")
    private String workmail;
    @OneToOne(cascade = CascadeType.MERGE)    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeJoiningInfo(String status, LocalDate dateOfJoining, LocalDate confirmationDate, String workmail, EmployeeBasicInfo employeeBasicInfo) {
        this.status = status;
        this.dateOfJoining = dateOfJoining;
        this.confirmationDate = confirmationDate;
        this.workmail = workmail;
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public EmployeeJoiningInfo() {

    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public LocalDate getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDate confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkmail() {
        return workmail;
    }

    public void setWorkmail(String workmail) {
        this.workmail = workmail;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
}
