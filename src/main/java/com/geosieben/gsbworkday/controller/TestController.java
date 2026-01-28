package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.dto.*;
import com.geosieben.gsbworkday.entity.*;
import com.geosieben.gsbworkday.service.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
private TestService testService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
   private LeaveServices leaveServices;
    @Autowired
    private ItTicketService itTicketService;
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/admin/testinfoo")
    public ResponseEntity<Map<String,String>> addTestInfo(@RequestParam String eid, @RequestParam String name, @RequestParam String designation){
        TestInfo testInfo=new TestInfo(eid,name);
        TestDesgn testDesgn=new TestDesgn(designation,testInfo);
        return testService.addinfo(testInfo,testDesgn);
    }

    public ResponseEntity<Map<String, String>> addEmployee(
            @ModelAttribute EmployeeRequestDto dto,
            @RequestParam("photo") MultipartFile image) {
     return employeeService.addEmployee(dto);

    }
    @GetMapping("/leaves/getbalance")
    public LeaveBalanceReposnseDto getLeaveBalance() {
        String eid = (String) httpSession.getAttribute("eid");
        return leaveServices.getLeaveBalance(eid);
    }

    @GetMapping("/admin/getemployees")
    public List<EmployeeResponseDto> getAllEmployee() {
        return employeeService.fetchEmployees();
    }
    @PostMapping("/leaves/applyleave")
    public ResponseEntity<Map<String,String>> applyLeave(@ModelAttribute LeaveRequestDto dto) throws MessagingException, UnsupportedEncodingException {
        return leaveServices.applyLeave(dto);
    }
    @GetMapping("/admin/pendingrequests")
    public List<PendingLeaveResponseDto> pendingRequests(){
return leaveServices.fetchPendingLeaves();
    }

    @PostMapping("/admin/updateLeave")
    public ResponseEntity<Map<String, Object>> updateLeave(@RequestParam("action") String action, @RequestParam int leaveId, @RequestParam String remarks){
     return leaveServices.updateLeaveApplication(action,leaveId,remarks);
    }
    @PostMapping("/it/raiseticket")
    public ResponseEntity<Map<String,String>> updateLeave(@RequestParam String issue,@RequestParam String description,@RequestParam String hostname,@RequestParam String priority,@RequestParam String anydesk) throws MessagingException, UnsupportedEncodingException {

        return itTicketService.raiseTicket(issue,description,priority,hostname,anydesk);
    }
    @GetMapping("/it/mytickets")
    public List<ItTicket> getMyTickets()  {

       return itTicketService.getMyTickets();
    }
    @GetMapping("/admin/stats")
    public StatsDto getStats(){
        return employeeService.getStats();
    }
    @GetMapping("/admin/managedepartments")
    public List<Department> getDepartments(){
        return departmentService.getDepartments();
    }

    @PostMapping("/admin/adddepartment")
    public ResponseEntity<Map<String,String>> addDept(@RequestParam String name,@RequestParam String shortName,@RequestParam  String code ){
        return departmentService.addDepartment(name,shortName,code);
    }


}
