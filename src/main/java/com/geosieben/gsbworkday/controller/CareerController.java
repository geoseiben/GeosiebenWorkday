package com.geosieben.gsbworkday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("/admin/careers")
@Controller
public class CareerController {
@GetMapping("/addCandidate")
public String navCandidatePage() {
    return "candidatePage";
}
@GetMapping("/manageCandidates")
public String manageCandidates() {
    return "managecandidates";
}


}
