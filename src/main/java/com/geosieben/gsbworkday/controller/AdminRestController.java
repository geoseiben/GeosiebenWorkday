package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.service.EmployeeService;
import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.AllotmentReportProjection;
import com.geosieben.gsbworkday.dto.ConsolidatedReportProjection;
import com.geosieben.gsbworkday.dto.DayWiseHoursProjection;
import com.geosieben.gsbworkday.dto.EmployeeReportProjection;
import com.geosieben.gsbworkday.dto.EmployeeTargetHoursProjection;
import com.geosieben.gsbworkday.dto.ProjectReportProjection;
import com.geosieben.gsbworkday.dto.TargetHoursReportProjection;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






@RestController
@RequestMapping("/admin/reports")
public class AdminRestController {
    private final EmployeeService employeeService;

    @Autowired 
    private  ReportServices reportServices;

    @Autowired 
    private BasicInfoRepository basicInfoRepository;

    AdminRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

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
    @GetMapping("/getCurrentTargetReport")
    List<TargetHoursReportProjection> getCurrentTargetReport()  {
        return reportServices.currentMonthTargetHoursReportService();
    }
    @GetMapping("/getProjectReport")
    public List<ProjectReportProjection> getProjectReport() {
        return reportServices.projectWiseReportService();
    }

    @GetMapping("/getConsolidatedReport")
    public  List<ConsolidatedReportProjection> getConsolidatedReport() {
        return reportServices.consolidatedReportProjectionsService();
    }    
    
    @PostMapping("/getDayWiseProjectHours")
    public List<DayWiseHoursProjection> getDayWiseProjectHours(@RequestParam("month") String month) {
    System.out.println(month);
        return  reportServices.dayWiseHoursProjectionsService(month);
    }
    @GetMapping("/getCurrentMonthTargetData")
    public List<EmployeeTargetHoursProjection> getCurrentMonthTargetData() {
        return reportServices.getCurrentMonthEmployeeTargetHoursProjections();
    }


    
    
}
