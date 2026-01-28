package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.dto.LeaveBalanceReposnseDto;
import com.geosieben.gsbworkday.dto.LeaveRequestDto;
import com.geosieben.gsbworkday.dto.PendingLeaveResponseDto;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface LeaveInterface {
    public LeaveBalanceReposnseDto getLeaveBalance(String eid);

    public ResponseEntity<Map<String, String>> applyLeave(LeaveRequestDto dto) throws UnsupportedEncodingException, MessagingException;
    public List<PendingLeaveResponseDto> fetchPendingLeaves();
    public ResponseEntity<Map<String, Object>> updateLeaveApplication(String action, int leaveId, String remarks);


}