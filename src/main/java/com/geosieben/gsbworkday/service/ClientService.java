package com.geosieben.gsbworkday.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.ClientRepository;

import jakarta.servlet.http.HttpSession;
@Service
public class ClientService implements ClientInterFace{
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BasicInfoRepository basicInfoRepository;
    @Autowired
    private HttpSession httpSession;
    @Override
    public ResponseEntity<Map<String, String>> saveclientInfo(String clientid,String clientName) {
        Map<String, String> response=new HashMap<>();
       String eid=(String) httpSession.getAttribute("eid");
       EmployeeBasicInfo emp=(EmployeeBasicInfo) basicInfoRepository.findEmployeeBasicInfoByEID(eid);
       Clients client=new Clients();
       client.setClientId(clientid);
       client.setClientName(clientName);
       client.setCreatedOn(LocalDateTime.now());
       client.setEmployeeBasicInfo(emp);
       clientRepository.save(client);
       response.put("status", "success");
       response.put("message", "Client info Added");
       return ResponseEntity.ok(response);
    }
    @Override
    public List<Clients> getClients() {
     return clientRepository.findAll();
    }

}
