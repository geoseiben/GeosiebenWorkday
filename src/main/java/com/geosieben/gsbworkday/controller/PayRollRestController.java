package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.PayRollRequest;
import com.geosieben.gsbworkday.entity.Salary;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
import com.geosieben.gsbworkday.entity.SalaryStructure;
import com.geosieben.gsbworkday.service.PayRollService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/admin/payroll")
public class PayRollRestController {
@Autowired
private PayRollService payRollService;
    @PostMapping("/addPayroll")
    public ResponseEntity<Map<String, String>>  addPayroll(@ModelAttribute PayRollRequest dto) {
        return payRollService.addToPayRoll(dto);
    }
    @GetMapping("/getsalaryStructures")
    public List<SalaryStructure> getsalaryStructures() {
        return payRollService.getStructures();
    }

    @PostMapping("/extraDetails")
    public ResponseEntity<Map<String, String>>  addExtraDetails(@RequestParam("eid") String eid,@RequestParam("epay") double epay,@RequestParam("lop") double lop,@RequestParam("month") String month,@RequestParam("type") String type) {
     return payRollService.addExtraDetails(eid, epay, lop, month, type);
    }
    @GetMapping("/getExtraDetails")
    public List<SalaryExtraDetails> getExtraDetails() {
        return payRollService.getExtraDetails();
    }

    @PostMapping("/processPayRoll")
    public ResponseEntity<Map<String, String>> processPayRoll(@RequestParam String month) {
        System.out.println(AddonServ.changePattern(month));
        return payRollService.processPayRoll(month);
    }
    @GetMapping("/fetchpayroll")
    public List<Salary> fetchpayroll() {
        return payRollService.getPayRoll();
    }
    
    @GetMapping("/authenticateUser")
    public ResponseEntity<Map<String,String>> authenticateUser(@RequestParam("password") String password) {

        return payRollService.authenticatePayRollUser(password);
    }
    @PostMapping("/passwordChange")
    public ResponseEntity<Map<String,String>> passwordChange(@RequestParam("password") String password,@RequestParam("newPassword") String newPassword) {
return payRollService.changePassword(password, newPassword);
    }
    
    
}
