package com.geosieben.gsbworkday.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.ProjectRequest;

public interface ProjectInterface {
public ResponseEntity<Map<String,String>> logNewProject(ProjectRequest request);

}
