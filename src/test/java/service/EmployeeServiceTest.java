package service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.service.ClientService;

@ExtendWith(MockitoExtension.class) // Necessary to initialize @Mock
public class EmployeeServiceTest {

    @Mock
    private BasicInfoRepository basicInfoRepository;


    @Test
    public void testFetchAllUsers() {
        // Arrange: Define behavior for consecutive calls
        EmployeeBasicInfo mockEmployee = new EmployeeBasicInfo();
        mockEmployee.setEID("GSB-0900");
        mockEmployee.setFirstName("Pavan");
        
        when(basicInfoRepository.findEmployeeBasicInfoByEID("GSB-0900"))
            .thenReturn(mockEmployee) // First call
            .thenReturn(null);         // Second call

        EmployeeBasicInfo emp = basicInfoRepository.findEmployeeBasicInfoByEID("GSB-0900");
        assertNotNull(emp);
        assertEquals("Pavan", emp.getFirstName());
        assertNull(basicInfoRepository.findEmployeeBasicInfoByEID("GSB-0900"));
    }

@Test
public void fetchEmployeesTest() {
    // 1. Arrange: Prepare the mock data
    List<EmployeeBasicInfo> mockList = new ArrayList<>();
    
    EmployeeBasicInfo emp1 = new EmployeeBasicInfo();
    emp1.setEID("GSB-0001");
    
    EmployeeBasicInfo emp2 = new EmployeeBasicInfo();
    emp2.setEID("GSB-0002"); // Fixed the variable name here
    
    mockList.add(emp1);
    mockList.add(emp2);

    // Stub the repository
    when(basicInfoRepository.findAll()).thenReturn(mockList);

    List<EmployeeBasicInfo> result = basicInfoRepository.findAll();

    assertNotNull(result, "The result should not be null");
    assertEquals(2, result.size(), "The list size should be 2");
    assertEquals("GSB-0001", result.get(0).getEID());
}
}
