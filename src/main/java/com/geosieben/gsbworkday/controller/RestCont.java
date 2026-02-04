package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.ProjectRequest;
import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.RootProject;
import com.geosieben.gsbworkday.service.CategoryService;
import com.geosieben.gsbworkday.service.ClientService;
import com.geosieben.gsbworkday.service.EmployeeService;
import com.geosieben.gsbworkday.service.ProjectService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;





@RestController
public class RestCont {
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProjectService projectService;
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
    
    
    
    
}
