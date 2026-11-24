package com.geosieben.gsbworkday.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.JoiningInfoRepository;

import jakarta.transaction.Transactional;

@Service 
public class EmployeeUpdatService {
    private  final BasicInfoRepository basicInfoRepository;
    private final JoiningInfoRepository joiningInfoRepository;
    public EmployeeUpdatService(BasicInfoRepository basicInfoRepository, JoiningInfoRepository joiningInfoRepository) {
        this.basicInfoRepository = basicInfoRepository;
        this.joiningInfoRepository = joiningInfoRepository;
    }
    
 @Transactional
public ResponseEntity<?> updateBasicInfoService(
        String employeeId,
        String address,
        String bloodGroup,
        LocalDate dob,
        String firstName,
        String gender,
        String lastName,
        String maritalStatus,
        String personalEmail,
        String phone
) {
    EmployeeBasicInfo basic = basicInfoRepository.findEmployeeBasicInfoByEID(employeeId);
    if (basic == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Employee basic info not found for ID: " + employeeId));
    }

    basic.setAddress(nullIfBlank(address));
    basic.setBloodGroup(nullIfBlank(bloodGroup));
    basic.setDob(dob);
    basic.setFirstName(nullIfBlank(firstName));
    basic.setLastName(nullIfBlank(lastName));
    basic.setEmail(nullIfBlank(personalEmail));
    basic.setGender(nullIfBlank(gender));
    basic.setMaritalStatus(nullIfBlank(maritalStatus));
    basic.setPhone(nullIfBlank(phone));

    EmployeeBasicInfo saved = basicInfoRepository.save(basic);
    return ResponseEntity.ok(saved);
}

private String nullIfBlank(String value) {
    return (value == null || value.isBlank()) ? null : value.trim();
}
}
