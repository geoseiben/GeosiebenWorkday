package com.geosieben.gsbworkday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SeparatiomController {
@GetMapping("/admin/separtion/manage")
public String getSeparationData() {
    return "manageSeparations";
}
@GetMapping("/admin/separation/view")
public String getMethodName() {
    
    return "viewSeparationRequest";
}


}
