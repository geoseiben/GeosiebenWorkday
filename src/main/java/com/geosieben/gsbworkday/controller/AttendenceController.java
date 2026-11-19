package com.geosieben.gsbworkday.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.geosieben.gsbworkday.addon.AddonServ;



@Controller
public class AttendenceController {

    @GetMapping("/admin/attendance")
    public String navigateAttendance() {
        return "attendance";
    }
   @GetMapping("/admin/masterReport")
   public String navMasterReport(Model model) {

    String month=AddonServ.monthYearFormatterNew(LocalDate.now());
    model.addAttribute("month", month);
       return "masterReport";
   }
    

}
