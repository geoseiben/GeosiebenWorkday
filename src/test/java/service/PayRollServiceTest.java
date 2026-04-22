package service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.geosieben.gsbworkday.entity.Salary;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
import com.geosieben.gsbworkday.repository.SalaryExtraRepository;
import com.geosieben.gsbworkday.repository.SalaryRepository;
import com.geosieben.gsbworkday.service.PayRollService;
@ExtendWith(MockitoExtension.class)
public class PayRollServiceTest {
    @Mock
    SalaryRepository salaryRepository;
        @Mock
SalaryExtraRepository salaryExtraRepository;
    @InjectMocks
    PayRollService payRollService;
    
@Test
    public void testgetPayRoll(){
    java.util.List<Salary> mockSalaries=new ArrayList<>();
    Salary s1=new Salary();
    s1.setSalaryid(1);
    s1.setMonth("Apr-2026");
        Salary s2=new Salary();
    s1.setSalaryid(2);
    s1.setMonth("Apr-2026");
    mockSalaries.add(s1);
     mockSalaries.add(s2);
     when(salaryRepository.findAll()).thenReturn(mockSalaries);
     java.util.List<Salary> resp=salaryRepository.findAll();
     assertTrue(resp instanceof ArrayList);
    }  

    @Test
    public void testgetExtraDetails(){
        java.util.List<SalaryExtraDetails> mockExtra=new ArrayList<>();
        SalaryExtraDetails s1=new SalaryExtraDetails();
        s1.setId(1);
        s1.setMonth("Apr-2026");
                SalaryExtraDetails s2=new SalaryExtraDetails();
        s2.setId(2);
        s1.setMonth("Apr-2026");
        mockExtra.add(s1);
        mockExtra.add(s2);
        when(salaryExtraRepository.findAll()).thenReturn(mockExtra);
        java.util.List<SalaryExtraDetails> extra=salaryExtraRepository.findAll();
             assertTrue(extra instanceof ArrayList);
            assertNotNull(extra); 
            }

}
