package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.EmployeeTargetHoursRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.entity.EmployeeBankInfo;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.EmployeeDesignationInfo;
import com.geosieben.gsbworkday.entity.EmployeeJoiningInfo;
import com.geosieben.gsbworkday.entity.EmployeeTargetHours;
import com.geosieben.gsbworkday.repository.BankInfoRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.DepartmentRepository;
import com.geosieben.gsbworkday.repository.DesignationInfoRepository;
import com.geosieben.gsbworkday.repository.JoiningInfoRepository;

import jakarta.transaction.Transactional;

@Service 
public class EmployeeUpdatService {
    private final EmployeeTargetHoursRepository employeeTargetHoursRepository;
    private  final BasicInfoRepository basicInfoRepository;
    private final JoiningInfoRepository joiningInfoRepository;
    private final DesignationInfoRepository designationInfoRepository;
    private  final DepartmentRepository departmentRepository;
    private final BankInfoRepository bankInfoRepository;
    

 public EmployeeUpdatService(EmployeeTargetHoursRepository employeeTargetHoursRepository,
            BasicInfoRepository basicInfoRepository, JoiningInfoRepository joiningInfoRepository,
            DesignationInfoRepository designationInfoRepository, DepartmentRepository departmentRepository,
            BankInfoRepository bankInfoRepository) {
        this.employeeTargetHoursRepository = employeeTargetHoursRepository;
        this.basicInfoRepository = basicInfoRepository;
        this.joiningInfoRepository = joiningInfoRepository;
        this.designationInfoRepository = designationInfoRepository;
        this.departmentRepository = departmentRepository;
        this.bankInfoRepository = bankInfoRepository;
    }
 @Transactional
public ResponseEntity<?> updateBasicInfoService(
        String employeeId,
        String address,
        String bloodGroup,
        LocalDate dob,
        String firstName,
        String gender,
        String lastName,
        String maritalStatus,
        String personalEmail,
        String phone
) {
    EmployeeBasicInfo basic = basicInfoRepository.findEmployeeBasicInfoByEID(employeeId);
    if (basic == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Employee basic info not found for ID: " + employeeId));
    }

    basic.setAddress(nullIfBlank(address));
    basic.setBloodGroup(nullIfBlank(bloodGroup));
    basic.setDob(dob);
    basic.setFirstName(nullIfBlank(firstName));
    basic.setLastName(nullIfBlank(lastName));
    basic.setEmail(nullIfBlank(personalEmail));
    basic.setGender(nullIfBlank(gender));
    basic.setMaritalStatus(nullIfBlank(maritalStatus));
    basic.setPhone(nullIfBlank(phone));

    EmployeeBasicInfo saved = basicInfoRepository.save(basic);
    return ResponseEntity.ok(saved);
}
 @Transactional
public ResponseEntity<?> updateJoiningInfo(
             String status,
             LocalDate dateOfJoining,
             LocalDate confirmationDate,
             String employeeId,
             String manager,
             String workmail

){
    EmployeeJoiningInfo joiningInfo=joiningInfoRepository.findByEmployeeBasicInfo_EID(employeeId);
      if (joiningInfo == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Employee joining info not found for ID: " + employeeId));
    }
    joiningInfo.setConfirmationDate(confirmationDate);
    joiningInfo.setDateOfJoining(dateOfJoining);
    joiningInfo.setWorkmail(nullIfBlank(workmail));
   if  (!joiningInfo.getReportingManager().getEID().equals(manager)) joiningInfo.setReportingManager(basicInfoRepository.findEmployeeBasicInfoByEID(manager));
   joiningInfo.setStatus(status);
  EmployeeJoiningInfo saved= joiningInfoRepository.save(joiningInfo);
 return ResponseEntity.ok(saved);

}
 @Transactional
public ResponseEntity<?> updateDesignationInfo(
             String employeeId, 
             String department,
             String designation)
             {
                EmployeeDesignationInfo designationInfo=designationInfoRepository.findDesignationInfo_ByEid(employeeId);
                      if (designationInfo == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Employee designation info not found for ID: " + employeeId));
                 }
                 if(designationInfo.getDepartment().getId()!=Integer.valueOf(department))  designationInfo.setDepartment(departmentRepository.findById(Integer.valueOf(department)).orElse(null));
                 if((!designation.equals(designationInfo.getDesignation()) && ! department.equals("1")) ){
                EmployeeTargetHours targetHours=employeeTargetHoursRepository.findEmployeeTargetHoursByEidMonth(AddonServ.monthYearFormatter(LocalDate.now()), employeeId);
                if(targetHours !=null){
                    targetHours.setTargetHours(BigDecimal.valueOf( AddonServ.targetHoursMap.get(designation)));
                    targetHours.setExtendedTarget(BigDecimal.valueOf( AddonServ.extendedTargetMap.get(designation)));
                }
                else{
                    EmployeeTargetHours target=new EmployeeTargetHours();
                    target.setEmployee(basicInfoRepository.findEmployeeBasicInfoByEID(employeeId));
                    target.setMonthNum(LocalDate.now().getMonthValue());
                    target.setYear(LocalDate.now().getYear());
                    target.setTargetHours(BigDecimal.valueOf( AddonServ.targetHoursMap.get(designation)));
                    target.setExtendedTarget(BigDecimal.valueOf( AddonServ.extendedTargetMap.get(designation)));
                    target.setMonth(AddonServ.monthYearFormatter(LocalDate.now()));
                    employeeTargetHoursRepository.save(target);
                }
                designationInfo.setDesignation(designation);
                 }

                 EmployeeDesignationInfo saved=designationInfoRepository.save(designationInfo);
                  return ResponseEntity.ok(saved);
             }

      @Transactional
public ResponseEntity<?>  updateBankInfo(            
    @RequestParam("employeeId") String employeeId,
    String accountNumber,  
    String bankName,   
    String branchName,  
    String ifsc, 
    String paymentMode)   {
        EmployeeBankInfo bankInfo=bankInfoRepository.findEmployeeBankInfoByEid(employeeId);
        if (bankInfo == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("message", "Employee bank info not found for ID: " + employeeId));
        }
        bankInfo.setAccountNumber(accountNumber);
        bankInfo.setBankName(bankName);
        bankInfo.setBranchName(branchName);
        bankInfo.setIFSC(ifsc);
        bankInfo.setPaymentMode(paymentMode);

        EmployeeBankInfo saved=bankInfoRepository.save(bankInfo);
         return ResponseEntity.ok(saved);
    }    

private String nullIfBlank(String value) {
    return (value == null || value.isBlank()) ? null : value.trim();
}


}
