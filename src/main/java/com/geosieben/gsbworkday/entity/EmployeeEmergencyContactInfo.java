package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

@Entity
@Table(name="employee_emergcontact_info")
public class EmployeeEmergencyContactInfo {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    private String emergencyContactName;
    private String emergencyContactNum;
    private String relation;

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeEmergencyContactInfo(String emergencyContactName, String emergencyContactNum, String relation, EmployeeBasicInfo employeeBasicInfo) {
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNum = emergencyContactNum;
        this.relation = relation;
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public EmployeeEmergencyContactInfo() {

    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactNum() {
        return emergencyContactNum;
    }

    public void setEmergencyContactNum(String emergencyContactNum) {
        this.emergencyContactNum = emergencyContactNum;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
}
