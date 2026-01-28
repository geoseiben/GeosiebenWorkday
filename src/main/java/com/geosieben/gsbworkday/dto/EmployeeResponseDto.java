package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public class EmployeeResponseDto {
    private String eid;
    private String name;
    private LocalDate joiningDate;
    private  String designation;
    private String phone;
    private String email;
    private String status;
    private String workmail;
    private String fieldOfStudy;
    private String yearOfPassing;
    private LocalDate dob;

    public EmployeeResponseDto(String eid, String name, LocalDate joiningDate,
                               String designation, String phone, String email,
                               String status, String workmail, String fieldOfStudy,
                               String yearOfPassing, LocalDate dob) {
        this.eid = eid;
        this.name = name;
        this.joiningDate = joiningDate;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.workmail = workmail;
        this.fieldOfStudy = fieldOfStudy;
        this.yearOfPassing = yearOfPassing;
        this.dob = dob;
    }


    public String getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(String yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}


