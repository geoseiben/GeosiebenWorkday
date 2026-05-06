package com.geosieben.gsbworkday.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import com.geosieben.gsbworkday.dto.PayRollRequest;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
import com.geosieben.gsbworkday.entity.SalaryStructure;;

public interface PayRollInterface {
public ResponseEntity<Map<String,String>> addToPayRoll(PayRollRequest dto); 
public List<SalaryStructure>  getStructures();
public List<SalaryExtraDetails>  getExtraDetails();
public ResponseEntity<Map<String,String>> addExtraDetails(String eid,double epay,double lop,String month,String type);
public ResponseEntity<Map<String,String>> processPayRoll(String month);
public ResponseEntity<Map<String,String>> authenticatePayRollUser(String password);
public ResponseEntity<Map<String,String>> changePassword (String password,String newPassword);
public List<EmployeeBasicInfo> fetchEmployees(String type,String month);
public Map<String,String> updateSalary(int salaryid,double lop,double extrapay,String month,int payid);
public ResponseEntity<Map<String, String>> updateSalaryExisting(int salaryid,int salid,double lopDays,double extraPay,String month);

}
