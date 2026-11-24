package com.geosieben.gsbworkday.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.geosieben.gsbworkday.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.dto.WarningPrerequsites;
import com.geosieben.gsbworkday.dto.WarningsProjection;
import com.geosieben.gsbworkday.entity.Warnings;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.WarningsRepository;
@Service 
public class WarningService {
    @Autowired 
    private WarningsRepository warningsRepository;
    @Autowired
    private  BasicInfoRepository basicInfoRepository;
    @Autowired 
    private EmailService emailService;
public  ResponseEntity<Map<String,Object>> issueWarningService(
String action,
LocalTime break_hm,
LocalDate date,
String eid,
LocalTime logout,
LocalTime login,
LocalTime workinghours_hm,
String remarks,
String type
) throws Exception{
    Map<String,Object> response=new HashMap<>();
    Warnings warnings =new Warnings();
    WarningPrerequsites pre=warningsRepository.getWarnigPrerequsites(eid);
    if(action.equals("break_time_violation")){
        warnings.setIssuedOn(LocalDateTime.now());
        warnings.setIssuedTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
        warnings.setWarningDate(date);
        warnings.setBreakHours(break_hm);
        warnings.setLoginTime(login);
        warnings.setLogoutTime(logout);
        warnings.setWorkingHours(workinghours_hm);
        warnings.setWarningType("In-Complete Working");
        warnings.setRemarks(remarks);
        warnings.setReportingManager(basicInfoRepository.findEmployeeBasicInfoByEID(pre.getReportingManager()));
        warningsRepository.save(warnings);
        emailService.sendIncompleteHoursEscalationMail(pre.getEmployeeName(),pre.getEmployeeMail(),date,login,logout,break_hm,workinghours_hm,remarks);
        emailService.sendIncompleteHoursEscalationMailManager(pre.getEmployeeName(),pre.getManagerName(), pre.getEmployeeMail(),date,login,logout,break_hm,workinghours_hm,remarks);

        response.put("status","success");
        response.put("message","warning issued Successfully");
    }
    else if(action.equals("issue_warning")){
        if(type.equals("mobile")){
        warnings.setIssuedOn(LocalDateTime.now());
        warnings.setIssuedTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
        warnings.setRemarks(remarks);
        warnings.setReportingManager(basicInfoRepository.findEmployeeBasicInfoByEID(pre.getReportingManager()));
        warnings.setWarningType("Mobile Usage");
        warningsRepository.save(warnings);
        emailService.sendMobileUsageEscalationMailManager(pre.getEmployeeName(), pre.getManagerName(),pre.getManagerMail());
        emailService.sendMobileUsageEscalationMail(pre.getEmployeeName(), pre.getEmployeeMail());
        response.put("status","success");
        response.put("message","warning issued Successfully");
        }
        else if(type.equals("performance")){
                    warnings.setIssuedOn(LocalDateTime.now());
                    warnings.setIssuedTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
                    warnings.setRemarks(remarks);
                    warnings.setReportingManager(basicInfoRepository.findEmployeeBasicInfoByEID(pre.getReportingManager()));
                    warnings.setWarningType("Performance Issue");
                    warningsRepository.save(warnings);
                    emailService.sendPerformanceEscalationMail(pre.getEmployeeName(),pre.getEmployeeMail(),remarks);
                    emailService.sendPerformanceEscalationMailManager(pre.getEmployeeName(), pre.getManagerName(), pre.getManagerMail(), remarks);
                    response.put("status","success");
                    response.put("message","warning issued Successfully");
        }
                else if(type.equals("leave")){
                    warnings.setIssuedOn(LocalDateTime.now());
                    warnings.setIssuedTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
                    warnings.setRemarks(remarks);
                    warnings.setReportingManager(basicInfoRepository.findEmployeeBasicInfoByEID(pre.getReportingManager()));
                    warnings.setWarningType("Excess Leaves");
                    warningsRepository.save(warnings);
                    emailService.sendExessLeavesEscalationMail(pre.getEmployeeName(),pre.getEmployeeMail(),remarks);
                    emailService.sendExessLeavesEscalationMailManager(pre.getEmployeeName(), pre.getManagerName(), pre.getManagerMail(), remarks);
                    response.put("status","success");
                    response.put("message","warning issued Successfully");
        }
                        else if(type.equals("quality")){
                    warnings.setIssuedOn(LocalDateTime.now());
                    warnings.setIssuedTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
                    warnings.setRemarks(remarks);
                    warnings.setReportingManager(basicInfoRepository.findEmployeeBasicInfoByEID(pre.getReportingManager()));
                    warnings.setWarningType("Quality Issue");
                    warningsRepository.save(warnings);
                    emailService.sendQualityEscalationMail(pre.getEmployeeName(),pre.getEmployeeMail(),remarks);
                    emailService.sendQualityEscalationMailManager(pre.getEmployeeName(), pre.getManagerName(), pre.getManagerMail(), remarks);
                    response.put("status","success");
                    response.put("message","warning issued Successfully");
        }
    }
return ResponseEntity.ok(response);
}

public List<WarningsProjection> findAllWarningsService(){
    return warningsRepository.findAllWarningProjections();
}
}
