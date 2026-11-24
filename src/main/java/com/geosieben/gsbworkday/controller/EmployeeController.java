package com.geosieben.gsbworkday.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geosieben.gsbworkday.entity.DocumentRequest;
import com.geosieben.gsbworkday.entity.HRDTickets;
import com.geosieben.gsbworkday.service.HrService;


@Controller 
public class EmployeeController {
private final HrService hrService;


public EmployeeController(HrService hrService) {
	this.hrService = hrService;
}
@GetMapping("/hrQueries")
public String navHrQueries() {
    return "hrdDocs";
}
@ResponseBody 
@GetMapping("/getMyHrd")
public List<HRDTickets> getMyHrdTickets() {
    return hrService.myHrdTicketsService();
}
@ResponseBody 
@GetMapping("/getMyDocRequests")
public List<DocumentRequest> getMyDocRequests() {
    return hrService.myDocumentRequests();
}
@ResponseBody 
@PostMapping ("/raiseHRD")
public ResponseEntity<Map<String,String>> raiseHRD(
    @RequestParam("subject") String subject,
    @RequestParam ("priority") String priority,
     @RequestParam ("remarks") String remarks
) {
    return hrService.raiseHrdTicket(subject, priority, remarks);
}

@ResponseBody 
@PostMapping ("/requestDocument")
public ResponseEntity<Map<String,String>> requestDocument(
    @RequestParam("documentType") String documentType,
     @RequestParam ("remarks") String remarks
) {
    return hrService.documentRequestService(documentType, remarks);
}


}
