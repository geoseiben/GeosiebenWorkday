package com.geosieben.gsbworkday.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geosieben.gsbworkday.dto.WarningsProjection;
import com.geosieben.gsbworkday.service.WarningService;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class WarningsController {
    private final WarningService warningService;
    
public WarningsController(WarningService warningService) {
        this.warningService = warningService;
    }

@ResponseBody 
@PostMapping("/admin/warnings/issueWarning")
public ResponseEntity<Map<String,Object>>  issueWarnings(
@RequestParam("action") String action,
@RequestParam(value = "break_hm",required = false) LocalTime break_hm,
@RequestParam(value="date",required = false) LocalDate date,
@RequestParam("eid") String eid,
@RequestParam(value = "logout",required = false) LocalTime logout,
@RequestParam(value = "login",required = false) LocalTime login,
@RequestParam(value = "workinghours_hm",required = false) LocalTime workinghours_hm,
@RequestParam(value = "remarks",required = false) String remarks,
@RequestParam(value = "type",required = false) String type

)throws Exception {

 System.out.println(action+"\t"+break_hm+"\t"+date+"\t"+eid+"\t"+login+"\t"+logout+"\t"+workinghours_hm+"\t"+remarks);
return warningService.issueWarningService(action,break_hm,date,eid,logout,login,workinghours_hm,remarks,type);
}
@GetMapping("/admin/warningHistory")
public String navWarningHistory() {
    return "warningsHistory";
}
@ResponseBody 
@GetMapping("/admin/getWarningHistory")
public List<WarningsProjection> getWarningHistory() {
    return  warningService.findAllWarningsService();
}

}
