package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.BookedHoursRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.AllotmentRequestDto;
import com.geosieben.gsbworkday.dto.CompletedProjectInvoiceProjection;
import com.geosieben.gsbworkday.dto.MyProjectProjection;
import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.dto.UpdateAllotmentDto;
import com.geosieben.gsbworkday.entity.AdminActivities;
import com.geosieben.gsbworkday.entity.BookedHours;
import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Project;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.entity.ProjectCategories;
import com.geosieben.gsbworkday.entity.ProjectDelivery;
import com.geosieben.gsbworkday.entity.ProjectInvoicee;
import com.geosieben.gsbworkday.entity.RootProject;
import com.geosieben.gsbworkday.entity.TeamLeadTargetHours;
import com.geosieben.gsbworkday.repository.AdminActivitiesRepository;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.ClientRepository;
import com.geosieben.gsbworkday.repository.ProjectDeliveryRepository;
import com.geosieben.gsbworkday.repository.ProjectInvoiceRepository;
import com.geosieben.gsbworkday.repository.ProjectRepository;
import com.geosieben.gsbworkday.repository.ProjectcategoryRepository;
import com.geosieben.gsbworkday.repository.RootProjectRepository;
import com.geosieben.gsbworkday.repository.TlTargetHoursRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Service
public class ProjectService implements ProjectInterface{

private final ProjectDeliveryRepository projectDeliveryRepository;
private final BookedHoursRepository bookedHoursRepository;
private final ClientRepository clientRepository;
private final ProjectcategoryRepository projectcategoryRepository;
private final AdminActivitiesRepository adminActivitiesRepository;
private final BasicInfoRepository basicInfoRepository;
private final HttpSession httpSession;
private final RootProjectRepository rootProjectRepository;
private final ProjectRepository projectRepository;
private final AllocationRepository allocationRepository;
private final TlTargetHoursRepository tlTargetHoursRepository;
private final ProjectInvoiceRepository projectInvoiceRepository;




    public ProjectService(ProjectDeliveryRepository projectDeliveryRepository, BookedHoursRepository bookedHoursRepository,
    ClientRepository clientRepository, ProjectcategoryRepository projectcategoryRepository,
    AdminActivitiesRepository adminActivitiesRepository, BasicInfoRepository basicInfoRepository,
    HttpSession httpSession, RootProjectRepository rootProjectRepository, ProjectRepository projectRepository,
    AllocationRepository allocationRepository, TlTargetHoursRepository tlTargetHoursRepository,
    ProjectInvoiceRepository projectInvoiceRepository) {
  this.projectDeliveryRepository = projectDeliveryRepository;
  this.bookedHoursRepository = bookedHoursRepository;
  this.clientRepository = clientRepository;
  this.projectcategoryRepository = projectcategoryRepository;
  this.adminActivitiesRepository = adminActivitiesRepository;
  this.basicInfoRepository = basicInfoRepository;
  this.httpSession = httpSession;
  this.rootProjectRepository = rootProjectRepository;
  this.projectRepository = projectRepository;
  this.allocationRepository = allocationRepository;
  this.tlTargetHoursRepository = tlTargetHoursRepository;
  this.projectInvoiceRepository = projectInvoiceRepository;
}
    @Transactional
    public ResponseEntity<Map<String,String>> logNewProject(ProjectRequest request) {
      Map<String,String> response=new HashMap<>();
        Clients client=(Clients) clientRepository.findById(request.getClientId()).orElse(null);
        ProjectCategories projectCategories=(ProjectCategories) projectcategoryRepository.findById(request.getCategoryId()).orElse(null);
        String eid=(String) httpSession.getAttribute("eid");
        EmployeeBasicInfo addedBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);
        EmployeeBasicInfo projectLead=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(request.getProjectLeadId());
      if(request.getProjectType().equals("Pilot")){
        RootProject existingRoot=rootProjectRepository.findPilotProjectsByClientId(request.getClientId());
        if(existingRoot==null){
        RootProject root=addRootProject(request,projectCategories,client,projectLead,addedBy);
        Project project =addProject( request, root, client);
        addAllocation(request, projectLead, addedBy,project);
        response.put("status", "success");
         response.put("message", "Pilot Added Successfully");
        }else{
          Project existiProject=projectRepository.findProjectByRootid(existingRoot.getProjId());
          if(existiProject!=null){
    
        addAllocation(request, projectLead, addedBy,existiProject);
                response.put("status", "success");
         response.put("message", "Pilot Added Successfully");
          }else{
              Project project =addProject( request, existingRoot, client);
       addAllocation(request, projectLead, addedBy,project);
         response.put("status", "success");
         response.put("message", "Pilot Added Successfully");
          }
        }
      }
      else if(request.getProjectType().equals("Project")){
      addRootProject(request,projectCategories,client,projectLead,addedBy);
               response.put("status", "success");
         response.put("message", "Project Added Successfully");
      }
return ResponseEntity.ok(response);
        }
        public RootProject addRootProject(ProjectRequest request,ProjectCategories projectCategories,Clients client,EmployeeBasicInfo projectLead,EmployeeBasicInfo addedBy){

                        RootProject rootProject=new RootProject();

            if(request.getProjectType().equals("Pilot")){
             rootProject.setProjectName(client.getClientId()+"_Pilots");
            }else{
              rootProject.setProjectName(request.getProjectName());
            }
                        rootProject.setCategory(projectCategories);
                        rootProject.setClient(client);
                        rootProject.setProjectStatus(0);
                        rootProject.setProjectType(request.getProjectType());
                        rootProject.setCreatedOn(LocalDateTime.now());
                        rootProject.setCreatedBy(addedBy);
                        rootProject.setProjectlead(projectLead);
                       
        return rootProjectRepository.save(rootProject);
                        
                }


                public Project addProject(ProjectRequest request,RootProject rootProject,Clients client){
                    AdminActivities activity=new AdminActivities();
                       Project project=new Project();
                              activity.setCategory("Project");
                    if(request.getProjectType().equals("Pilot")){
                       activity.setTitle("New Pilot Logged");
              project.setAllotmentName(client.getClientId()+"_Pilots");
            }
            else {
                      activity.setTitle("New Project Logged");
            }
                 
                    EmployeeBasicInfo projectLead=(EmployeeBasicInfo)basicInfoRepository.findEmployeeBasicInfoByEID(request.getProjectLeadId());
                    String eid=(String) httpSession.getAttribute("eid");
                    EmployeeBasicInfo addedBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);  
                            project.setAllotmentName(request.getProjectName());
                            project.setRootProject(rootProject);
                            project.setTotalHours(request.getHours());
                            project.setProjectLead(projectLead);
                            project.setStartDate(request.getStartDate());
                            project.setEndDate(request.getDeadline());
                            project.setStatus(0);
                            project.setFilePath(request.getFilepath());
                            project.setCreatedBy(addedBy);     
                             String message=request.getProjectName()+" "+AddonServ.fullDateFormatter(request.getDeadline());
                            activity.setMessage(message);
                            adminActivitiesRepository.save(activity); 
                    return projectRepository.save(project);
                }
                public ProjectAllocation addAllocation(ProjectRequest request,EmployeeBasicInfo projectLead,EmployeeBasicInfo addedBy,Project project){

                  ProjectAllocation allocation=new ProjectAllocation();
                    allocation.setFeederAlloted(request.getProjectName());
                    allocation.setHrsAssigned(request.getHours());
                    allocation.setAllotmentOn(LocalDateTime.now());
                    allocation.setAllottedBy(addedBy);
                    allocation.setDeadline(request.getDeadline());
                    allocation.setAllottedTo(projectLead);
                    allocation.setStatus(1);
                    allocation.setQcAllotted(1);
                    allocation.setType(request.getProjectType());
                    allocation.setAllottedTo(projectLead);
                    allocation.setProject(project);
                  return allocationRepository.save(allocation);
                }
                @Override
                public List<RootProject> getRootProjectByClient(int clientid) {
                 return rootProjectRepository.findProjectsByClientId(clientid);

                }
                @Override
                public List<Project> getAllotmentsByRoot(int rootid) {
                    return projectRepository.findProjectsByRootProjectId(rootid);
                }
                @Override
                public ResponseEntity<Map<String, String>> createProjectAllotment(AllotmentRequestDto request) {
                    String eid=(String) httpSession.getAttribute("eid");
                  
                  EmployeeBasicInfo createdBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);
                  ProjectCategories category=(ProjectCategories) projectcategoryRepository.findById((int)request.getCategory()).orElse(null);
                  RootProject rootProject=(RootProject) rootProjectRepository.findById(request.getRootid()).orElse(null);
                  EmployeeBasicInfo projectLead=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID((String) request.getProjectLeadId());
                  Project project=new Project();

                  project.setAllotmentName(request.getAllotmentName());
                  project.setProjectLead(projectLead);
                  project.setCategory(category);
                  project.setCreatedBy(createdBy);
                  project.setTotalHours(request.getTotalHours());
                  project.setProductionHrs(request.getProductionHours());
                  project.setQaHrs(request.getQcHours());
                  project.setRootProject(rootProject);
                  project.setFilePath(request.getFilePath());
                  project.setStartDate(request.getStartDate());
                  project.setEndDate(request.getDeadline());
                  project.setIsHoursFixed(request.getIsHoursFixed());
                  project.setArea(request.getArea());
                  projectRepository.save(project);
                  LocalDate date=LocalDate.now();
                  TeamLeadTargetHours existingHours=tlTargetHoursRepository.findLeadTargetHoursForCurrentMonth(AddonServ.monthYearFormatter(date),Integer.parseInt(AddonServ.monthNumExtract(date)),Integer.parseInt(AddonServ.yearExtract(date)),request.getProjectLeadId());
                  if(existingHours!=null){
                      double hours=existingHours.getTargethours().doubleValue() + request.getTotalHours().doubleValue();
                      existingHours.setTargethours(BigDecimal.valueOf(hours));
                      existingHours.setUpdatedOn(LocalDateTime.now());

                  }
                  else{
                    TeamLeadTargetHours tltarget=new TeamLeadTargetHours();
                    tltarget.setTeamLead(projectLead);
                    tltarget.setMonth(AddonServ.monthYearFormatter(date));
                    tltarget.setMonthNum(Integer.parseInt(AddonServ.monthNumExtract(date)));
                    tltarget.setYear(Integer.parseInt(AddonServ.yearExtract(date)));
                    tltarget.setUpdatedOn(LocalDateTime.now());
                    tltarget.setTargethours(request.getTotalHours());
                    tlTargetHoursRepository.save(tltarget);
                  }
                            
                  Map<String,String> response=new HashMap<>();
                  response.put("status", "success");
                   response.put("message", "Allotment Created");
                   return ResponseEntity.ok(response);
                }
                @Override
                public Project getAllotmenInfo(int projectid) {
                return projectRepository.findById(projectid).orElse(null);
                }
                @Override
                public List<ProjectAllocation> getProjectAllocationData(int projectid) {
                  return allocationRepository.findByProjectPid(projectid);
                }
                @Override
                public List<EmployeeBasicInfo> findEmployees() {
              return basicInfoRepository.findAll();
                }
                public EmployeeBasicInfo getAssignee(String eid){
                 return basicInfoRepository.findEmployeeBasicInfoByEID(eid);
                }
                @Override
                public ResponseEntity<Map<String, String>> assignTask(int allotmentId, String prodAssignee,
                    LocalDate deadline, String qcAssignee, LocalDate qcDeadline) {
                      System.out.println(allotmentId+"\t"+prodAssignee+"\t"+deadline+"\t"+qcAssignee+"\t"+qcDeadline);
                      Map<String,String> response=new HashMap<>();
                      try{
                      ProjectAllocation allocation=(ProjectAllocation) allocationRepository.findById(allotmentId).orElse(null);
                      if(allocation!=null){
                        allocation.setAllottedTo(getAssignee(prodAssignee));
                        allocation.setDeadline(deadline);
                        allocation.setStatus(1);
                       if(qcAssignee!=null){
                        allocation.setQcAssinee(getAssignee(qcAssignee));
                        allocation.setQcDeadline(qcDeadline);
                        allocation.setQcAllotted(1);
                       }

                       allocationRepository.save(allocation);
                       response.put("status", "success");
                         response.put("message", "Task Assigned Successfully");
                      }
                      else{
                                               response.put("status", "error");
                         response.put("message", "Allotment Not Find");
                      }
                    }
                    catch(Exception e){
                        response.put("status","success");
                        response.put("message",e.getMessage());
                    }
                      return ResponseEntity.ok(response);
                }
             @Override
@Transactional
public ResponseEntity<Map<String, String>> updateAllotment(UpdateAllotmentDto dto) {
    Map<String, String> response = new HashMap<>();
    
    // 1. Safe retrieval using Optional
    ProjectAllocation allotment = allocationRepository.findById(dto.getAllotmentId())
            .orElse(null);

    if (allotment == null) {
        response.put("status", "error");
        response.put("message", "Task Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 2. Handle QC Task Synchronization
    // Added null check for getQcAssinee() to prevent NPE
    boolean isQcStatus = (allotment.getStatus() == 3 || allotment.getStatus() == 6);
    if (isQcStatus) {
      if(allotment.getQcAssinee() ==null && dto.getAssignedToId() !=null){
        ProjectAllocation qc=new ProjectAllocation();
        qc.setQcAssinee(basicInfoRepository.findEmployeeBasicInfoByEID(dto.getQcAssineeId()));
        qc.setQcDeadline(dto.getQcDeadline());
        qc.setStatus(4);
        qc.setQcAllotted(1);
        qc.setAllotmentOn(LocalDateTime.now());
        qc.setAllottedBy(allotment.getAllottedBy());
        qc.setCompletionDate(allotment.getCompletionDate());
        qc.setProject(allotment.getProject());
        qc.setFeederAlloted(allotment.getFeederAlloted());
        qc.setQcHrs(allotment.getQcHrs());
        qc.setType("QC");
        allocationRepository.save(qc);
      }
    }

    // 3. Handle Primary Assignment
    if (dto.getAssignedToId() != null && 
       (allotment.getAllottedTo() == null || !allotment.getAllottedTo().getEID().equals(dto.getAssignedToId()))) {
        
        EmployeeBasicInfo newMainEmp = (EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(dto.getAssignedToId());
        if (newMainEmp != null) {
            allotment.setAllottedTo(newMainEmp);
            if (allotment.getStatus() == 0) allotment.setStatus(1);
        }
    }

    // 4. Update General Info
    allotment.setDeadline(dto.getDeadline());
    allotment.setHrsAssigned(dto.getHrsAssigned());

    // No need for explicit .save() due to @Transactional dirty checking
    response.put("status", "success");
    response.put("message", "Updated successfully");
    return ResponseEntity.ok(response);
}

 public ResponseEntity<Map<String, String>> reOpenTask(int allotmentid){
      Map<String, String> response = new HashMap<>();

    ProjectAllocation allocation=allocationRepository.findById(allotmentid).orElse(null);
    if(allocation!=null){
      ProjectAllocation qcTask=allocationRepository.getQcTask(
        allocation.getFeederAlloted(),
        allocation.getProject().getPid(),
        allocation.getType(),
        allocation.getQcAssinee()!=null?allocation.getQcAssinee().getEID():null);
        if(qcTask!=null){
          deleteBookedHours(qcTask.getAllotmentid());
          allocationRepository.deleteById(qcTask.getAllotmentid());
        }
      deleteBookedHours(allocation.getAllotmentid());
      allocation.setStatus(1);
      allocation.setQcAllotted(0);
      allocation.setQcCompletionDate(null);
      allocation.setCompletionDate(null);
      allocation.setIsDelivered(0);
      allocationRepository.save(allocation);
      response.put("status", "error");
      response.put("message", "Task Reopened");
    }
    else{
    response.put("status", "error");
    response.put("message", "Task not Found");
    }
  return null;
 }
public void deleteBookedHours(int allotmentid){
   bookedHoursRepository.deleteHoursByAllotmentId(allotmentid);
}

public List<MyProjectProjection> myProjectProjectionsService(){
  return projectRepository.findActiveProjectsByLead((String) httpSession.getAttribute("eid"));
}
@Transactional
public ResponseEntity<Map<String,String>> markTaskDelivered(int allotmentId, String remarks) {
    String currentEmployeeId = (String) httpSession.getAttribute("eid");
    if (currentEmployeeId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "status", "error",
                        "message", "User session expired or employee ID not found."
                ));
    }

    ProjectAllocation allocation = allocationRepository.findById(allotmentId)
            .orElseThrow(() -> new EntityNotFoundException("Allocation not found for ID: " + allotmentId));

    int pid = allocation.getProject().getPid();
    String feeder = allocation.getFeederAlloted();

    Optional<ProjectDelivery> existingDelivery = projectDeliveryRepository.getDeliveryDataByPidFeeder(pid, feeder);
    if (existingDelivery.isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "status", "error",
                        "message", "Task for this project and feeder is already marked as delivered."
                ));
    }

    EmployeeBasicInfo deliveredBy = basicInfoRepository.findEmployeeBasicInfoByEID(currentEmployeeId);
    if (deliveredBy == null) {
        throw new IllegalStateException("Logged-in employee record not found: " + currentEmployeeId);
    }

    // 1. Save delivery record
    ProjectDelivery delTask = new ProjectDelivery();
    delTask.setDeliveredBy(deliveredBy);
    delTask.setDeliveryDate(LocalDateTime.now());
    delTask.setFeeder(feeder);
    delTask.setProject(allocation.getProject());
    delTask.setDeliveryRemarks(remarks);
    projectDeliveryRepository.save(delTask);

    // 2. Save booked hours BEFORE bulk @Modifying query clears the persistence context
    BookedHours hours = new BookedHours();
    hours.setCompletionPercentage(100);
    hours.setEmployeeBasicInfo(allocation.getProject().getProjectLead());
    hours.setDate(LocalDate.now());
    hours.setType("Delivery");
    hours.setProjectAllocation(allocation);
    bookedHoursRepository.save(hours);

    // 3. Bulk update allocations (rolls back steps 1 & 2 if qcHrs is null)
    int updatedRows = allocationRepository.deliveryTask(1, feeder, pid);
    if (updatedRows == 0) {
        throw new IllegalStateException("Cannot mark delivered: QC hours are missing for feeder " + feeder);
    }

    return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Task marked as delivered successfully."
    ));
}

public   List<CompletedProjectInvoiceProjection> getFinishedProjectsProjectionsService(){
  return  projectRepository.getFinishedProjectsProjections();
}


@Transactional
public ResponseEntity<Map<String, String>> updateProjectAndInvoice(
        Integer pid,
        Integer iid,
        String projectName,
        String projectLead,
        LocalDate startdate,
        LocalDate enddate,
        String invoiceNo,
        String workorder,
        Double clientHours,
        Double prodHours
) {
    Map<String, String> response = new HashMap<>();

    String eid = (String) httpSession.getAttribute("eid");
    if (eid == null) {
        response.put("status", "error");
        response.put("message", "Session expired. Please log in again.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    Project project = projectRepository.findById(pid).orElse(null);
    if (project == null) {
        response.put("status", "error");
        response.put("message", "Project not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    project.setAllotmentName(projectName);
    project.setStartDate(startdate);
    project.setEndDate(enddate);
    projectRepository.save(project);

    ProjectInvoicee invoice;
    if (iid != null) {
        invoice = projectInvoiceRepository.findById(iid).orElse(null);
        if (invoice == null) {
            response.put("status", "error");
            response.put("message", "Invoice not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    } else {
        invoice = new ProjectInvoicee();
        invoice.setProject(project); // Link new invoice to the parent project
    }

    invoice.setAddedBy(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
    invoice.setInvoiceNo(invoiceNo);
    invoice.setWorkOrder(workorder);
    invoice.setClientHours(clientHours != null ? clientHours : 0.0);
    invoice.setProdHours(prodHours != null ? prodHours : 0.0);
    projectInvoiceRepository.save(invoice);

    response.put("status", "success");
    response.put("message", "Project and invoice updated successfully.");
    return ResponseEntity.ok(response);
}
            
}