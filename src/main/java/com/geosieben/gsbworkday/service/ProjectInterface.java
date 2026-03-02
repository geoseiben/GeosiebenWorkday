package com.geosieben.gsbworkday.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.AllotmentRequestDto;
import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.entity.*;

public interface ProjectInterface {
public ResponseEntity<Map<String,String>> logNewProject(ProjectRequest request);
public List<RootProject> getRootProjectByClient(int clientid);
public List<Project> getAllotmentsByRoot(int rootid);
public ResponseEntity<Map<String, String>> createProjectAllotment(AllotmentRequestDto request);
public Project getAllotmenInfo(int projectid);
public List<ProjectAllocation> getProjectAllocationData(int projectid);
public List<EmployeeBasicInfo> findEmployees();
public ResponseEntity<Map<String,String>> assignTask(int allotmentId,String prodAssignee,LocalDate deadline,String qcAssignee,LocalDate qcDeadline);
}
