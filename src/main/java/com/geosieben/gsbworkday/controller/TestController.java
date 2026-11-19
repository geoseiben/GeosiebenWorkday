package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.dto.*;
import com.geosieben.gsbworkday.entity.*;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.service.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    private final BasicInfoRepository basicInfoRepository;
    private HttpSession httpSession;
    private EmployeeService employeeService;
    private LeaveServices leaveServices;
    private ItTicketService itTicketService;
    private DepartmentService departmentService;
    private ExcelService excelService;
    private PayRollService payRollService;



    public TestController(BasicInfoRepository basicInfoRepository,  HttpSession httpSession,
        EmployeeService employeeService, LeaveServices leaveServices, ItTicketService itTicketService,
        DepartmentService departmentService, ExcelService excelService,PayRollService payRollService) {
    this.basicInfoRepository = basicInfoRepository;
    this.httpSession = httpSession;
    this.employeeService = employeeService;
    this.leaveServices = leaveServices;
    this.itTicketService = itTicketService;
    this.departmentService = departmentService;
    this.excelService = excelService;
    this.payRollService =payRollService;
}
@PostMapping("/admin/addemployeee")
    public ResponseEntity<Map<String, String>> addEmployee(
            @ModelAttribute EmployeeRequestDto dto,@RequestParam("image") MultipartFile file) {
                 String path=null;
                if(!file.isEmpty()){
                 path=excelService.uploadImage(file);
                }
                System.out.println(path);
                        return employeeService.addEmployee(dto, path);
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
    @GetMapping("/it/activetickets")
    public List<ActiveTickets> getActiveTickets() {
               
        return itTicketService.fetchActiveTickets();
    }
    @PostMapping("/it/update")
    public ResponseEntity<Map<String,String>> fetchupdateTicketcket(@RequestParam Integer ticketid,@RequestParam Integer status,@RequestParam String  remarks) throws UnsupportedEncodingException, MessagingException {
        return itTicketService.updateTicket(ticketid, status, remarks);
    }
@GetMapping("/getPayRollEmployee")
public List<EmployeeBasicInfo> getPayRollEmployee() {
    return basicInfoRepository.payRollEmployee();
}
@GetMapping("/admin/getApprovedLeaves")
public  List<Leaves> approvedLeaves() {
   return leaveServices.approvedLeaves();
}

@GetMapping("/getEncSalaryy")
public List<SalaryResponseDecrypted> getEncSalaryy() throws Exception {

return payRollService.getSalaryDecrypted();
}

@GetMapping("/getManageData")
public List<ManageEmployeeResponseDto> getManageData() {
    return basicInfoRepository.getManageEmployeeList();
}
@PostMapping("/admin/issueId")
public ResponseEntity<Map<String,String>> issueId(@RequestParam("eid") String eid) {
    return employeeService.issueIdCard(eid);
}
@GetMapping("/getAvailableUsers")
public List<AvailableUsers> getAvailableUsers() {
    return employeeService.getAvailableUsers((String) httpSession.getAttribute("eid"));
}
}
