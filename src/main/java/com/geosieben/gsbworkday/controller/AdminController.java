package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.repository.DocuRequestProjection;
import com.geosieben.gsbworkday.service.EmployeeService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {
private final EmployeeService employeeService;
    AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

@GetMapping("/admin/clients")
public String getClients() {
    return "clients";
}

@GetMapping("/separtion/resign")
public String navigateSeparation() {
    return "resign";
}
@GetMapping("/myprofile")
public String navigateMyprofile() {
    return "myprofile";
}
@GetMapping("/test")
public String test() {
    return "test";
}
@GetMapping("/admin/approvedLeaves")
public String getApprovedLeaves() {
    return "approvedLeaves";
}
@GetMapping("/admin/resourceManager")
public String navRmanager() {
    return "resourceManager";
}
@GetMapping("/testt")
public String navigateTest(){
    return "test";
}
@GetMapping("/admin/dashboard")
public String navAdmin() {
    return "admindashboard";
}
@GetMapping("/admin/leavesAvailedReport")
public String navLeaveAvaied() {
    return "leaveAvailedReport";
}
@GetMapping("/admin/hrDashboard")
public String getHrDashboard() {
    return "hrDashboard";
}

@GetMapping("/it/subscriptions")
public String navSubsriptions() {
    return "subscriptions";
}
@GetMapping("/requestDocuments")
public String navRequestDoc() {
    return "requestDocuments";
}
@PostMapping("/requestDoc")
public ResponseEntity<?> requestDocument(
    @RequestParam("documentType") String documentType,
    @RequestParam("remarks") String remarks
) {

    return ResponseEntity.ok(employeeService.documentRequestService(documentType, remarks));
}
@GetMapping("/getDocRequests")
public List<DocuRequestProjection> getDocRequests() {
    return employeeService.getDocumentProjections();
}
@GetMapping("/admin/reports/targetHours")
public String navTargetHours() {
    return"targetHoursReport";
}

@GetMapping("/admin/reports/allotmentReport")
public String navAllotmentReport() {
    return"allotmentReport";
}


}
