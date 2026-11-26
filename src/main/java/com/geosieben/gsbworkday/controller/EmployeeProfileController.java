package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.service.EmployeeUpdatService;

import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;



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

      @PostMapping("/joining")
      public  ResponseEntity<?> joiningUpdate(
            @RequestParam("status") String status,
            @RequestParam("dateOfJoining") LocalDate dateOfJoining,
            @RequestParam(value = "confirmationDate",required = false) LocalDate confirmationDate,
            @RequestParam("employeeId") String employeeId,
            @RequestParam("manager") String manager,
            @RequestParam("workmail") String workmail

      ){
            return  employeeUpdatService.updateJoiningInfo(status, dateOfJoining, confirmationDate, employeeId, manager, workmail);
      }
   @PostMapping("/designation")
      public  ResponseEntity<?> designationUpdate(
            @RequestParam("employeeId") String employeeId,
            @RequestParam("departmentName") String departmentName,
            @RequestParam("designation") String designation

      ){
            return  employeeUpdatService.updateDesignationInfo(employeeId, departmentName, designation);
      }

@PostMapping("/bank")
public ResponseEntity<?> bankInfoUpdate(
            @RequestParam("employeeId") String employeeId,
            @RequestParam("accountNumber") String accountNumber,  
            @RequestParam("bankName") String bankName,   
            @RequestParam("branchName") String branchName,  
            @RequestParam("ifsc") String ifsc, 
            @RequestParam("paymentMode") String paymentMode

) {
  
    return employeeUpdatService.updateBankInfo(employeeId, accountNumber, bankName, branchName, ifsc, paymentMode);
}


}
