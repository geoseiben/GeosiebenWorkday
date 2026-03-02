package com.geosieben.gsbworkday.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geosieben.gsbworkday.dto.StatsDto;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.ColumnResult;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "employee_basic_info")
@SqlResultSetMapping(
    name = "StatsMapping",
    classes = {
        @ConstructorResult(
            targetClass = StatsDto.class,
            columns = {
                @ColumnResult(name = "totalActiveEmployees", type = Long.class),
                @ColumnResult(name = "activeMale", type = Long.class),
                @ColumnResult(name = "activeFemale", type = Long.class),
                @ColumnResult(name = "totalSeparatedEmployees", type = Long.class),
                @ColumnResult(name = "separatedMale", type = Long.class),
                @ColumnResult(name = "separatedFemale", type = Long.class),
                @ColumnResult(name = "pendingLeaves", type = Long.class),
                @ColumnResult(name = "pendingTickets", type = Long.class)
            }
        )
    }
)

@NamedNativeQuery(
    name = "EmployeeBasicInfo.fetchAggregates",
    query = "SELECT " +
            " (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentStatus = 1) AS totalActiveEmployees, " +
            " (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentStatus = 1 AND e.gender = 'Male') AS activeMale, " +
            " (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentStatus = 1 AND e.gender = 'Female') AS activeFemale, " +
            " (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentStatus = 0) AS totalSeparatedEmployees, " +
            " (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentStatus = 0 AND e.gender = 'Male') AS separatedMale, " +
            " (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentStatus = 0 AND e.gender = 'Female') AS separatedFemale, " +
            " (SELECT COUNT(*) FROM tblleaves l WHERE l.status = 0) AS pendingLeaves, " +
            " (SELECT COUNT(*) FROM ittickets i WHERE i.status < 2) AS pendingTickets",
    resultSetMapping = "StatsMapping"
)
public class EmployeeBasicInfo {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "bloodGroup")
    private String bloodGroup;
    @Column(name = "maritalStatus")
    private String maritalStatus;
    @Column(name = "employmentStatus")
    private int  employmentStatus=1;

    public EmployeeBasicInfo() {
    }

    public EmployeeBasicInfo(String maritalStatus, String EID, String firstName, String lastName, String gender, LocalDate dob, String email, String phone, String bloodGroup) {
        this.maritalStatus = maritalStatus;
        this.EID = EID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEID() {
        return EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
