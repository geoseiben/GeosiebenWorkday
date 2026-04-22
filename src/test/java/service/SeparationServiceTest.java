package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import com.geosieben.gsbworkday.dto.SeparationRequestsProjection;
import com.geosieben.gsbworkday.entity.AdminActivities;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Separation;
import com.geosieben.gsbworkday.entity.SeparationDocs;
import com.geosieben.gsbworkday.repository.AdminActivitiesRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.SeparationRepository;
import com.geosieben.gsbworkday.repository.SeperationDocsRepository;
import com.geosieben.gsbworkday.service.SeparationService;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class SeparationServiceTest {

    @Mock
    private SeparationRepository separationRepository;
    
    @Mock 
    private BasicInfoRepository basicInfoRepository;
    
    @Mock 
    private HttpSession httpSession;
    
    @Mock  
    private AdminActivitiesRepository adminActivitiesRepository;
    
    @Mock
    private SeperationDocsRepository seperationDocsRepository;

    @InjectMocks
    private SeparationService separationService; // Change to your Impl class

    @Test
    public void testApplyResignation() {
        // Arrange
        EmployeeBasicInfo mockEmp = new EmployeeBasicInfo();
        mockEmp.setEID("GSB-0900");
        mockEmp.setFirstName("Pavan");
        mockEmp.setLastName("CA");

    java.util.List<String> list=new ArrayList<>();
        list.add("exp");
        list.add("rel");
        list.add("payslips");

        String mockEid = "GSB-0900";
        Separation mockSeparation = new Separation();
        
        when(httpSession.getAttribute("eid")).thenReturn(mockEid);
        when(basicInfoRepository.findEmployeeBasicInfoByEID(mockEid)).thenReturn(mockEmp);
        when(separationRepository.save(any(Separation.class))).thenReturn(mockSeparation);

        // Act
        ResponseEntity<?> response = separationService.applyResignation(
            LocalDate.now(), "test Reason", "separation", list, mockEmp
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(separationRepository, times(1)).save(any(Separation.class));
        verify(seperationDocsRepository, times(list.size())).save(any(SeparationDocs.class));
        verify(adminActivitiesRepository, times(1)).save(any(AdminActivities.class));
    }
    @Test
    public void testUpdateSeparationRequest_success(){
        Separation mockSeparation=new Separation();
        mockSeparation.setSeparationid(1);
        mockSeparation.setStatus(0);
        when(separationRepository.findById(1)).thenReturn(Optional.of(mockSeparation)); // CORRECT
        ResponseEntity<?>reponse= separationService.updateSeparationRequest("approve", 1, LocalDate.now(),"test");
        assertEquals(HttpStatus.OK, reponse.getStatusCode());

    }
        @Test
    public void testUpdateSeparationRequest_fail(){
        Separation mockSeparation=new Separation();
        mockSeparation.setSeparationid(1);
        mockSeparation.setStatus(0);
        when(separationRepository.findById(1)).thenReturn(Optional.empty()); // CORRECT
        ResponseEntity<?> reponse= separationService.updateSeparationRequest("approve", 1, LocalDate.now(),"test");
        assertEquals(HttpStatus.OK, reponse.getStatusCode());
        System.out.println(reponse.getBody());
      // 1. Get the body
Map<String, Object> body = (Map<String, Object>) reponse.getBody();

// 2. Assert the specific key
assertEquals(HttpStatus.OK, reponse.getStatusCode());
assertEquals("error", body.get("status"));

    }

}