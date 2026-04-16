package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.SalaryRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.PayRollRequest;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Salary;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
import com.geosieben.gsbworkday.entity.SalaryStructure;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.DesignationInfoRepository;
import com.geosieben.gsbworkday.repository.SalaryExtraRepository;
import com.geosieben.gsbworkday.repository.SalaryStructureRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class PayRollService implements PayRollInterface{
    private final SalaryRepository salaryRepository;
    @Autowired
    private BasicInfoRepository basicInfoRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private SalaryStructureRepository salaryStructureRepository;
    @Autowired
    private SalaryExtraRepository salaryExtraRepository;
    PayRollService(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }
    @Transactional
    @Override
    public ResponseEntity<Map<String, String>> addToPayRoll(PayRollRequest dto) {
        Map<String,String> response=new HashMap<>();
        SalaryStructure salary=new SalaryStructure();
        EmployeeBasicInfo emp=basicInfoRepository.findEmployeeBasicInfoByEID(dto.getName());
        String eid=(String)httpSession.getAttribute("eid");
         EmployeeBasicInfo addedBy=basicInfoRepository.findEmployeeBasicInfoByEID(eid);
        salary.setEmployeeBasicInfo(emp);
        salary.setBasicPay(convert(dto.getBasic()));
        salary.setAnnualCtc(convert(dto.getAnnual()));
        salary.setConveyance(convert(dto.getConveyance()));
        salary.setStatutoryBonus(convert(dto.getBonus()));
        salary.setDesignation("Associate");
        salary.setEsiEmployeeShare(convert(dto.getEsiEmployee()));
        salary.setEsiEmployerShare(convert(dto.getEsiEmployer()));
        salary.setPfEmployeeShare(convert(dto.getPfEmployee()));
        salary.setPfEmployerShare(convert(dto.getPfEmployer()));
        salary.setPt(convert(dto.getPt()));
        salary.setMonthlyCtc(convert(dto.getMonthly()));
        salary.setNetPay(convert(dto.getNetPay()));
        salary.setOtherAllowences(convert(dto.getOther()));
        salary.setMedicalAllowence(convert(dto.getMedical()));
        salary.setAddedBy(addedBy);
        salary.setMonthlyGratuity(convert(dto.getGratuityMonthly()));
        salaryStructureRepository.save(salary);
        response.put("status", "success");
        response.put("message", "Added To PayRoll SuccessFully");

        return ResponseEntity.ok(response);
    }
public BigDecimal convert(BigDecimal num) {
    if (num == null) {
        return BigDecimal.ZERO.setScale(2);
    }
    return num.setScale(2, RoundingMode.HALF_UP);
}
@Override
public List<SalaryStructure> getStructures() {
   return salaryStructureRepository.findAll();
}
@Override
@Transactional
public ResponseEntity<Map<String, String>> addExtraDetails(String eid, double epay, double lop, String month, String type) {
   Map<String, String> response=new HashMap<>();
Optional<SalaryExtraDetails> salextra =salaryExtraRepository.findExisting(month, eid);
if(salextra.isPresent()){
    SalaryExtraDetails sal=salextra.get();
    if(type.equals("LOP")){
  sal.setLopindays(BigDecimal.valueOf(lop));
    }else{
    sal.setExtraPay(BigDecimal.valueOf(epay));
  }
    salaryExtraRepository.save(sal);
    response.put("status", "success");
     response.put("message", "Updated SuccessFully");
}
else{
    SalaryExtraDetails sal=new SalaryExtraDetails();
    String eidd=(String)httpSession.getAttribute("eid");
    EmployeeBasicInfo addedBy=basicInfoRepository.findEmployeeBasicInfoByEID(eidd);
    sal.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
    sal.setExtraPay(BigDecimal.valueOf(epay));
    sal.setLopindays(BigDecimal.valueOf(lop));
    sal.setMonth(month);
    sal.setAddedBy(addedBy);
    salaryExtraRepository.save(sal);
    response.put("status", "success");
    response.put("message", "Added SuccessFully");
}
return ResponseEntity.ok(response);
}
@Override
public List<SalaryExtraDetails> getExtraDetails() {
return salaryExtraRepository.findAll();
}
@Override
@Transactional()
public ResponseEntity<Map<String, String>> processPayRoll(String month) {
    Map<String,String> response =new HashMap<>();
    double lopDays=0;
    double extrapay=0;
    String modmonth=AddonServ.changePattern(month);
    System.out.println(modmonth+"test Data");
    int noofdays=AddonServ.noofdaysinmonth(month);
    List<SalaryStructure> salaries=salaryStructureRepository.getEligibleSalaries(modmonth);
    if(salaries.size()>0){
        for (SalaryStructure salary : salaries) {
            SalaryExtraDetails extra=salaryExtraRepository.findExisting(month,salary.getEmployeeBasicInfo().getEID()).orElse(null);
            if(extra !=null){
                lopDays=extra.getLopindays().doubleValue();
                extrapay=extra.getExtraPay().doubleValue();
            }
          double basic = lopDays > 0 ? salary.getBasicPay().subtract( salary.getBasicPay().divide(BigDecimal.valueOf(noofdays), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(lopDays))).doubleValue(): salary.getBasicPay().doubleValue();
          double conveyance = lopDays > 0 ? salary.getConveyance().subtract( salary.getConveyance().divide(BigDecimal.valueOf(noofdays), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(lopDays))).doubleValue(): salary.getConveyance().doubleValue();
          double medicalAllowence = lopDays > 0 ? salary.getMedicalAllowence().subtract( salary.getMedicalAllowence().divide(BigDecimal.valueOf(noofdays), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(lopDays))).doubleValue(): salary.getMedicalAllowence().doubleValue();
          double statutoryBonus = lopDays > 0 ? salary.getStatutoryBonus().subtract( salary.getStatutoryBonus().divide(BigDecimal.valueOf(noofdays), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(lopDays))).doubleValue(): salary.getStatutoryBonus().doubleValue();
          double otherAllowences = lopDays > 0 ? salary.getOtherAllowences().subtract( salary.getOtherAllowences().divide(BigDecimal.valueOf(noofdays), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(lopDays))).doubleValue(): salary.getOtherAllowences().doubleValue();
          double gross=basic+conveyance+medicalAllowence+statutoryBonus+otherAllowences;
          double pfEmployee=salary.getBasicPay().doubleValue()>14999?1800:salary.getBasicPay().doubleValue()*0.12;
          double pfEmployer=salary.getBasicPay().doubleValue()>14999?1950:salary.getBasicPay().doubleValue()*0.12;
          double esiEmployee=gross>21000?0:salary.getBasicPay().doubleValue()*0.0075;
          double pfEmployeePayable=(salary.getEsiEmployeeShare().doubleValue()==0 && esiEmployee>0)?esiEmployee:0;
          esiEmployee=0;
          double esiEmployer=gross>21000?0:salary.getBasicPay().doubleValue()*0.0325;
          double netPay=(gross-pfEmployee)+extrapay+pfEmployeePayable;
          double gratuity=((basic*15)/26)/12;
          Salary sal=new Salary();
          sal.setEmployeeBasicInfo(salary.getEmployeeBasicInfo());
          sal.setMonth(month);
          sal.setSalaryStructure(salary);
          sal.setNoofdays(BigDecimal.valueOf(noofdays));
          sal.setLopDays(BigDecimal.valueOf(lopDays));
          sal.setPayebaleDays(BigDecimal.valueOf(noofdays-lopDays));
          sal.setBasic(BigDecimal.valueOf(basic));
          sal.setConveyance(BigDecimal.valueOf(conveyance));
          sal.setMedicalAllowance(BigDecimal.valueOf(medicalAllowence));
          sal.setStatutoryBonus(BigDecimal.valueOf(statutoryBonus));
          sal.setOtherAllowance(BigDecimal.valueOf(otherAllowences));
          sal.setOtherEarnings(BigDecimal.valueOf(extrapay));
          sal.setPfEmployeeShare(BigDecimal.valueOf(pfEmployee));
          sal.setPfEmployerShare(BigDecimal.valueOf(pfEmployer));
          sal.setEsiEmployeeShare(BigDecimal.valueOf(esiEmployee)); 
          sal.setEsiEmployerShare(BigDecimal.valueOf(esiEmployer));
          sal.setGross(BigDecimal.valueOf(gross));
          sal.setNetPayeble(BigDecimal.valueOf(netPay));
          sal.setOtherEarnings(BigDecimal.valueOf(extrapay));
          sal.setMonthlyGratuity(BigDecimal.valueOf(gratuity));
        String eidd=(String)httpSession.getAttribute("eid");
        EmployeeBasicInfo addedBy=basicInfoRepository.findEmployeeBasicInfoByEID(eidd);
        sal.setProcessedBy(addedBy);

        salaryRepository.save(sal);
        }
        response.put("status", "success");
        response.put("message", "Added To PayRoll SuccessFully");
    }
return ResponseEntity.ok(response);
}

public List<Salary> getPayRoll(){
    return salaryRepository.findAll();
}

}
