package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.TestDesgn;
import com.geosieben.gsbworkday.entity.TestInfo;
import com.geosieben.gsbworkday.repository.TestDesgnRepo;
import com.geosieben.gsbworkday.repository.TestInfoRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {

    private final TestInfoRepo testInfoRepo;
    private final TestDesgnRepo testDesgnRepo;

    // Constructor Injection (Best Practice)
    public TestService(TestInfoRepo testInfoRepo, TestDesgnRepo testDesgnRepo) {
        this.testInfoRepo = testInfoRepo;
        this.testDesgnRepo = testDesgnRepo;
    }

    @Transactional
    public ResponseEntity<Map<String, String>> addinfo(TestInfo testInfo, TestDesgn testDesgn) {
        // 1. Save the parent first and capture the saved instance (with the new ID)
        TestInfo savedInfo = testInfoRepo.save(testInfo);

        // 2. LINK the child to the saved parent
        // This is the step that fixes the TransientPropertyValueException
        testDesgn.setTestInfo(savedInfo);

        // 3. Save the child
        testDesgnRepo.save(testDesgn);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}


