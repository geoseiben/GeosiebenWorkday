package com.geosieben.gsbworkday.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.entity.*;;

public interface SeparationInterface {
public ResponseEntity<?> applyResignation(LocalDate lastWorkingDate,String reason,String remark,List<String> documents,EmployeeBasicInfo appliedBy);
}
