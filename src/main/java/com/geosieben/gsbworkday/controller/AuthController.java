package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.entity.User;
import com.geosieben.gsbworkday.service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserServices userServices;

    public AuthController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/login")
    public String loginpage(){
        return "login";
    }
    @GetMapping("/admin/addemployee")
    public String addemployee(Model model){
        return "addemployee";
    }
    @GetMapping("/admin/testinfo")
    public String testpage(){
        return "testinfo";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // shows register.html
    }
    @GetMapping("/leaves/apply")
    public String applyLeaves() {
        return "leaveapply"; // shows register.html
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String uname=authentication.getName();
        model.addAttribute("username", uname);
        System.out.println(authentication.getDetails());

        return "dashboard"; // shows register.html
    }
@GetMapping("/admin/activeEmployees")
public  String activeEmployees(Model model, HttpSession httpSession){
    String eid = (String) httpSession.getAttribute("eid");
    System.out.println(eid);
    return "activeemployees";
}

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logged out successfully");
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/admin")
    public String admin(Model model){
        return "admindashboard";
    }

@GetMapping("/admin/pendingLeaves")
    public String renderLeavesPending(){
        return "pendingleaves";
}
    @GetMapping("/it/raiseticket")
    public String raiseticket(){
        return "itticket";
    }
    @GetMapping("/admin/departments")
    public String departments(){
        return "managedepartments";
    }

    @GetMapping("/it/dashboard")
    public String getitdashboard() {
       return "itdashboard";
    }
    
}
