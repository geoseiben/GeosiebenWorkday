package com.geosieben.gsbworkday.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String department;
    private String designation;
    private String managerName;
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

   

   }

