package service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.ClientWiseProjectResponseDto;
import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.ClientRepository;
import com.geosieben.gsbworkday.service.ClientService;

import jakarta.servlet.http.HttpSession;
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;
   @Mock
    private HttpSession httpSession;
    @Mock
    BasicInfoRepository basicInfoRepository;

@Test
    public void testSaveClientInfo_Success() {
        // 1. Arrange
        String mockEid = "GSB-0900";
        String clientId = "C123";
        String clientName = "Acme Corp";
        
        EmployeeBasicInfo mockEmp = new EmployeeBasicInfo();
        mockEmp.setEID(mockEid);

        // Stub session to return our EID
        when(httpSession.getAttribute("eid")).thenReturn(mockEid);
        
        // Stub repo to return our Employee
        when(basicInfoRepository.findEmployeeBasicInfoByEID(mockEid)).thenReturn(mockEmp);

        // 2. Act
        ResponseEntity<Map<String, String>> response = clientService.saveclientInfo(clientId, clientName);

        // 3. Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().get("status"));
        assertEquals("Client info Added", response.getBody().get("message"));

        // Verify that save was actually called on the client repository
        verify(clientRepository, times(1)).save(any(Clients.class));
    }
    @Test
    public void testGetClients(){
ArrayList<Clients> mockClients = new ArrayList<Clients>();
Clients c1=new Clients();  
c1.setClientId("hexa");  
Clients c2=new Clients();  
c1.setClientId("gcd");  
mockClients.add(c1);   
mockClients.add(c2);    
when(clientService.getClients()).thenReturn(mockClients);
assertNotNull(clientService.getClients());
}
@Test 
public void testGetPrejectStats(){
    ClientWiseProjectResponseDto dto=new ClientWiseProjectResponseDto(1,"hexa", "hexagon", 0, 1, 2,3);
    ArrayList<ClientWiseProjectResponseDto> respo=new ArrayList<>();
    when(clientService.getPrejectStats()).thenReturn(respo);
    assertNotNull(clientService.getPrejectStats());


}
}
