package com.geosieben.gsbworkday.service;


import com.geosieben.gsbworkday.dto.LeaveBalanceReposnseDto;
import com.geosieben.gsbworkday.dto.LeaveRequestDto;
import com.geosieben.gsbworkday.dto.PendingLeaveResponseDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.LeaveBalance;
import com.geosieben.gsbworkday.entity.Leaves;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.LeaveBalanceRepository;
import com.geosieben.gsbworkday.repository.LeaveRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LeaveServices implements LeaveInterface{

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private BasicInfoRepository basicInfoRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private EmailService emailService;

    @Value("${app.hrname}")
    String toname;
    @Value("${app.hrmail}")
    String tomail;
    @Override
    public LeaveBalanceReposnseDto getLeaveBalance(String eid) {
       return leaveBalanceRepository.findLeaveBalanceByEid(eid);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, String>> applyLeave(LeaveRequestDto dto) throws UnsupportedEncodingException, MessagingException {
        String eid = (String) httpSession.getAttribute("eid");
        Map<String, String> response = new HashMap<>();

        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeBasicInfo_EID(eid);
        String leaveType = dto.getLeaveType();

        switch (leaveType) {
            case "CL" -> {
                if (leaveBalance.getCasualleaves() < dto.getDays()) {
                    response.put("status", "error");
                    response.put("message", "Insufficient Leave Balance");
                    return ResponseEntity.badRequest().body(response);
                }
                leaveBalance.setCasualleaves(leaveBalance.getCasualleaves() - dto.getDays());
            }
            case "EL" -> {
                if (leaveBalance.getEarnedleaves() < dto.getDays()) {
                    response.put("status", "error");
                    response.put("message", "Insufficient Leave Balance");
                    return ResponseEntity.badRequest().body(response);
                }
                leaveBalance.setEarnedleaves(leaveBalance.getEarnedleaves() - dto.getDays());
            }
            case "SL" -> {
                if (leaveBalance.getSickleaves() < dto.getDays()) {
                    response.put("status", "error");
                    response.put("message", "Insufficient Leave Balance");
                    return ResponseEntity.badRequest().body(response);
                }
                leaveBalance.setSickleaves(leaveBalance.getSickleaves() - dto.getDays());
            }
            case "RH" -> {
                if (leaveBalance.getRestrictedholidays() < dto.getDays()) {
                    response.put("status", "error");
                    response.put("message", "Insufficient Leave Balance");
                    return ResponseEntity.badRequest().body(response);
                }
                leaveBalance.setRestrictedholidays(leaveBalance.getRestrictedholidays() - dto.getDays());
            }
            default -> {
                response.put("status", "error");
                response.put("message", "Invalid leave type");
                return ResponseEntity.badRequest().body(response);
            }
        }

        EmployeeBasicInfo emp = basicInfoRepository.findEmployeeBasicInfoByEID(eid);

        Leaves leave = new Leaves();
        leave.setLeaveType(dto.getLeaveType());
        leave.setFromDate(dto.getFromDate());
        leave.setFromsession("1".equals(dto.getFromSession()) ? "Session 1" : "Session 2");
        leave.setToDate(dto.getToDate());
        leave.setToSession("1".equals(dto.getToSession()) ? "Session 1" : "Session 2");
        leave.setNoofDays(dto.getDays());
        leave.setDescription(dto.getComment());
        leave.setEmployeeBasicInfo(emp);
        leave.setAppliedby(emp);

        leaveRepository.save(leave);
        leaveBalanceRepository.save(leaveBalance);

        String empname=emp.getFirstName()+" "+emp.getLastName();
        String sub="Leave Application from "+empname +"[ "+emp.getEID()+"]";
emailService.leaveApplicationHR(tomail,toname,empname,emp.getEID(),
        dto.getFromDate(),dto.getToDate(),dto.getLeaveType(),dto.getComment(),
        LocalDate.now(),dto.getDays(),sub,String.valueOf(LocalDate.now().getYear()));
        response.put("status", "success");
        response.put("message", "Applied Leave Successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public List<PendingLeaveResponseDto> fetchPendingLeaves() {
       return leaveRepository.findPendingLeaves();
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateLeaveApplication(String action, int leaveid, String remarks) {
        Map<String, Object> response = new HashMap<>();

        Optional<Leaves> leaves = leaveRepository.findById(leaveid);
        if (leaves.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Application Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        switch (action) {
            case "approve" -> {
                String eid = (String) httpSession.getAttribute("eid");
                EmployeeBasicInfo emp = basicInfoRepository.findEmployeeBasicInfoByEID(eid);

                leaves.get().setStatus(1);
                leaves.get().setAdminRemark(remarks);
                leaves.get().setAdminRemarkDate(LocalDate.now());
                leaves.get().setApprovedby(emp);

                response.put("status", "success");
                response.put("message", "Leave approved");
            }
            case "reject" -> {
                String eid = (String) httpSession.getAttribute("eid");
                EmployeeBasicInfo emp = basicInfoRepository.findEmployeeBasicInfoByEID(eid);

                leaves.get().setStatus(2);
                leaves.get().setAdminRemark(remarks);
                leaves.get().setAdminRemarkDate(LocalDate.now());
                leaves.get().setApprovedby(emp);

                response.put("status", "success");
                response.put("message", "Leave rejected");
            }
            default -> {
                response.put("status", "error");
                response.put("message", "Invalid Action");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        // persist changes
        leaveRepository.save(leaves.get());

        return ResponseEntity.ok(response);
    }

}



