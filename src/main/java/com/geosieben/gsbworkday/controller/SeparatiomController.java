package com.geosieben.gsbworkday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



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
