package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.LeaveRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.CoRequestProjection;
import com.geosieben.gsbworkday.dto.CompOffHistoryProjection;
import com.geosieben.gsbworkday.dto.MyCompOffProjection;
import com.geosieben.gsbworkday.entity.CompensatoryOff;
import com.geosieben.gsbworkday.entity.CompensatoryOffRequests;
import com.geosieben.gsbworkday.entity.Leaves;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.CompensatoryOffRepository;
import com.geosieben.gsbworkday.repository.CompensatoryOffRequestsRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Service
public class CompOffService {
    private final LeaveRepository leaveRepository;
    private final CompensatoryOffRepository compensatoryOffRepository;
    private final HttpSession httpSession;
    private final BasicInfoRepository basicInfoRepository;
    private final CompensatoryOffRequestsRepository compensatoryOffRequestsRepository;



public CompOffService(LeaveRepository leaveRepository, CompensatoryOffRepository compensatoryOffRepository,
            HttpSession httpSession, BasicInfoRepository basicInfoRepository,
            CompensatoryOffRequestsRepository compensatoryOffRequestsRepository) {
        this.leaveRepository = leaveRepository;
        this.compensatoryOffRepository = compensatoryOffRepository;
        this.httpSession = httpSession;
        this.basicInfoRepository = basicInfoRepository;
        this.compensatoryOffRequestsRepository = compensatoryOffRequestsRepository;
    }

public ResponseEntity<Map<String,String>> requestCompOff(LocalDate workDate,String description){
    Map<String,String> response=new HashMap<>();
    String eid=(String) httpSession.getAttribute("eid");

    Optional<CompensatoryOff> existCompOff=compensatoryOffRepository.findExistingCompOff(workDate, eid);
    if(existCompOff.isPresent()){
        response.put("status", "error");
        response.put("message", "Comp-Off Exist for the Following Date");
        return ResponseEntity.ok(response);
    }
    else{
        Optional<CompensatoryOffRequests> pendingRequest =compensatoryOffRequestsRepository.findPendingRequest(workDate, eid);
        if(pendingRequest.isPresent()){
        response.put("status", "error");
        response.put("message", "Comp-Off Request Exist for the Following Date");
        return ResponseEntity.ok(response);
        }
        else{
            Optional<CompensatoryOff> pendingCompOff=compensatoryOffRepository.findPendingCompOffs(eid);
if(pendingCompOff.isEmpty()){
                CompensatoryOffRequests request=new CompensatoryOffRequests();
            request.setWorkedDate(workDate);
            request.setRequestType("Request");
            request.setRequester(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
            request.setRemarks(description);
            request.setStatus(0);
            compensatoryOffRequestsRepository.save(request);
        }
        else{
            CompensatoryOffRequests request=new CompensatoryOffRequests();
            request.setCompOffDate(pendingCompOff.get().getCompOffDate());
            request.setWorkedDate(workDate);
            request.setRequestType("Clear");
            request.setRequester(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
            request.setRemarks(description);
            request.setStatus(0);
            compensatoryOffRequestsRepository.save(request);

        }
        response.put("status", "success");
        response.put("message", "Compoff Requested");

    }
    }
   
return ResponseEntity.ok(response);
}

public  ResponseEntity<Map<String,String>> applyCompOff(LocalDate compOffDate,int id){
        Map<String,String> response=new HashMap<>();

    CompensatoryOff comp=compensatoryOffRepository.findById(id).orElse(null);
    if(comp!=null){
        String dateFromat=AddonServ.fullDateFormatter(compOffDate);
    String reason="Compensatory Off Request (Worked Date: "+ dateFromat +" )";
    Leaves leave=new Leaves();
    leave.setFromDate(compOffDate);
    leave.setToDate(compOffDate);
    leave.setFromsession("Session 1");
    leave.setToSession("Session 2");
    leave.setDescription(reason);
    leave.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
    leave.setAppliedby(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
    leave.setNoofDays(1);
    leave.setLeaveType("CO");
   Leaves applied= leaveRepository.save(leave);
    comp.setLeaves(applied);
    compensatoryOffRepository.save(comp);

    response.put("status", "success");
response.put("message", "Leave Requested");
    }
    else{
                    response.put("status", "error");
        response.put("message", "Comp-Off Not Found");
    }


    return ResponseEntity.ok(response);
}
public List<MyCompOffProjection> myCompOffs(){
    return compensatoryOffRepository.getMyCompOff((String) httpSession.getAttribute("eid"));

}

public List<CoRequestProjection> getMyCoRequests(){
    return compensatoryOffRequestsRepository.getMyCoRequests((String) httpSession.getAttribute("eid"));
}

public List<CoRequestProjection> getRequests(){
    return compensatoryOffRequestsRepository.getRequests();
}
@Transactional()
public  ResponseEntity<Map<String,String>> updateCoRequest(int id,String action,String remarks){
        Map<String,String> response=new HashMap<>();
        int status =action.equals("approveRequest")? 1:2;
        String finalRemarks=action.equals("approveRequest")? "Approved":remarks;
            String eid=(String) httpSession.getAttribute("eid");

        CompensatoryOffRequests request=compensatoryOffRequestsRepository.findById(id).orElse(null);
        if(request!=null){
            if(request.getRequestType().equals("Request") && status == 1){
                CompensatoryOff compoff=new CompensatoryOff();
                compoff.setApprovedBy(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
                compoff.setEmployeeBasicInfo(request.getRequester());
                compoff.setWorkedDate(request.getWorkedDate());

                compensatoryOffRepository.save(compoff);
            } 
            else if(request.getRequestType().equals("Clear") &&  status==1){
                CompensatoryOff compoff=request.getCompensatoryOff();
                compoff.setWorkedDate(request.getWorkedDate());
                compoff.setApprovedBy(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
                compensatoryOffRepository.save(compoff);
            }

            request.setStatus(status);
            request.setActionBy(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
            request.setAdminRemarks(finalRemarks);
            request.setUpdatedAt(LocalDateTime.now());
            compensatoryOffRequestsRepository.save(request);
                response.put("status", "success");
                response.put("message", "Request Updated");
        }
        else{
            response.put("status", "error");
                response.put("message", "Request not found");
        }

return ResponseEntity.ok(response);
}
@Transactional
public  ResponseEntity<Map<String,String>> addCompOff(String employeeId,LocalDate compOffDate,String adminRemarks){
            Map<String,String> response=new HashMap<>();

    String eid=(String) httpSession.getAttribute("eid");
    Leaves leave=new Leaves();
    leave.setAdminRemark("Approved");
    leave.setDescription(adminRemarks);
    leave.setAppliedby(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
    leave.setFromDate(compOffDate);
    leave.setFromsession("Session 1");
    leave.setToDate(compOffDate);
    leave.setToSession("Session 2");
    leave.setStatus(1);
    leave.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(employeeId));
    leave.setLeaveType("CO");
    leave.setNoofDays(1);
    Leaves res=leaveRepository.save(leave);

    CompensatoryOff compoff=new CompensatoryOff();
    compoff.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(employeeId));
    compoff.setApprovedBy(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
    compoff.setLeaves(res);
    compoff.setCompOffDate(compOffDate);
    compensatoryOffRepository.save(compoff);

        response.put("status", "success");
        response.put("message", "Compoff Added");
 
    return ResponseEntity.ok(response);
}

public List<CompOffHistoryProjection> getCompoffHistory(){
    return compensatoryOffRepository.getCompoffHistory();
}
@Transactional
public  ResponseEntity<Map<String,String>> applyCo(int id,LocalDate compoffdate){
        Map<String,String> response=new HashMap<>();
        try{

        CompensatoryOff comp=compensatoryOffRepository.findById(id).orElse(null);
            if(comp!=null){
                    String remarks="Compensatory Off Request (Worked Date: "+AddonServ.fullDateFormatter(comp.getWorkedDate()) +" )";
                    Leaves leave =new Leaves();
                    leave.setDescription(remarks);
                    leave.setAppliedby(comp.getEmployeeBasicInfo());
                    leave.setEmployeeBasicInfo(comp.getEmployeeBasicInfo());
                    leave.setFromDate(compoffdate);
                    leave.setToDate(compoffdate);
                    leave.setFromsession("Session 1");
                    leave.setToSession("Session 2");
                    leave.setLeaveType("CO");
                    leave.setNoofDays(1);
                    leaveRepository.save(leave);

                    comp.setCompOffDate(compoffdate);
                    compensatoryOffRepository.save(comp);
        response.put("status", "success");
        response.put("message", "Leave Appplied");
            }
            else{
                response.put("status", "error");
                response.put("message", "Comp-Off Not Found");
            }
        }
        catch(Exception e){
                    response.put("status", "error");
                response.put("message", e.getMessage());
            
        }
return ResponseEntity.ok(response);
}
}

