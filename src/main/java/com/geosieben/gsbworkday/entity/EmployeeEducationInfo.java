package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

@Entity
@Table(name="employee_education_info")
public class EmployeeEducationInfo {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    private String yearOfPassing;
    private String fieldOfStudy;
    private double gpa;
    private String college;
    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeEducationInfo(EmployeeBasicInfo employeeBasicInfo, String college, double gpa, String fieldOfStudy, String yearOfPassing) {
        this.employeeBasicInfo = employeeBasicInfo;
        this.college = college;
        this.gpa = gpa;
        this.fieldOfStudy = fieldOfStudy;
        this.yearOfPassing = yearOfPassing;
    }

    public EmployeeEducationInfo() {

    }

    public String getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(String yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
}
