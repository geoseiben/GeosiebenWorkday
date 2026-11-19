package com.geosieben.gsbworkday.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.PayRollRequest;
import com.geosieben.gsbworkday.dto.SalaryExtraDetailsDecryptedDto;
import com.geosieben.gsbworkday.dto.SalaryStructureDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Salary;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
import com.geosieben.gsbworkday.service.PayRollService;





@RestController
@RequestMapping("/admin/payroll")
public class PayRollRestController {
private PayRollService payRollService;

    public PayRollRestController(PayRollService payRollService) {
    this.payRollService = payRollService;
}
    @PostMapping("/addPayroll")
    public ResponseEntity<Map<String, String>>  addPayroll(@ModelAttribute PayRollRequest dto) {
        return payRollService.addToPayRoll(dto);
    }
    @GetMapping("/getsalaryStructures")
    public List<SalaryStructureDto> getsalaryStructures() {
        return payRollService.getStructures();
    }

    @PostMapping("/extraDetails") 
    public ResponseEntity<Map<String, String>>  addExtraDetails(@RequestParam("eid") String eid,@RequestParam("epay") double epay,@RequestParam("lop") double lop,@RequestParam("month") String month,@RequestParam("type") String type) throws Exception {
     return payRollService.addExtraDetails(eid, epay, lop, month, type);
    }
    @GetMapping("/getExtraDetails")
    public List<SalaryExtraDetails> getExtraDetails() {
        return payRollService.getExtraDetails();
    }

    @PostMapping("/processPayRoll")
    public ResponseEntity<Map<String, String>> processPayRoll(@RequestParam String month)throws Exception {
        System.out.println("salary Peocessed for month"+month );
        return payRollService.processPayRoll(month);
    }
    @GetMapping("/fetchpayroll")
    public List<Salary> fetchpayroll() {
        return payRollService.getPayRoll();
    }
    @GetMapping("/getemps")
    public List<EmployeeBasicInfo>  getEmployees(@RequestParam("type") String type,@RequestParam("month") String month) {
      return payRollService.fetchEmployees(type, month);
    }
    
    
    @GetMapping("/authenticateUser")
    public ResponseEntity<Map<String,String>> authenticateUser(@RequestParam("password") String password) {

        return payRollService.authenticatePayRollUser(password);
    }
    @PostMapping("/passwordChange")
    public ResponseEntity<Map<String,String>> passwordChange(@RequestParam("password") String password,@RequestParam("newPassword") String newPassword) {
return payRollService.changePassword(password, newPassword);
    }
@GetMapping("/getEncData")
public List<SalaryStructureDto> getEncData() {
    return payRollService.getEncryptedPayrollData();
}

@GetMapping("/getExtraDec")
public List<SalaryExtraDetailsDecryptedDto> getExtraDec() throws Exception {
    return payRollService.getSalaryExtraDecrypted();
}

@PostMapping("/updateExtraDetails")
public ResponseEntity<Map<String,String>> updateExtraDetails(@RequestParam("id") int id,@RequestParam("lop") double lop,@RequestParam("epay") double epay)throws Exception{
    //TODO: process POST request
    
    return payRollService.updateExtraDetails(id, lop, epay);
}

@PostMapping("/updateSalary")
public ResponseEntity<Map<String, String>> updateExistingSalary (
    @RequestParam("salid") int salid,
    @RequestParam("lopDays") double lopDays,
    @RequestParam("otherEarnings") double otherEarnings,
    @RequestParam("month") String month
) throws Exception{    
    return payRollService.updateSalaryExisting(0, salid, lopDays, otherEarnings, month);
}   
}
