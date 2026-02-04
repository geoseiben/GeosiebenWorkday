package com.geosieben.gsbworkday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {
@GetMapping("/admin/clients")
public String getClients() {
    return "clients";
}

}
