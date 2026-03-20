package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public interface EmployeeProfileProjection {
    // Basic Info
    String getFirstName();
    String getLastName();
    String getGender();
    String getEid();
    String getEmail();
    String getAddress();

    // Joining Info
    LocalDate getDateOfJoining();
    String getStatus();
    String getWorkmail();

    // Statutory Info
    String getPan();
    String getAadhar();
    String getUan();
    LocalDate getPfStartDate();

    // Emergency Contact
    String getEmergencyContactName();
    String getEmergencyContactNum();
    String getRelation();

    // Bank & Designation
    String getAccountNumber();
    String getBankName();
    String getDesignation();
}