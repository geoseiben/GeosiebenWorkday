package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.dto.EmployeeDataProjection;
import com.geosieben.gsbworkday.dto.MyProjectProjection;
import com.geosieben.gsbworkday.service.EmployeeService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geosieben.gsbworkday.service.ProjectService;


@Controller 
public class UserController {

private final EmployeeService employeeService;
private final ProjectService projectService;

public UserController(ProjectService projectService, EmployeeService employeeService) {
    this.projectService = projectService;
    this.employeeService = employeeService;
}

@GetMapping("/myProjects")
public String navMyProject() {
    return  "myProjects";
}
@ResponseBody 
@GetMapping("/getMyProjects")
public List<MyProjectProjection> getMyProjects() {
    return projectService.myProjectProjectionsService();
}
@GetMapping("/viewProject")
public String navProject() {
    return "viewProject";
}


}
