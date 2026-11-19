package com.geosieben.gsbworkday.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import com.geosieben.gsbworkday.dto.PayRollRequest;
import com.geosieben.gsbworkday.dto.SalaryStructureDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;

public interface PayRollInterface {
public ResponseEntity<Map<String,String>> addToPayRoll(PayRollRequest dto); 
public List<SalaryStructureDto>  getStructures();
public List<SalaryExtraDetails>  getExtraDetails();
public ResponseEntity<Map<String,String>> addExtraDetails(String eid,double epay,double lop,String month,String type) throws Exception;
public ResponseEntity<Map<String,String>> processPayRoll(String month) throws Exception;
public ResponseEntity<Map<String,String>> authenticatePayRollUser(String password);
public ResponseEntity<Map<String,String>> changePassword (String password,String newPassword);
public List<EmployeeBasicInfo> fetchEmployees(String type,String month);
public Map<String,String> updateSalary(int salaryid,double lop,double extrapay,String month,int payid) throws Exception;
public ResponseEntity<Map<String, String>> updateSalaryExisting(int salaryid,int salid,double lopDays,double extraPay,String month) throws Exception;

}
