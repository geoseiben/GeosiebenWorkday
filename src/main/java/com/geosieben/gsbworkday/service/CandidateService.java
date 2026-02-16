package com.geosieben.gsbworkday.service;

import java.io.IOException;
import java.nio.file.Files; // Added missing import
import java.nio.file.Path;  // Corrected import (was jakarta.persistence.criteria.Path)
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import com.geosieben.gsbworkday.entity.*;
import com.geosieben.gsbworkday.repository.AssesmentRepository;
import com.geosieben.gsbworkday.repository.CandidiateRepository;

@Service
public class CandidateService {

    @Value("${file.upload-dir-resumes}")
    private String uploadDir;

    @Autowired
    private CandidiateRepository repository;
        @Autowired
    private AssesmentRepository assesmentRepository;

    public ResponseEntity<Map<String,String>> saveCandidate(Candidate candidate, MultipartFile resume) throws IOException {
        // 1. Create directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
Map<String,String> response=new HashMap<>();
        // 2. Generate unique filename to avoid overwriting
        String fileName = System.currentTimeMillis() + "_" + resume.getOriginalFilename();
        
        // Use the NIO Path resolve method
        Path filePath = uploadPath.resolve(fileName);

        // 3. Save file to filesystem
        Files.copy(resume.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 4. Save metadata to DB (Storing the relative or absolute path)
        candidate.setResumeFilepath(filePath.toString());
        repository.save(candidate);
                       response.put("status", "success");
                    response.put("message", "Candidate Data Saved");
                    return ResponseEntity.ok(response);
    }
  @Transactional
public ResponseEntity<Map<String, String>> updateHiring(@RequestBody Map<String, Object> data) {
    Map<String, String> response = new HashMap<>();
      Integer cid = (Integer) data.get("cid");
    Integer id = (Integer) data.get("id");
    String task = (String) data.get("task");
    String type = (String) data.get("type");

    Candidate candidate = repository.findById(cid).orElse(null);
    if (candidate == null) {
        response.put("status", "error");
        response.put("message", "Candidate not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Attempt to find existing assessment or create a new one if it's the first step
    Assessment assessment = assesmentRepository.findById(id).orElse(null);
    
    if (assessment.getCandidate() == null) {
        assessment.setCandidate(candidate);
    }

    try {
        if ("schedule".equals(task)) {
            LocalDateTime scheduledDate = LocalDateTime.parse((String) data.get("date"));
            
            if ("Aptitude".equals(type)) {
                assessment.setAptitudeScheduledOn(scheduledDate);
                assessment.setStatus(0); 
            } else if ("Technical".equals(type)) {
                assessment.setTechnicalScheduledOn(scheduledDate);
                assessment.setStatus(2);
            }
            else if("documentation".equals(type)){
                assessment.setDocumentationScheduledOn(scheduledDate);
                assessment.setStatus(4);
            }
            else if("onboardschedule".equals(type)){
                assessment.setOnboardScheduledOn(scheduledDate);
                assessment.setStatus(6);
            }
        } 
        else if ("score".equals(task)) {
            Double score = Double.valueOf(data.get("score").toString());
            
            if ("Aptitude".equals(type)) {
                assessment.setAptitudeScore(score);
                assessment.setStatus(1);
            } else if ("Technical".equals(type)) {
                assessment.setTechnicalScore(score);
                assessment.setStatus(3);
            }
        }
        else if("finish".equals(task)){
                if ("finishdocs".equals(type)) {
            
                assessment.setStatus(5);
            } else if ("onboardfinish".equals(type)) {
                assessment.setStatus(7);
            }
        }

        assesmentRepository.save(assessment);
        response.put("status", "success");
        response.put("message", "Updated Successfully");

    } catch (Exception e) {
        response.put("status", "error");
        response.put("message", "Processing failed: " + e.getMessage());
    }

    return ResponseEntity.ok(response);
}

    public List<Candidate> getCandidates(){
        return repository.findAll();
    }
}