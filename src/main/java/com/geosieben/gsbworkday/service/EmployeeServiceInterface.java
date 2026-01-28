package com.geosieben.gsbworkday.service;


import com.geosieben.gsbworkday.dto.EmployeeRequestDto;
import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.dto.StatsDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.EmployeeDesignationInfo;
import com.geosieben.gsbworkday.entity.EmployeeJoiningInfo;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.JoiningInfoRepository;
import jakarta.persistence.JoinColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface EmployeeServiceInterface {


    public ResponseEntity<Map<String,String>> addEmployee(EmployeeRequestDto dto);
    public List<EmployeeResponseDto> fetchEmployees();
    public StatsDto getStats();
}
