package com.geosieben.gsbworkday.service;


import com.geosieben.gsbworkday.dto.EmployeeRequestDto;
import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.dto.StatsDto;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface EmployeeServiceInterface {


    public ResponseEntity<Map<String,String>> addEmployee(EmployeeRequestDto dto);
    public List<EmployeeResponseDto> fetchEmployees();
    public StatsDto getStats();
}
