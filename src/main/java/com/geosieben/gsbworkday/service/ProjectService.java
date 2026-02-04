package com.geosieben.gsbworkday.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Project;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.entity.ProjectCategories;
import com.geosieben.gsbworkday.entity.RootProject;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.ClientRepository;
import com.geosieben.gsbworkday.repository.ProjectRepository;
import com.geosieben.gsbworkday.repository.ProjectcategoryRepository;
import com.geosieben.gsbworkday.repository.RootProjectRepository;

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
              request.setProjectName(client.getClientId()+"_Pilots");
            }
                        RootProject rootProject=new RootProject();
                        rootProject.setCategory(projectCategories);
                        rootProject.setProjectName(request.getProjectName());
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
              request.setProjectName(client.getClientId()+"_Pilots");
            }
                            Project project=new Project();
                            EmployeeBasicInfo projectLead=(EmployeeBasicInfo)basicInfoRepository.findEmployeeBasicInfoByEID(request.getProjectLeadId());
                                                   String eid=(String) httpSession.getAttribute("eid");
                    EmployeeBasicInfo addedBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);  
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
}