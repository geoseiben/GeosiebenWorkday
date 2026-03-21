package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.SeparationRequestsProjection;
import com.geosieben.gsbworkday.entity.Separation;
import com.geosieben.gsbworkday.service.SeparationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class SeparationRestController {
    @Autowired
    private SeparationService separationService;
@GetMapping("/admin/separtion/getRequests")
public List<SeparationRequestsProjection> getRequests() {
    return separationService.getSeparationRequests();
}

@PostMapping("/admin/separtion/updateStatus")
public ResponseEntity<?> updateSeparationRequest(@RequestParam String action,@RequestParam int id,@RequestParam String remarks ) {
    System.out.println(action+" "+id+" "+remarks);
return separationService.updateSeparationRequest(action, id, remarks);
}
@GetMapping("/admin/separation/view/{separationid}")
public Separation getSeparationRequest(@PathVariable int separationid) {
    
    return separationService.getSeparationRequest(separationid);
}
}
