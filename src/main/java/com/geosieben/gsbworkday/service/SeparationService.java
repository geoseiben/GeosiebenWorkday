package com.geosieben.gsbworkday.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

           for(String doc:documents){
            SeparationDocs separationDocs=new SeparationDocs();
            separationDocs.setDocument(doc);
            separationDocs.setSeparation(savedDoc);
            seperationDocsRepository.save(separationDocs);
           }

             return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Resignation processed for " + documents
        ));
    }

}
