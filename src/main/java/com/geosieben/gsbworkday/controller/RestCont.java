package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.entity.BookedHours;
import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.BookerHoursRepository;
import com.geosieben.gsbworkday.service.CategoryService;
import com.geosieben.gsbworkday.service.ClientService;
import com.geosieben.gsbworkday.service.EmployeeService;
import com.geosieben.gsbworkday.service.ProjectService;
import com.geosieben.gsbworkday.service.SeparationService;
import com.geosieben.gsbworkday.service.TrackerService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class RestCont {
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SeparationService separationService;
    @Autowired
    private ProjectService projectService;
    @Autowired 
    AllocationRepository allocationRepository;
        @Autowired
    private BasicInfoRepository basicInfoRepository;
    @Autowired
    private HttpSession httpSession;
    @PostMapping("/admin/saveClient")
    public ResponseEntity<Map<String,String>> saveClient(@RequestParam String clientId,@RequestParam String clientName) {
  return clientService.saveclientInfo(clientId, clientName);
    }

    @GetMapping("/admin/activeclients")
    public List<Clients> getActiveClinetsString() {
        return clientService.getClients();
    }

    @GetMapping("/admin/getprojectrequired")
    public List<Object> getRequired() {
       return Arrays.asList(
        employeeService.fetchEmployees(),
        categoryService.getAllCategories(),
        clientService.getClients()
       );
    }

@PostMapping("/admin/addproject")
public ResponseEntity<Map<String,String>> addProject(@ModelAttribute ProjectRequest request) {
    return projectService.logNewProject(request); // and so on...
}
    
@PostMapping("/separtion/resign")
public ResponseEntity<?> processResignation(
            @RequestParam("lastworking") LocalDate lastWorkingDate,
            @RequestParam("reason") String reason,
            @RequestParam("remark") String remark,
            @RequestParam(value = "documents[]", required = false) List<String> documents) {
        String eid=(String) httpSession.getAttribute("eid");
        EmployeeBasicInfo appliedBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);
                    return separationService.applyResignation(lastWorkingDate,reason,remark,documents,appliedBy);
    }
    
   @GetMapping("/myAllotments")
   public List<ProjectAllocation> myAllotments() {
    String eid=(String)httpSession.getAttribute("eid");
       return allocationRepository.myAllotments(eid, eid);
   }
      @GetMapping("/getRunningAllotments")
   public Optional<ProjectAllocation> activeAllotments() {
    String eid=(String)httpSession.getAttribute("eid");
       return allocationRepository.getRunningTasks(eid);
   }
    @Autowired
    private AllocationRepository repository;


@Autowired
    private TrackerService trackerService;
@GetMapping("/{id}/{action}/{percentage}")
    public ResponseEntity<?> handleTaskAction(
            @PathVariable int id,
            @PathVariable String action,
             @PathVariable String percentage
        ) {
            
                String employeeId =(String) httpSession.getAttribute("eid");
        try {
            System.out.println("percentage is"+percentage);
            ProjectAllocation updatedTask = trackerService.handleAction(id, action, employeeId,percentage);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalStateException e) {
            // Catches validation errors like "Task already completed"
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }
@Autowired
private BookerHoursRepository bookerHoursRepository ;
    @GetMapping("/{empid}/{allotmentid}")
    public BookedHours getHours(@PathVariable("empid") String empid,@PathVariable("allotmentid") int allotmentid) {
        return bookerHoursRepository.findbydateAndAllotmentforToday(empid, allotmentid);
    }
    
}
