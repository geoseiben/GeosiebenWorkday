package com.geosieben.gsbworkday.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.WarningPrerequsites;
import com.geosieben.gsbworkday.entity.DocumentRequest;
import com.geosieben.gsbworkday.entity.HRDTickets;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.DocumentRequestRepository;
import com.geosieben.gsbworkday.repository.HRDRepository;
import com.geosieben.gsbworkday.repository.WarningsRepository;

import jakarta.servlet.http.HttpSession;

@Service 
public class HrService {
private final EmailService emailService;
private  final HRDRepository hrdRepository;
private final DocumentRequestRepository documentRequestRepository;
private final HttpSession httpSession;
private final BasicInfoRepository basicInfoRepository;
private final WarningsRepository warningsRepository;


public HrService(HRDRepository hrdRepository, DocumentRequestRepository documentRequestRepository,
		HttpSession httpSession, BasicInfoRepository basicInfoRepository, WarningsRepository warningsRepository, EmailService emailService) {
	this.hrdRepository = hrdRepository;
	this.documentRequestRepository = documentRequestRepository;
	this.httpSession = httpSession;
	this.basicInfoRepository = basicInfoRepository;
	this.warningsRepository = warningsRepository;
    this.emailService = emailService;
}
public List<HRDTickets> myHrdTicketsService(){
    return  hrdRepository.findHrdTicketsByRequester((String) httpSession.getAttribute("eid"));
}
public  List<DocumentRequest> myDocumentRequests(){
    return documentRequestRepository.findMyDocumentRequests((String) httpSession.getAttribute("eid"));
}
public  ResponseEntity<Map<String,String>> raiseHrdTicket(String subject,String priority,String remarks){
	Map<String,String> response=new HashMap<>();
	HRDTickets hrd=new HRDTickets();
	WarningPrerequsites pre=warningsRepository.getWarnigPrerequsites((String) httpSession.getAttribute("eid"));
	try{
		hrd.setSubject(subject);
		hrd.setDescription(remarks);
		hrd.setPriority(priority);
		hrd.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
		HRDTickets saved=hrdRepository.save(hrd);
		if(saved!=null){
			emailService.sendHrdEmployeeMail(pre.getEmployeeMail(), pre.getManagerName(),(String) httpSession.getAttribute("eid") ,saved.getId(), subject, remarks, AddonServ.fullDateFormatter(LocalDate.now()));

		}
		response.put("status", "success");
		response.put("message", "requested");
	}
catch(Exception e){
		response.put("status", "error");
		response.put("message", "failed to request");
}
	return null;
}
public  ResponseEntity<Map<String,String>> documentRequestService(String documentType,String remarks){
	Map<String,String> response=new HashMap<>();
DocumentRequest request=new DocumentRequest();
	WarningPrerequsites pre=warningsRepository.getWarnigPrerequsites((String) httpSession.getAttribute("eid"));
		try{
			request.setRequestedDocument(documentType);
			request.setReason(remarks);
			request.setRequester(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
	documentRequestRepository.save(request);
		response.put("status", "success");
		response.put("message", "Requested");
	}
catch(Exception e){
		response.put("status", "error");
		response.put("message", "failed to request");
}
return ResponseEntity.ok(response);
}

}
