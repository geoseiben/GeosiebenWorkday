package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.AllotmentRequestDto;
import com.geosieben.gsbworkday.dto.ClientWiseProjectResponseDto;
import com.geosieben.gsbworkday.entity.Project;
import com.geosieben.gsbworkday.entity.RootProject;
import com.geosieben.gsbworkday.service.ClientService;
import com.geosieben.gsbworkday.service.ProjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("admin/project")
@RestController
public class ProjectRestController {
@Autowired
private ClientService clientService; 
@Autowired
private ProjectService projectService;
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
public AllotmentRequestDto createAllotment(@ModelAttribute AllotmentRequestDto dto) {
    
    return dto;
}


}
