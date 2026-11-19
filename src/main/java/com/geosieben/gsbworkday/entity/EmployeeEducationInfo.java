package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data 
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
    private String degree;
    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeEducationInfo(EmployeeBasicInfo employeeBasicInfo, String college, double gpa, String fieldOfStudy, String yearOfPassing,String degree) {
        this.employeeBasicInfo = employeeBasicInfo;
        this.college = college;
        this.gpa = gpa;
        this.fieldOfStudy = fieldOfStudy;
        this.yearOfPassing = yearOfPassing;
        this.degree=degree;
    }


}
