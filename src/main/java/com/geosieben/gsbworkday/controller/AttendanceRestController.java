package com.geosieben.gsbworkday.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.geosieben.gsbworkday.dto.*;
import com.geosieben.gsbworkday.service.AttendanceService;
import com.geosieben.gsbworkday.service.EmployeeService;

import org.springframework.web.bind.annotation.PostMapping;

;
@RestController
@RequestMapping("/admin")
public class AttendanceRestController {
    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;


    public AttendanceRestController(AttendanceService attendanceService, EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    @GetMapping("/getAttendanceData")
    public List<AttendanceResponseDto> getAttendanceData(@RequestParam String date){
        System.out.println(date);
        return attendanceService.getAttendanceData(date);
    }

    @PostMapping("/updateAttendance")
    public ResponseEntity<?>  updateAttendance(@RequestParam("eid") String eid,@RequestParam("status") String status ,@RequestParam("date") String date) {
      return attendanceService.markAttendace(eid, status, date);
       
    }

    @PostMapping("/markAllPresent")
    public ResponseEntity<?> markAllPresent(@RequestParam String date) {
        return attendanceService.markAttendanceForAll(date);
    }
    
    @GetMapping("/getMonths")
    public  List<MonthProjection> getMonths(){
        return  attendanceService.getMonths();
    }

    @GetMapping("/getMasterReport")
    public List<AttendenceMasterReportProjection>  getMasterReport(@RequestParam("month") String month) {
        return attendanceService.getMasterData(month);
    }
    @GetMapping("/")
    public List<EmployeeDataProjection> getEmployeeList(){
        return  employeeService.getActiveEmployeeProjectionsService();
    }
    
}
