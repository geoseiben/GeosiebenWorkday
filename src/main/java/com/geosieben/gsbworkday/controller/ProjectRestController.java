package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.geosieben.gsbworkday.dto.AllotmentRequestDto;
import com.geosieben.gsbworkday.dto.BookeHoursProjection;
import com.geosieben.gsbworkday.dto.ClientWiseProjectResponseDto;
import com.geosieben.gsbworkday.dto.MonthlyHoursProjection;
import com.geosieben.gsbworkday.dto.ProjectHoursProjection;
import com.geosieben.gsbworkday.dto.UpdateAllotmentDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Project;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.entity.RootProject;
import com.geosieben.gsbworkday.repository.BookerHoursRepository;
import com.geosieben.gsbworkday.service.ClientService;
import com.geosieben.gsbworkday.service.ExcelService;
import com.geosieben.gsbworkday.service.ProjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("admin/project")
@RestController
public class ProjectRestController {

    private final BookerHoursRepository bookerHoursRepository;
@Autowired
private ClientService clientService; 
@Autowired
private ExcelService excelService;
@Autowired
private ProjectService projectService;

    ProjectRestController(BookerHoursRepository bookerHoursRepository) {
        this.bookerHoursRepository = bookerHoursRepository;
    }
@GetMapping("/projectStatsByclient")
public List<ClientWiseProjectResponseDto> projectStatsByclient() {
    return clientService.getPrejectStats();
}
@GetMapping("/projectsByClientDetailed")
public  List<RootProject> projectsByClient(@RequestParam int clientid) {
    return projectService.getRootProjectByClient(clientid);
}

@GetMapping("/getProjectAllotments")
public List<Project> getAllotments(@RequestParam int  projectId) {
return projectService.getAllotmentsByRoot(projectId);
}
@PostMapping("/createAllotment")
public ResponseEntity<Map<String,String>> createAllotment(@ModelAttribute AllotmentRequestDto dto) {
    
    return projectService.createProjectAllotment(dto);
}
@GetMapping("/getAllotmentInfo")
public Project  getAllotmentInfo(@RequestParam int projectid) {
   // System.out.println(projectid);
    return projectService.getAllotmenInfo(projectid);
}
    @PostMapping("/uploadallotments")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam int projectId) {
        System.out.println(projectId);
        try {
            excelService.saveExcelData(file,projectId);
            return ResponseEntity.ok("File uploaded and data saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed.");
        }
    }
    @GetMapping("/getAllocationData")
    public List<ProjectAllocation> getAllocationData(@RequestParam int  projectid) {
        return projectService.getProjectAllocationData(projectid);
    }
    @GetMapping("/getAssinee")
    public List<EmployeeBasicInfo> getAssinee() {
        return projectService.findEmployees();
    }
       @GetMapping("/getHoursByEmployee")
    public List<BookeHoursProjection> getEmployeeWiseBookedHours() {
        return bookerHoursRepository.getHoursByEmployeePivot();
    }
           @GetMapping("/getHoursByProject")
    public List<ProjectHoursProjection> getHoursByProject() {
        return bookerHoursRepository.getHoursByProjects();
    }
               @GetMapping("/getHoursByMonth")
    public List<MonthlyHoursProjection> getHoursByMonth() {
        return bookerHoursRepository.getHoursByMonthly();
    }
    @PostMapping("/assignTask")
    public ResponseEntity<Map<String,String>> assignTask(@RequestParam int allotmentid,@RequestParam String memberId,@RequestParam LocalDate deadline,@RequestParam String qcMemberId,@RequestParam LocalDate qcdeadline) {
        return projectService.assignTask(allotmentid, memberId, deadline, qcMemberId, qcdeadline);
    }
    
@PostMapping("/updateAllotment")
public ResponseEntity<Map<String, String>> updateAllotment(@RequestBody UpdateAllotmentDto dto) { 
    return projectService.updateAllotment(dto); 
}
    




}
