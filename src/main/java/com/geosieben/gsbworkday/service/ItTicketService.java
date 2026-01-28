package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.ItTicket;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.ItTicketRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItTicketService implements ItTicketServiceInterface {
    @Autowired
private ItTicketRepository itTicketRepository;
    @Value("${app.itmail}")
    private String itmail;
    @Value("${app.itname}")
    private String itname;
    @Autowired
    private BasicInfoRepository basicInfoRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private EmailService emailService;
    @Override
    public ResponseEntity<Map<String, String>> raiseTicket(String issue, String description, String priority, String hostname, String anydeskid) throws MessagingException, UnsupportedEncodingException {
        Map<String,String> response=new HashMap<>();
        String empid= (String) httpSession.getAttribute("eid");
        EmployeeBasicInfo emp=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(empid);
        EmployeeResponseDto empRes= (EmployeeResponseDto) basicInfoRepository.fetchEmployee(empid);
        ItTicket ticket=new ItTicket();
        ticket.setIssue(issue);
        ticket.setDescription(description);
        ticket.setHostname(hostname);
        ticket.setAnyDesk(anydeskid);
        ticket.setEmployeeBasicInfo(emp);
        ticket.setPriority(priority);
        itTicketRepository.save(ticket);
        emailService.raiseTicket(itname,itmail,empRes.getName(),issue,description,priority, LocalDate.now());
        response.put("status", "success");
        response.put("message", "Applied Leave Successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public List<ItTicket> getMyTickets() {
        String empid= (String) httpSession.getAttribute("eid");
     return   itTicketRepository.findByEmployeeBasicInfo_EID(empid);
    }
}
