package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tblleaves")
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String leaveType;
    private LocalDate fromDate;
    private String fromsession;
    private String toSession;
    private LocalDate toDate=LocalDate.now();
    private String Description;
    private LocalDate postingdate;
    private String adminRemark;
    private LocalDate AdminRemarkDate;
    private double NoofDays;
    private int status=0;
    @ManyToOne
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;
    @ManyToOne
    @JoinColumn(name = "appliedBy")
    private EmployeeBasicInfo appliedBy;
    @ManyToOne
    @JoinColumn(name = "approvedBy")
    private EmployeeBasicInfo approvedBy;

    public Leaves(String leaveType, LocalDate fromDate, String fromsession, LocalDate toDate,String toSession, String description, double noofDays, EmployeeBasicInfo appliedby, EmployeeBasicInfo employeeBasicInfo) {
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.fromsession = fromsession;
        this.toSession = toSession;

        this.toDate = toDate;
        Description = description;
        NoofDays = noofDays;
        this.appliedBy = appliedby;
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public Leaves() {

    }

    public String getToSession() {
        return toSession;
    }

    public void setToSession(String toSession) {
        this.toSession = toSession;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromsession() {
        return fromsession;
    }

    public void setFromsession(String fromsession) {
        this.fromsession = fromsession;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDate getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(LocalDate postingdate) {
        this.postingdate = postingdate;
    }

    public LocalDate getAdminRemarkDate() {
        return AdminRemarkDate;
    }

    public void setAdminRemarkDate(LocalDate adminRemarkDate) {
        AdminRemarkDate = adminRemarkDate;
    }

    public double getNoofDays() {
        return NoofDays;
    }

    public void setNoofDays(double noofDays) {
        NoofDays = noofDays;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public EmployeeBasicInfo getAppliedby() {
        return appliedBy;
    }

    public void setAppliedby(EmployeeBasicInfo appliedby) {
        this.appliedBy = appliedby;
    }

    public EmployeeBasicInfo getApprovedby() {
        return approvedBy;
    }

    public void setApprovedby(EmployeeBasicInfo approvedby) {
        this.approvedBy = approvedby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
