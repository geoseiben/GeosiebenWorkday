package com.geosieben.gsbworkday.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.AllotmentRequestDto;
import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Project;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.entity.ProjectCategories;
import com.geosieben.gsbworkday.entity.RootProject;
import com.geosieben.gsbworkday.entity.TeamLeadTargetHours;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.ClientRepository;
import com.geosieben.gsbworkday.repository.ProjectRepository;
import com.geosieben.gsbworkday.repository.ProjectcategoryRepository;
import com.geosieben.gsbworkday.repository.RootProjectRepository;
import com.geosieben.gsbworkday.repository.TlTargetHoursRepository;

import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpSession;
@Service
public class ProjectService implements ProjectInterface{
@Autowired
private ClientRepository clientRepository;
@Autowired 
private ProjectcategoryRepository projectcategoryRepository;
@Autowired
private BasicInfoRepository basicInfoRepository;
@Autowired
private HttpSession httpSession;
@Autowired 
private RootProjectRepository rootProjectRepository;
@Autowired
private ProjectRepository projectRepository;
@Autowired
private AllocationRepository allocationRepository;
@Autowired
private TlTargetHoursRepository tlTargetHoursRepository;
    @Transactional
    public ResponseEntity<Map<String,String>> logNewProject(ProjectRequest request) {
      Map<String,String> response=new HashMap<>();
        Clients client=(Clients) clientRepository.findById(request.getClientId()).orElse(null);
        ProjectCategories projectCategories=(ProjectCategories) projectcategoryRepository.findById(request.getCategoryId()).orElse(null);
        String eid=(String) httpSession.getAttribute("eid");
        EmployeeBasicInfo addedBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);
        EmployeeBasicInfo projectLead=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(request.getProjectLeadId());
        String pilotName=request.getProjectName();
      if(request.getProjectType().equals("Pilot")){
        RootProject existingRoot=rootProjectRepository.findPilotProjectsByClientId(request.getClientId());
        if(existingRoot==null){
        RootProject root=addRootProject(request,projectCategories,client,projectLead,addedBy);
        Project project =addProject( request, root, client);
        ProjectAllocation allocation=addAllocation(request, projectLead, addedBy,project);
        response.put("status", "success");
         response.put("message", "Pilot Added Successfully");
        }else{
          Project existiProject=projectRepository.findProjectByRootid(existingRoot.getProjId());
          if(existiProject!=null){
    
        ProjectAllocation allocation=addAllocation(request, projectLead, addedBy,existiProject);
                response.put("status", "success");
         response.put("message", "Pilot Added Successfully");
          }else{
              Project project =addProject( request, existingRoot, client);
        ProjectAllocation allocation=addAllocation(request, projectLead, addedBy,project);
         response.put("status", "success");
         response.put("message", "Pilot Added Successfully");
          }
        }
      }
      else if(request.getProjectType().equals("Project")){
       RootProject root=addRootProject(request,projectCategories,client,projectLead,addedBy);
               response.put("status", "success");
         response.put("message", "Project Added Successfully");
      }
return ResponseEntity.ok(response);
        }
        public RootProject addRootProject(ProjectRequest request,ProjectCategories projectCategories,Clients client,EmployeeBasicInfo projectLead,EmployeeBasicInfo addedBy){


            if(request.getProjectType().equals("Pilot")){
              request.setPilotName(client.getClientId()+"_Pilots");
            }
                        RootProject rootProject=new RootProject();
                        rootProject.setCategory(projectCategories);
                        rootProject.setProjectName(client.getClientId()+"_Pilots");
                        rootProject.setClient(client);
                        rootProject.setProjectStatus(0);
                        rootProject.setProjectType(request.getProjectType());
                        rootProject.setCreatedOn(LocalDateTime.now());
                        rootProject.setCreatedBy(addedBy);
                        rootProject.setProjectlead(projectLead);
                       
        return rootProjectRepository.save(rootProject);
                        
                }


                public Project addProject(ProjectRequest request,RootProject rootProject,Clients client){
                    if(request.getProjectType().equals("Pilot")){
              request.setPilotName(client.getClientId()+"_Pilots");
            }
                    Project project=new Project();
                    EmployeeBasicInfo projectLead=(EmployeeBasicInfo)basicInfoRepository.findEmployeeBasicInfoByEID(request.getProjectLeadId());
                    String eid=(String) httpSession.getAttribute("eid");
                    EmployeeBasicInfo addedBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);  
                    String pname=request.getPilotName()==null?request.getProjectName():request.getPilotName();
                            project.setAllotmentName(request.getProjectName());
                            project.setRootProject(rootProject);
                            project.setTotalHours(request.getHours());
                            project.setProjectLead(projectLead);
                            project.setStarDate(request.getStartDate());
                            project.setEndDate(request.getDeadline());
                            project.setStatus(0);
                            project.setFilePath(request.getFilepath());
                            project.setCreatedBy(addedBy);                            
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
                 Clients client=(Clients) clientRepository.findById(clientid).orElse(null);
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
                  project.setStarDate(request.getStartDate());
                  project.setEndDate(request.getDeadline());
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
                      Map<String,String> response=new HashMap<>();
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
                      return ResponseEntity.ok(response);
                }
                

                

                
}