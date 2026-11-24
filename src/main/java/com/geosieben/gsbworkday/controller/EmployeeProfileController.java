package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.service.EmployeeUpdatService;

import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;


@RestController 
@RequestMapping ("/admin/updateEmployee")
public class EmployeeProfileController {
    private final EmployeeUpdatService employeeUpdatService;
    
    public EmployeeProfileController(EmployeeUpdatService employeeUpdatService) {
        this.employeeUpdatService = employeeUpdatService;
    }

    @PostMapping("/basic")
    public  ResponseEntity<?> basicInfoUpdate(
        @RequestParam ("employeeId") String employeeId,
        @RequestParam ("address") String address,
        @RequestParam ("bloodGroup") String bloodGroup,
        @RequestParam ("dob") LocalDate dob,
        @RequestParam ("firstName") String firstName,
        @RequestParam ("gender") String gender,
        @RequestParam ("lastName") String lastName,
        @RequestParam ("meritalStatus") String meritalStatus,
        @RequestParam ("personalEmail") String personalEmail,
        @RequestParam ("phone") String phone
    ) {
     return employeeUpdatService.updateBasicInfoService(employeeId, address, bloodGroup, dob, firstName, gender, lastName, meritalStatus, personalEmail, phone);
    }

}
