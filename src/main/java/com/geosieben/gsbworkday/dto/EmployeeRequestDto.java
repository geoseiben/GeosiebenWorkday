package com.geosieben.gsbworkday.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeRequestDto {

    // --- Personal Information ---
    private String employeeId;
    private String firstName;
    private String lastName;
    private String fatherName;
    private LocalDate dob; // Date of Birth
    private String gender;
    private String maritalStatus;
    private String bloodGroup;
    private String personalEmail;
    private String phone;
    private String address;

    // --- Professional / Employment Details ---
    private String workEmail;
    private Integer department;
    private String designation;
    private String manager;
    private LocalDate doj; // Date of Joining
    private String employmentStatus;
    private Integer probationMonths;
    private LocalDate confirmationDate;

    // --- Education Details ---
    private String college;
    private String degree;
    private String fieldOfStudy; // Fixed spelling from "feildofstudy"
    private String yearOfPassing;
    private Double gpa;

    // --- Financial & Statutory Details ---
    private String aadhar;
    private String pan;
    private String uan;
    private String pfNo;
    private LocalDate pfStart;
    private String esiNo;
    private String bankName;
    private String bankBranch;
    private String bankIfsc;
    private String accountNo;
    private String paymentMode;
    private String onsurity; // Insurance opt-in/out

    // --- Emergency Contact ---
    private String emergencyName;
    private String emergencyPhone;
    private String emergencyRelation;
    private String accessLevel;
    public EmployeeRequestDto(String employeeId, String firstName, String lastName, String fatherName, LocalDate dob,
            String gender, String maritalStatus, String bloodGroup, String personalEmail, String phone, String address,
            String workEmail, Integer department, String designation, String manager, LocalDate doj,
            String employmentStatus, Integer probationMonths, LocalDate confirmationDate, String college, String degree,
            String fieldOfStudy, String yearOfPassing, Double gpa, String aadhar, String pan, String uan, String pfNo,
            LocalDate pfStart, String esiNo, String bankName, String bankBranch, String bankIfsc, String accountNo,
            String paymentMode, String onsurity, String emergencyName, String emergencyPhone, String emergencyRelation,
            String accessLevel) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.dob = dob;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.bloodGroup = bloodGroup;
        this.personalEmail = personalEmail;
        this.phone = phone;
        this.address = address;
        this.workEmail = workEmail;
        this.department = department;
        this.designation = designation;
        this.manager = manager;
        this.doj = doj;
        this.employmentStatus = employmentStatus;
        this.probationMonths = probationMonths;
        this.confirmationDate = confirmationDate;
        this.college = college;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.yearOfPassing = yearOfPassing;
        this.gpa = gpa;
        this.aadhar = aadhar;
        this.pan = pan;
        this.uan = uan;
        this.pfNo = pfNo;
        this.pfStart = pfStart;
        this.esiNo = esiNo;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.bankIfsc = bankIfsc;
        this.accountNo = accountNo;
        this.paymentMode = paymentMode;
        this.onsurity = onsurity;
        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
        this.emergencyRelation = emergencyRelation;
        this.accessLevel = accessLevel;
    }


   }

