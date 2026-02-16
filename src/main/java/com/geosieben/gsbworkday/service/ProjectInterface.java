package com.geosieben.gsbworkday.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.entity.*;

public interface ProjectInterface {
public ResponseEntity<Map<String,String>> logNewProject(ProjectRequest request);
public List<RootProject> getRootProjectByClient(int clientid);
public List<Project> getAllotmentsByRoot(int rootid);

}
