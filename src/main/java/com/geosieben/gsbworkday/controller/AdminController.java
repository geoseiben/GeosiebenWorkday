package com.geosieben.gsbworkday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
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
}
