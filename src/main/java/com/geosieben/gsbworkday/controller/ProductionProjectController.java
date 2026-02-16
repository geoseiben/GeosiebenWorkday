package com.geosieben.gsbworkday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin/project")
@Controller
public class ProductionProjectController {
    @Autowired
    HttpSession httpSession;
    @GetMapping("/clients_stats")
    public String clientStats() {
        return "clientProjectStats";
    }
    @GetMapping("/projectsByClient")
    public String projectByClient(@RequestParam int cid)  {
      //  System.out.println(cid);
        return  "projectsByClient";
    }
    @GetMapping("/allotments")
    public String projectAllotments() {
        return "projectAllotments";
    }
    
    
}
