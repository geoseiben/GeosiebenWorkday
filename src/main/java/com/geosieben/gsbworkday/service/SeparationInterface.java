package com.geosieben.gsbworkday.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.SeparationRequestsProjection;
import com.geosieben.gsbworkday.entity.*;

public interface SeparationInterface {
 ResponseEntity<?> applyResignation(LocalDate lastWorkingDate,String reason,String remark,List<String> documents,EmployeeBasicInfo appliedBy);
List<SeparationRequestsProjection> getSeparationRequests();
ResponseEntity<?> updateSeparationRequest(String action,int id,LocalDate lwd,String remarks);
Separation  getSeparationRequest(int separationid);
}
