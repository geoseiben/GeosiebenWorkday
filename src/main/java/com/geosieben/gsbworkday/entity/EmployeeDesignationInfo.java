package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name="department")
    private Department department;
    public EmployeeDesignationInfo(String eID, String designation, EmployeeBasicInfo employeeBasicInfo,
            Department department) {
        EID = eID;
        this.designation = designation;
        this.employeeBasicInfo = employeeBasicInfo;
        this.department = department;
    }

}
