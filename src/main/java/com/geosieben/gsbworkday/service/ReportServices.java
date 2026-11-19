package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.ProjectRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service; // Import Service annotation

import com.geosieben.gsbworkday.dto.AllotmentReportProjection;
import com.geosieben.gsbworkday.dto.EmployeeTargetHoursProjection;
import com.geosieben.gsbworkday.entity.EmployeeTargetHours;
import com.geosieben.gsbworkday.repository.EmployeeTargetHoursRepository;

import jakarta.servlet.http.HttpSession;

@Service // Required so Spring registers this class as a bean
public class ReportServices {
    @Autowired 
    private  ProjectRepository projectRepository;
    @Autowired 
    private EmployeeTargetHoursRepository employeeTargetHoursRepository;
    @Autowired 
    private HttpSession httpSession;


    public List<EmployeeTargetHoursProjection> getEmployeeTargetHoursProjections() {
        return employeeTargetHoursRepository.getEmployeeTargetReport();
    }

public ResponseEntity updateTargetHoursService(int id, String eid, double targethours) {
    Map<String, String> response = new HashMap<>();
    try {
        Optional<EmployeeTargetHours> target = employeeTargetHoursRepository.findById(id);
        
        if (target.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Unable to find target record");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            EmployeeTargetHours employeeTargetHours = target.get();
            employeeTargetHours.setTargetHours(BigDecimal.valueOf(targethours));
            employeeTargetHoursRepository.save(employeeTargetHours);
            
            response.put("status", "success");
            response.put("message", "Target Hour updated successfully!");
            return ResponseEntity.ok(response);
        }
        
    } catch (Exception e) {
        response.put("status", "error");
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

public  List<AllotmentReportProjection> getAllotmentProjectionService(){
    return projectRepository.getAllotmentsReport();
}
}