package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_designation_info")
public class EmployeeDesignationInfo {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    @Column(name = "designation")
    private String designation;

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeDesignationInfo(EmployeeBasicInfo employeeBasicInfo, String designation) {
        this.employeeBasicInfo = employeeBasicInfo;
        this.designation = designation;
    }

    public EmployeeDesignationInfo() {

    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
