package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.AllotmentReportProjection;
import com.geosieben.gsbworkday.dto.EmployeeReportProjection;
import com.geosieben.gsbworkday.dto.EmployeeTargetHoursProjection;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.service.ReportServices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/admin/reports")
public class AdminRestController {
    @Autowired 
    private  ReportServices reportServices;

    @Autowired 
    private BasicInfoRepository basicInfoRepository;

    @GetMapping("/getTargetHours")
    public List<EmployeeTargetHoursProjection> getTargetHours() {
        return reportServices.getEmployeeTargetHoursProjections();
    }

    @PutMapping("/updateTargetHours")
    public ResponseEntity updateTargetHours(@RequestParam("id") int id,@RequestParam("eid") String eid,@RequestParam("targethours") double targethours) {

        return reportServices.updateTargetHoursService(id, eid, targethours);
    }

    @GetMapping("/getAllotmentReport")
    public List<AllotmentReportProjection> getAllotmentReport() {
        return reportServices.getAllotmentProjectionService();
    }
    @GetMapping("/employeeReport")
    public List<EmployeeReportProjection> getEmployeeReport() {
        return basicInfoRepository.getEmployeeReport();
    }
    
}
