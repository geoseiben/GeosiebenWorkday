package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.repository.ProjectRepository;
import com.geosieben.gsbworkday.service.LeaveServices;
import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.AllotmentsProjection;
import com.geosieben.gsbworkday.dto.AssignedTaskProjection;
import com.geosieben.gsbworkday.dto.EmployeeAttendanceProjection;
import com.geosieben.gsbworkday.dto.EmployeeProfileProjection;
import com.geosieben.gsbworkday.dto.EmployeeReportProjection;
import com.geosieben.gsbworkday.dto.EmployeeStatsProjection;
import com.geosieben.gsbworkday.dto.LeaveAvailedDto;
import com.geosieben.gsbworkday.dto.LeaveProjection;
import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.dto.ResourceDto;
import com.geosieben.gsbworkday.dto.SeparatedEmployeeProjection;
import com.geosieben.gsbworkday.entity.BookedHours;
import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.BookedHoursRepository;
import com.geosieben.gsbworkday.repository.CourseCompletionRepository;
import com.geosieben.gsbworkday.repository.SkillsRepository;
import com.geosieben.gsbworkday.service.CategoryService;
import com.geosieben.gsbworkday.service.ClientService;
import com.geosieben.gsbworkday.service.EmployeeService;
import com.geosieben.gsbworkday.service.ProjectService;
import com.geosieben.gsbworkday.service.SeparationService;
import com.geosieben.gsbworkday.service.TrackerService;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class RestCont {
    private final ProjectRepository projectRepository;
    private final LeaveServices leaveServices;
    private ClientService clientService;
    private EmployeeService employeeService;
    private CategoryService categoryService;
    private SeparationService separationService;
    private ProjectService projectService;
    private  AllocationRepository allocationRepository;
    private BasicInfoRepository basicInfoRepository;
    private HttpSession httpSession;
    private SkillsRepository skillsRepository;
    private CourseCompletionRepository courseCompletionRepository;
    
    public RestCont(LeaveServices leaveServices, ClientService clientService, EmployeeService employeeService,
            CategoryService categoryService, SeparationService separationService, ProjectService projectService,
            AllocationRepository allocationRepository, BasicInfoRepository basicInfoRepository, HttpSession httpSession,
            TrackerService trackerService, BookedHoursRepository bookerHoursRepository,SkillsRepository skillsRepository,
             ProjectRepository projectRepository,CourseCompletionRepository courseCompletionRepository) {
        this.leaveServices = leaveServices;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.separationService = separationService;
        this.projectService = projectService;
        this.allocationRepository = allocationRepository;
        this.basicInfoRepository = basicInfoRepository;
        this.httpSession = httpSession;
        this.trackerService = trackerService;
        this.bookerHoursRepository = bookerHoursRepository;
        this.skillsRepository=skillsRepository;
        this.projectRepository = projectRepository;
        this.courseCompletionRepository=courseCompletionRepository;
    }
    




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
   public List<AllotmentsProjection> myAllotments() {
    String eid=(String)httpSession.getAttribute("eid");
       return allocationRepository.getMyAllotments(eid);
   }
      @GetMapping("/getRunningAllotments")
   public Optional<ProjectAllocation> activeAllotments() {
    String eid=(String)httpSession.getAttribute("eid");
       return allocationRepository.getRunningTasks(eid);
   }



@Autowired
    private TrackerService trackerService;



@Autowired
private BookedHoursRepository bookerHoursRepository ;

@PostMapping("/updateTask")
public ResponseEntity<?> updateTask(@RequestParam("allotmentid") int allotmentid,@RequestParam("action") String action,@RequestParam("percentage") String percentage)  {
                String employeeId =(String) httpSession.getAttribute("eid");
        try {
            System.out.println("percentage is"+percentage);
            ProjectAllocation updatedTask = trackerService.handleAction(allotmentid, action, employeeId,percentage);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalStateException e) {
            // Catches validation errors like "Task already completed"
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
}


@GetMapping("/myProfileData")
public EmployeeProfileProjection myProfile() {
    return basicInfoRepository.employeeProfile((String)httpSession.getAttribute("eid"));
}
@PostMapping("/admin/cancelLeave")
public ResponseEntity<Map<String,String>> cancelLeaveds(@RequestParam("leaveId") int  id) {
return leaveServices.calcelLeave(id);
}

@GetMapping("/admin/resourceData")
public List<ResourceDto> getResourceData() {
    return employeeService.getResources();
}

@GetMapping("/getStats")
public EmployeeStatsProjection getstats() {
    return employeeService.getEmployeeStatsProjection();
}

@PostMapping("/admin/manualSeparation")
public ResponseEntity<Map<String,String>> manualSeparation(
    @RequestParam("Manualseparation")  String eid,
    @RequestParam("separationType")  String separationType,
    @RequestParam("lastDay")  LocalDate lastDay,
    @RequestParam("reason")  String reason,
    @RequestParam("employeeRemarks")  String employeeRemarks,
    @RequestParam("hrRemarks")  String hrRemarks,
    @RequestParam("rehireEligible")  int rehireEligible
) {
   return employeeService.processManualSeparation(eid, separationType, lastDay, reason, employeeRemarks, hrRemarks, rehireEligible);
}   
@GetMapping("/admin/getLeaveAvailedReport")
public List<LeaveAvailedDto> getLeaveAvailedReport() {  
    return employeeService.getLeaveAvailedDtos();
}
@GetMapping("/admin/getSeparated")
public List<SeparatedEmployeeProjection> getSeparated() {
    return employeeService.getSeparatedProjections();
}

@GetMapping("/admin/leaveCalenderData")
public List<LeaveProjection> leaveCalenderData() {
    return leaveServices.getLeaveProjectionsService();
}
@GetMapping("/getAttendance")
public List<EmployeeAttendanceProjection> getAttendance() {
    return employeeService.getEmployeeAttendanceService();
}

@GetMapping("/getMyTasks")
public List<AssignedTaskProjection> getMyTasks() {
    return employeeService.getAssignedTasksProjectionService();
}
@GetMapping("/admin/getEmployee/{eid}")
public EmployeeReportProjection getEmployeeById(@PathVariable String eid) {
    return employeeService.getEmployeeReportByEidService(eid);
}
}
