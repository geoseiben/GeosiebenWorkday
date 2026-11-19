package com.geosieben.gsbworkday.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.AttendanceResponseDto;

public interface AttendanceInterface {
    public List<AttendanceResponseDto> getAttendanceData(String date);
    public ResponseEntity<?> markAttendace(String eid,String status,String date);
    public ResponseEntity<?> markAttendanceForAll(String date);
}
