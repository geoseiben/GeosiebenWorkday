package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.dto.LeaveBalanceReposnseDto;
import com.geosieben.gsbworkday.dto.LeaveRequestDto;
import com.geosieben.gsbworkday.dto.PendingLeaveResponseDto;
import com.geosieben.gsbworkday.entity.*;
import com.geosieben.gsbworkday.repository.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class LeaveServices implements LeaveInterface {

    private LeaveBalanceRepository leaveBalanceRepository;
    private LeaveRepository leaveRepository;
    private BasicInfoRepository basicInfoRepository;
    private HttpSession httpSession;
    private EmailService emailService;
    private AdminActivitiesRepository adminActivitiesRepository;
    private AttendanceRepository attendanceRepository;

    public LeaveServices(LeaveBalanceRepository leaveBalanceRepository, LeaveRepository leaveRepository,
            BasicInfoRepository basicInfoRepository, HttpSession httpSession, EmailService emailService,
            AdminActivitiesRepository adminActivitiesRepository, AttendanceRepository attendanceRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveRepository = leaveRepository;
        this.basicInfoRepository = basicInfoRepository;
        this.httpSession = httpSession;
        this.emailService = emailService;
        this.adminActivitiesRepository = adminActivitiesRepository;
        this.attendanceRepository = attendanceRepository;
    }
    @Value("${app.hrname}")
    private String toname;
    @Value("${app.hrmail}")
    private String tomail;

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
        
        // Deduct balance immediately upon application
        boolean balanceUpdated = updateBalance(leaveBalance, dto.getLeaveType(), dto.getDays(), true);
        
        if (!balanceUpdated) {
            response.put("status", "error");
            response.put("message", "Insufficient Leave Balance");
            return ResponseEntity.badRequest().body(response);
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
        leave.setStatus(0); // Pending

        leaveRepository.save(leave);
        leaveBalanceRepository.save(leaveBalance);

        // Log Activity
        AdminActivities activity = new AdminActivities();
        activity.setCategory("Leaves");
        activity.setTitle("Leave Requested");
        activity.setMessage(emp.getFirstName() + " " + emp.getLastName() + " - " + dto.getLeaveType());
        adminActivitiesRepository.save(activity);

        // Send Email
        String empname = emp.getFirstName() + " " + emp.getLastName();
        String sub = "Leave Application from " + empname + " [" + emp.getEID() + "]";
        emailService.leaveApplicationHR(tomail, toname, empname, emp.getEID(),
                dto.getFromDate(), dto.getToDate(), dto.getLeaveType(), dto.getComment(),
                LocalDate.now(), dto.getDays(), sub, String.valueOf(LocalDate.now().getYear()));

        response.put("status", "success");
        response.put("message", "Applied Leave Successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, Object>> updateLeaveApplication(String action, int leaveid, String remarks) {
        Map<String, Object> response = new HashMap<>();
        Optional<Leaves> leaveOpt = leaveRepository.findById(leaveid);

        if (leaveOpt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Application Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Leaves leave = leaveOpt.get();
        String adminEid = (String) httpSession.getAttribute("eid");
        EmployeeBasicInfo admin = basicInfoRepository.findEmployeeBasicInfoByEID(adminEid);

        if ("approve".equalsIgnoreCase(action)) {
            leave.setStatus(1);
            mark(leave); // Update attendance records
            response.put("message", "Leave approved");
        } else if ("reject".equalsIgnoreCase(action)) {
            leave.setStatus(2);
            // Restore balance if rejected
            LeaveBalance lb = leaveBalanceRepository.findByEmployeeBasicInfo_EID(leave.getEmployeeBasicInfo().getEID());
            updateBalance(lb, leave.getLeaveType(), leave.getNoofDays(), false);
            leaveBalanceRepository.save(lb);
            response.put("message", "Leave rejected and balance restored");
        } else {
            response.put("status", "error");
            response.put("message", "Invalid Action");
            return ResponseEntity.badRequest().body(response);
        }

        leave.setAdminRemark(remarks);
        leave.setAdminRemarkDate(LocalDate.now());
        leave.setApprovedby(admin);
        leaveRepository.save(leave);

        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, String>> calcelLeave(int leaveid) {
        Map<String, String> response = new HashMap<>();
        Leaves leave = leaveRepository.findById(leaveid).orElse(null);

        if (leave != null) {
            LeaveBalance lb = leaveBalanceRepository.findByEmployeeBasicInfo_EID(leave.getEmployeeBasicInfo().getEID());
            updateBalance(lb, leave.getLeaveType(), leave.getNoofDays(), false);
            leave.setStatus(4); // Cancelled
            leaveRepository.save(leave);
            leaveBalanceRepository.save(lb);
            deleteAttendance(leave);
            response.put("status", "success");
            response.put("message", "Leave Cancelled Successfully");
        } else {
            response.put("status", "error");
            response.put("message", "Unable to find leave request");
        }
        return ResponseEntity.ok(response);
    }

    private boolean updateBalance(LeaveBalance lb, String type, double days, boolean isDeduct) {
        double factor = isDeduct ? -1 : 1;
        switch (type) {
            case "CL" -> {
                if (isDeduct && lb.getCasualleaves() < days) return false;
                lb.setCasualleaves(lb.getCasualleaves() + (days * factor));
            }
            case "EL" -> {
                if (isDeduct && lb.getEarnedleaves() < days) return false;
                lb.setEarnedleaves(lb.getEarnedleaves() + (days * factor));
            }
            case "RH" -> {
                if (isDeduct && lb.getRestrictedholidays() < days) return false;
                lb.setRestrictedholidays(lb.getRestrictedholidays() + (days * factor));
            }
            case "SL" -> {
                if (isDeduct && lb.getSickleaves() < days) return false;
                lb.setSickleaves(lb.getSickleaves() + (days * factor));
            }
            default -> { return false; }
        }
        return true;
    }

    @Override
    public List<PendingLeaveResponseDto> fetchPendingLeaves() {
        return leaveRepository.findPendingLeaves();
    }

    @Override
    public List<Leaves> approvedLeaves() {
        return leaveRepository.findApprovedLeaves();
    }

    public void mark(Leaves leave) {
        String eid = leave.getEmployeeBasicInfo().getEID();
        LocalDate start = leave.getFromDate();
        LocalDate end = leave.getToDate();

        // Handle single day
        if (start.equals(end)) {
            if (leave.getFromsession().equals(leave.getToSession())) {
                markAttendance(start.toString(), "Half Day", eid, leave.getFromsession());
            } else {
                markAttendance(start.toString(), "Full Day", eid, "Full");
            }
        } else {
            // datesUntil is exclusive of the end date, so we use plusDays(1)
            start.datesUntil(end.plusDays(1)).forEach(date -> {
                if (date.equals(start)) {
                    if (leave.getFromsession().equals("Session 2")) {
                        markAttendance(date.toString(), "Half Day", eid, "Session 2");
                    } else {
                        markAttendance(date.toString(), "Full Day", eid, "Full");
                    }
                } else if (date.equals(end)) {
                    if (leave.getToSession().equals("Session 1")) {
                        markAttendance(date.toString(), "Half Day", eid, "Session 1");
                    } else {
                        markAttendance(date.toString(), "Full Day", eid, "Full");
                    }
                } else {
                    markAttendance(date.toString(), "Full Day", eid, "Full");
                }
            });
        }
    }

    public void markAttendance(String date, String type, String eid, String sessionType) {
        String status = type.equals("Full Day") ? "Leave" : "Half Day Leave";
        double leaveValue = type.equals("Full Day") ? 1.0 : 0.5;
        
        Optional<Attendance> attendance = attendanceRepository.findExistingAttendanceRecord(date, eid);
        
        Attendance atd = attendance.orElse(new Attendance());
        if (attendance.isEmpty()) {
            atd.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
            atd.setDate(LocalDate.parse(date));
            atd.setWorkingDay(BigDecimal.ZERO);
        }
        
        atd.setStatus(status);
        atd.setLeaveDays(BigDecimal.valueOf(leaveValue));
        atd.setLeaveType(sessionType);
        atd.setUpdatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
        
        attendanceRepository.save(atd);
    }
public void deleteAttendance(Leaves leave){
    if(leave.getFromDate().equals(leave.getToDate())){
        attendanceRepository.deleteById(attendanceRepository.findExistingAttendanceRecord(String.valueOf(leave.getFromDate()),leave.getEmployeeBasicInfo().getEID()).get().getId());
    }

}
}