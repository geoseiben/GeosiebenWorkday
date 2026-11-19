package com.geosieben.gsbworkday.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geosieben.gsbworkday.dto.CoRequestProjection;
import com.geosieben.gsbworkday.dto.CompOffHistoryProjection;
import com.geosieben.gsbworkday.dto.MyCompOffProjection;

import com.geosieben.gsbworkday.service.CompOffService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CompensatoryOffController {
@Autowired
private  CompOffService compOffService;

@GetMapping("/myCompOffs")
public String navGetCompOff() {
    return "myCompoff";
}
@ResponseBody
@PostMapping("/requestCompoff")
public ResponseEntity<Map<String,String>> requestCompoff(@RequestParam("workedDate") LocalDate workedDate,@RequestParam("description") String description) {
return compOffService.requestCompOff(workedDate, description);
}

@ResponseBody
@GetMapping("/getCompOff")
public List<MyCompOffProjection> getMyCompOff() {
    return compOffService.myCompOffs();
}
@ResponseBody
@GetMapping("/getMyCoRequests")
public List<CoRequestProjection> getMyCoRequests() {
    return compOffService.getMyCoRequests();
}
@GetMapping("/admin/compOffRequests")
public String compOffRequests() {
    return "compOffRequests";
}
@ResponseBody
@GetMapping("/admin/getCoRequests")
public List<CoRequestProjection> getRequests() {
    return compOffService.getRequests();
}
@ResponseBody
@PostMapping("/admin/updateCoRequest")
public ResponseEntity<Map<String,String>> updateCoRequest(@RequestParam("id") int id,@RequestParam("action") String action,@RequestParam(value = "rejectionReason",required = false) String rejectionReason) {
System.out.println(id+"\t"+action+"\t"+rejectionReason);
return compOffService.updateCoRequest(id, action, rejectionReason);
}
@ResponseBody
@GetMapping("/admin/getCoHistory")
public List<CompOffHistoryProjection> getMethodName() {
    return compOffService.getCompoffHistory();
}
@PostMapping("/admin/addCompOff")
public ResponseEntity<Map<String, String>> addCo(@RequestParam("employeeId") String employeeId,@RequestParam("compOffDate") LocalDate compOffDate,@RequestParam("adminRemark") String adminRemark) {
      return compOffService.addCompOff(employeeId, compOffDate, adminRemark);
}

@GetMapping("/admin/CompOff")
public String navCompOff() {
   return "compensatoryOff";
}

@PostMapping("/applyCompoff")
public ResponseEntity<Map<String, String>> applyCompoff(
    @RequestParam("id") int id,
    @RequestParam("compOffDate") LocalDate compOffDate,
    @RequestParam("remarks") String remarks) {    
    return compOffService.applyCo(id, compOffDate);
}


}
