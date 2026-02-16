package com.geosieben.gsbworkday.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.geosieben.gsbworkday.dto.CandidateAssessmentDTO;
import com.geosieben.gsbworkday.entity.Candidate;
import com.geosieben.gsbworkday.repository.CandidiateRepository;
import com.geosieben.gsbworkday.service.CandidateService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/admin/careers")
@RestController
public class CareersRestController {
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CandidiateRepository candidiateRepository;
@PostMapping("/addCandidate")
    public ResponseEntity<Map<String,String>> addCandidate(
            @ModelAttribute Candidate candidate, 
            @RequestParam("resume") MultipartFile resume) throws IOException {

            return  candidateService.saveCandidate(candidate, resume);
            
    }
    @GetMapping("/getCandidates")
    public  List<CandidateAssessmentDTO>  getResponse() { 

return candidiateRepository.findAllCandidateAssessments();    }

@PostMapping("/updateprocess")
public ResponseEntity<Map<String, String>> update(@RequestBody Map<String, Object> data) {
     return candidateService.updateHiring(data);
}

    



}
 