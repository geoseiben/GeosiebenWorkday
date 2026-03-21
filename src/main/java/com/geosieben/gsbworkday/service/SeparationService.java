package com.geosieben.gsbworkday.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geosieben.gsbworkday.dto.SeparationRequestsProjection;
import com.geosieben.gsbworkday.entity.AdminActivities;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Separation;
import com.geosieben.gsbworkday.entity.SeparationDocs;
import com.geosieben.gsbworkday.repository.*;


import jakarta.servlet.http.HttpSession;
@Service
public class SeparationService implements SeparationInterface {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private BasicInfoRepository basicInfoRepository;
    @Autowired
    private SeparationRepository separationRepository;
        @Autowired
    private SeperationDocsRepository seperationDocsRepository;
    @Autowired
    private AdminActivitiesRepository adminActivitiesRepository;

    @Override
    @Transactional
    public ResponseEntity<?> applyResignation(LocalDate lastWorkingDate, String reason, String remark,
            List<String> documents, EmployeeBasicInfo eid) {
            String eidd=(String) httpSession.getAttribute("eid");
            EmployeeBasicInfo appliedBy=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eidd);
            Separation separation=new Separation();
            separation.setLastWorkingDay(lastWorkingDate);
            separation.setEmployeeBasicInfo(eid);
            separation.setReasonforLeaving(reason);
            separation.setDescription(remark);
            separation.setAppliedBy(appliedBy);
            Separation savedDoc=separationRepository.save(separation);
            AdminActivities activity=new AdminActivities();
            activity.setCategory("Separation");
            activity.setTitle("Separation Initiated");
            String message=appliedBy.getFirstName()+" "+appliedBy.getLastName()+" - "+reason;
            activity.setMessage(message);
            adminActivitiesRepository.save(activity);
            for(String doc:documents){
                    SeparationDocs separationDocs=new SeparationDocs();
                    separationDocs.setDocument(doc);
                    separationDocs.setSeparation(savedDoc);
                    seperationDocsRepository.save(separationDocs);
            }

             return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Resignation processed for "
        ));
    }

    @Override
    public List<SeparationRequestsProjection> getSeparationRequests() {
        return separationRepository.getSeparationRequests();
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSeparationRequest(String action, int id, String remarks) {
        Map<String,String> response=new HashMap<>();
       Separation request=separationRepository.findById(id).orElse(null);
       if(!request.equals(null)){
        int status =action.equals("approve")?1:2;
        request.setStatus(status);
        request.setAdminRemarks(remarks);
        request.setApprovedOn(LocalDate.now());
        separationRepository.save(request);
        response.put("status", "success");
          response.put("message", "Request Updated");
       }
       else{
        response.put("status", "error");
          response.put("message", "separation request not found");
       }

       return ResponseEntity.ok(response);
    }

    @Override
    public Separation getSeparationRequest(int separationid) {
return separationRepository.findById(separationid).orElse(null);
    }
   

}
