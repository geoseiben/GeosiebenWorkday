package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.HostsRepository;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;




@Controller
@RequestMapping("/it")
public class ItController {
    private final HostsRepository hostsRepository;
    private BasicInfoRepository basicInfoRepository;
    private HttpSession httpSession;
    ItController(HostsRepository hostsRepository,BasicInfoRepository basicInfoRepository,HttpSession httpSession) {
        this.hostsRepository = hostsRepository;
        this.basicInfoRepository=basicInfoRepository;
        this.httpSession=httpSession;
    }
    @GetMapping("/addAsset")
    public String navigateAddAsset() {
        return "addAsset";
    }
    @GetMapping("/itInventory")
    public String navigateItInventory() {
        return "itInventory";
    }
    @GetMapping("/addHost")
    public String addHost() {
        return "addHost";
    }
public String navigateCredManager() {
    return "credentialsManager";
}
@GetMapping("/manageHosts")
public String manageHosts(Model model) {
    
    EmployeeBasicInfo emp=basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid"));
    model.addAttribute("issuerFullName",emp.getFirstName()+" "+emp.getLastName());
    model.addAttribute("issuerEmployeeId",emp.getEID());
    model.addAttribute("currentYear",LocalDate.now().getYear());
    
    return "manageHosts";
}
@GetMapping("/manageSubscriptions")
public String manageSubscriptions() {
    return "subscriptions";
}
   

}
