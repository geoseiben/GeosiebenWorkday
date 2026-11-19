package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.SalaryRepository;
import com.geosieben.gsbworkday.repository.SalaryStructure1Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.nio.charset.StandardCharsets;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.PayRollRequest;
import com.geosieben.gsbworkday.dto.SalaryDto;
import com.geosieben.gsbworkday.dto.SalaryExtraDetailsDecryptedDto;
import com.geosieben.gsbworkday.dto.SalaryExtraDto;
import com.geosieben.gsbworkday.dto.SalaryResponseDecrypted;
import com.geosieben.gsbworkday.dto.SalaryStructureDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Salary;
import com.geosieben.gsbworkday.entity.Salary1;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails1;
import com.geosieben.gsbworkday.entity.SalaryProcess;
import com.geosieben.gsbworkday.entity.SalaryStructure1;
import com.geosieben.gsbworkday.entity.User;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.Salary1Repository;
import com.geosieben.gsbworkday.repository.SalaryExtraDetails1Repository;
import com.geosieben.gsbworkday.repository.SalaryExtraRepository;
import com.geosieben.gsbworkday.repository.SalaryProcessRepository;
import com.geosieben.gsbworkday.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class PayRollService implements PayRollInterface{
    @Value("${spring.secret.encryptionKey}")
    private String secretKey;
    private final SalaryRepository salaryRepository;
    private BasicInfoRepository basicInfoRepository;
    private HttpSession httpSession;
    private SalaryExtraRepository salaryExtraRepository;
private SalaryStructure1Repository salaryStructure1Repository;
    private UserRepository userRepository;
    private Salary1Repository salary1Repository;
private SalaryProcessRepository salaryProcessRepository;
private SalaryExtraDetails1Repository salaryExtraDetails1Repository;

    public PayRollService(
        SalaryRepository salaryRepository,
        BasicInfoRepository basicInfoRepository,
        HttpSession httpSession,
        SalaryExtraRepository salaryExtraRepository,
        SalaryStructure1Repository salaryStructure1Repository,
        UserRepository userRepository,
        Salary1Repository salary1Repository,
        SalaryProcessRepository salaryProcessRepository,
        SalaryExtraDetails1Repository salaryExtraDetails1Repository) {
        this.salaryRepository = salaryRepository;
        this.basicInfoRepository = basicInfoRepository;
        this.httpSession = httpSession;
        this.salaryExtraRepository = salaryExtraRepository;
        this.salaryStructure1Repository = salaryStructure1Repository;
        this.userRepository = userRepository;
        this.salary1Repository = salary1Repository;
        this.salaryProcessRepository=salaryProcessRepository;
        this.salaryExtraDetails1Repository =salaryExtraDetails1Repository;
    }
    @Transactional
    @Override
    public ResponseEntity<Map<String, String>> addToPayRoll(PayRollRequest dto) {

        Map<String,String> response=new HashMap<>();
        EmployeeBasicInfo emp=basicInfoRepository.findEmployeeBasicInfoByEID(dto.getName());
        String eid=(String)httpSession.getAttribute("eid");
         EmployeeBasicInfo addedBy=basicInfoRepository.findEmployeeBasicInfoByEID(eid);
    
        try{
        byte[] iv=getIv();
        SecretKeySpec secretKeySpec=getSecretKey(secretKey);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SalaryStructure1 salaryStructure1=new SalaryStructure1();
        salaryStructure1.setEmployeeBasicInfo(emp);
        salaryStructure1.setAddedBy(addedBy);
        salaryStructure1.setBasicPay(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getBasic()).toString()));
        salaryStructure1.setAnnualCtc(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getAnnual()).toString()));
        salaryStructure1.setConveyance(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getConveyance()).toString()));
        salaryStructure1.setStatutoryBonus(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getBonus()).toString()));
        salaryStructure1.setDesignation(encyptPlainText(secretKeySpec, ivSpec,"Associate"));
        salaryStructure1.setPfEmployeeShare(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getPfEmployee()).toString()));
        salaryStructure1.setPfEmployerShare(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getPfEmployer()).toString()));
        salaryStructure1.setEsiEmployeeShare(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getEsiEmployee()).toString()));
        salaryStructure1.setEsiEmployerShare(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getEsiEmployer()).toString()));
        salaryStructure1.setPt(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getPt()).toString()));
        salaryStructure1.setMonthlyCtc(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getMonthly()).toString()));
        salaryStructure1.setNetPay(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getNetPay()).toString()));
        salaryStructure1.setOtherAllowences(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getOther()).toString()));
        salaryStructure1.setMedicalAllowence(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getMedical()).toString()));
        salaryStructure1.setMonthlyGratuty(encyptPlainText(secretKeySpec, ivSpec,convert(dto.getGratuityMonthly()).toString()));
        salaryStructure1Repository.save(salaryStructure1);
        salaryStructure1.setIv(iv);
        }
        catch(Exception e){

        }
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
public List<SalaryStructureDto> getStructures() {
   return getEncryptedPayrollData();
}
@Override
@Transactional
public ResponseEntity<Map<String, String>> addExtraDetails(String eid, double epay, double lop, String month, String type) throws Exception  {
   Map<String, String> response=new HashMap<>();
//Optional<SalaryExtraDetails> salextra =salaryExtraRepository.findExisting(month, eid);
Optional<SalaryExtraDetails1> salextra =salaryExtraDetails1Repository.findByMonthEid(month, eid);

if(salextra.isPresent()){
    SalaryExtraDetails1 sal=salextra.get();
    if(type.equals("LOP")){
  sal.setLopindays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()), String.valueOf(lop)));
    }else{
  sal.setExtraPay(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()), String.valueOf(epay)));
  }
    salaryExtraDetails1Repository.save(sal);
    response.put("status", "success");
     response.put("message", "Updated SuccessFully");
}
else{
    SalaryExtraDetails1 sal=new SalaryExtraDetails1();
    String eidd=(String)httpSession.getAttribute("eid");
    EmployeeBasicInfo addedBy=basicInfoRepository.findEmployeeBasicInfoByEID(eidd);
    byte[] iv=getIv();
    sal.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
    sal.setExtraPay( encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(iv), String.valueOf(epay)));
        sal.setLopindays( encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(iv), String.valueOf(lop)));
        sal.setIv(iv);
    sal.setMonth(month);
    sal.setAddedBy(addedBy);
    salaryExtraDetails1Repository.save(sal);
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
public ResponseEntity<Map<String, String>> processPayRoll(String month) throws Exception{
    Map<String,String> response =new HashMap<>();

    int noofdays=AddonServ.noofdaysinmonth(month);
    List<SalaryStructureDto> salaries=getEncryptedPayrollData();
    if(salaries.size()>0){
        for (SalaryStructureDto salary : salaries) {
                double lopDays=0;
    double extrapay=0;
            Optional<SalaryExtraDetails1> extra=salaryExtraDetails1Repository.findByMonthEid(month,salary.getEID());
            if(extra.isPresent()){
            SalaryExtraDetails1 salextra=extra.get();
            lopDays=Double.parseDouble(decryptCipherText(getSecretKey(secretKey), new IvParameterSpec(salextra.getIv()), salextra.getLopindays()));
            extrapay=Double.parseDouble(decryptCipherText(getSecretKey(secretKey), new IvParameterSpec(salextra.getIv()), salextra.getExtraPay()));
        }
          double basic = lopDays > 0 ? (salary.getBasicPay()-(salary.getBasicPay()/noofdays)*lopDays):salary.getBasicPay();
          double conveyance = lopDays > 0 ? (salary.getConveyance()-(salary.getConveyance()/noofdays)*lopDays):salary.getConveyance();
          double medicalAllowence = lopDays > 0 ?(salary.getMedicalAllowence()-(salary.getMedicalAllowence()/noofdays)*lopDays):salary.getMedicalAllowence();
          double statutoryBonus = lopDays > 0 ? (salary.getStatutoryBonus()-(salary.getStatutoryBonus()/noofdays)*lopDays):salary.getStatutoryBonus();
          double otherAllowences = lopDays > 0 ?(salary.getOtherAllowences()-(salary.getOtherAllowences()/noofdays)*lopDays):salary.getOtherAllowences();
          double gross=basic+conveyance+medicalAllowence+statutoryBonus+otherAllowences;
          double pfEmployee=salary.getBasicPay()>14999?1800:salary.getBasicPay()*0.12;
          double pfEmployer=salary.getBasicPay()>14999?1950:salary.getBasicPay()*0.12;
          double esiEmployee=gross>21000?0:salary.getBasicPay()*0.0075;
          double pfEmployeePayable=(salary.getEsiEmployeeShare()==0 && esiEmployee>0)?esiEmployee:0;
          esiEmployee=0;
          double esiEmployer=gross>21000?0:salary.getBasicPay()*0.0325;
          double pt=gross>25000?200:0;
          double netPay=(gross-pfEmployee-esiEmployee-pt)+extrapay+pfEmployeePayable;
          double gratuity=((basic*15)/26)/12;
          try{
                  byte[] iv=getIv();
        SecretKeySpec secretKeySpec=getSecretKey(secretKey);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
          Salary1 sal=new Salary1();
          
          sal.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(salary.getEID()));
          sal.setMonth(month);
          sal.setSalaryStructure(salaryStructure1Repository.findById(salary.getSalid()).orElse(null));
          sal.setNoofdays(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(noofdays)));
          sal.setLopdays(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(lopDays)));
          sal.setPayableDays(encyptPlainText(secretKeySpec, ivSpec,String.valueOf((noofdays-lopDays))));
          sal.setBasic(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(basic)));
          sal.setConveyance(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(conveyance)));
          sal.setMedicalAllowence(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(medicalAllowence)));
          sal.setStatutoryBonus(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(statutoryBonus)));
          sal.setOtherAllowences(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(otherAllowences)));
          sal.setOtherEarnings(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(extrapay)));
          sal.setPfEmployeeShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(pfEmployee)));
          sal.setPfEmployerShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(pfEmployer)));
          sal.setEsiEmployeeShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(esiEmployee))); 
          sal.setEsiEmployerShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(esiEmployer)));
          sal.setGross(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(gross)));
          sal.setNetPayeble(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(netPay)));
          sal.setOtherEarnings(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(extrapay)));
          sal.setGratuty(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(gratuity)));
          sal.setPt(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(pt)));
          sal.setIv(iv);
        String eidd=(String)httpSession.getAttribute("eid");
        EmployeeBasicInfo addedBy=basicInfoRepository.findEmployeeBasicInfoByEID(eidd);
        sal.setProcessedBy(addedBy);

        salary1Repository.save(sal);
          }

          catch(Exception e){
             response.put("status", "error");
        response.put("message", e.getMessage());
        return ResponseEntity.ok(response);
          }
        }
                      SalaryProcess salProcess=new SalaryProcess();
        salProcess.setMonth(month);
        salProcess.setProcessedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
        salaryProcessRepository.save(salProcess);
        response.put("status", "success");
        response.put("message", "Payroll Processed");
    }
return ResponseEntity.ok(response);
}

public List<Salary> getPayRoll(){
    return salaryRepository.findAll();
}
@Override
public ResponseEntity<Map<String, String>> authenticatePayRollUser(String password) {
    Map<String,String> response =new HashMap<>();
        String eid=(String)httpSession.getAttribute("eid");
    User user =userRepository.findByEmployeeBasicInfo_EID(eid);
    if(user!=null){
    if(BCrypt.checkpw(password, user.getVaultPassword())){
     response.put("status", "success");
        response.put("message", "authenticated");
    }
    else{
        response.put("status", "error");
        response.put("message", "Incorrect Password");
    }
    }
    else {
        response.put("status", "error");
        response.put("message", "User Not  Found");
    }
    return ResponseEntity.ok(response);
}
@Override
public ResponseEntity<Map<String, String>> changePassword(String password, String newPassword) {
        Map<String,String> response =new HashMap<>();
        String eid=(String)httpSession.getAttribute("eid");
    User user =userRepository.findByEmployeeBasicInfo_EID(eid);
    if(user!=null){
        if(BCrypt.checkpw(password, user.getVaultPassword())){
            String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setVaultPassword(hashed);
            userRepository.save(user);
        }
        else{
        response.put("status", "error");
        response.put("message", "Incorrect Current  Password"); 
        }

    }
    else{
      
    }
    return ResponseEntity.ok(response);
}
@Override
public List<EmployeeBasicInfo> fetchEmployees(String type, String month) {
    List<EmployeeBasicInfo> emps=new ArrayList<EmployeeBasicInfo>();
if(type.equals("payroll")){
    emps= basicInfoRepository.payRollEmployee();
}
else if(type.equals("lop")){
emps= basicInfoRepository.getlopEmplpoyees(month);
}
else if(type.equals("epay")){
emps= basicInfoRepository.getextrapayEmplpoyees(month);
}
return emps;
}
@Override
public Map<String,String> updateSalary(int salid, double lopDays, double extrapay, String month, int payid)throws Exception{
           Map<String,String> response=new HashMap<>();
    SalaryStructureDto salary=getEncryptedPayrollDataById(salid);

    System.out.println(salid);
    if(salary!=null){
          double noofdays=AddonServ.noofdaysinmonth(month);
          double basic = lopDays > 0 ? salary.getBasicPay() - ((salary.getBasicPay()/noofdays)*lopDays): salary.getBasicPay();
          double conveyance = lopDays > 0 ? salary.getConveyance() - ((salary.getConveyance()/noofdays)*lopDays): salary.getConveyance();
          double medicalAllowence = lopDays > 0 ? salary.getMedicalAllowence() - ((salary.getMedicalAllowence()/noofdays)*lopDays): salary.getMedicalAllowence();
          double statutoryBonus = lopDays > 0 ?  salary.getStatutoryBonus() - ((salary.getStatutoryBonus()/noofdays)*lopDays): salary.getStatutoryBonus();
          double otherAllowences = lopDays > 0 ?  salary.getOtherAllowences() - ((salary.getOtherAllowences()/noofdays)*lopDays): salary.getOtherAllowences();
          double gross=basic+conveyance+medicalAllowence+statutoryBonus+otherAllowences;
          double pfEmployee=salary.getBasicPay()>14999?1800:salary.getBasicPay()*0.12;
          double pfEmployer=salary.getBasicPay()>14999?1950:salary.getBasicPay()*0.12;
          double esiEmployee=gross>21000?0:salary.getBasicPay()*0.0075;
          double pfEmployeePayable=(salary.getEsiEmployeeShare()==0 && esiEmployee>0)?esiEmployee:0;
          esiEmployee=0;
          double esiEmployer=gross>21000?0:salary.getBasicPay()*0.0325;
          double pt=gross>25000?200:0;
          double netPay=(gross-pfEmployee-pfEmployee-pt)+extrapay+pfEmployeePayable;
          double gratuity=((basic*15)/26)/12;
          System.out.println(salary.toString());
          Salary1 sal=salary1Repository.getSalaryByMonthEid(salary.getEID(), month).orElse(null);
            if(sal!=null){
          sal.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(salary.getEID()));
          sal.setMonth(month);
          sal.setSalaryStructure(salaryStructure1Repository.findById(salary.getSalid()).orElse(null));
          sal.setNoofdays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(noofdays)));
          sal.setLopdays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(lopDays)));
          sal.setPayableDays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf((noofdays-lopDays))));
          sal.setBasic(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(basic)));
          sal.setConveyance(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(conveyance)));
          sal.setMedicalAllowence(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(medicalAllowence)));
          sal.setStatutoryBonus(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(statutoryBonus)));
          sal.setOtherAllowences(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(otherAllowences)));
          sal.setOtherEarnings(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(extrapay)));
          sal.setPfEmployeeShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(pfEmployee)));
          sal.setPfEmployerShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(pfEmployer)));
          sal.setEsiEmployeeShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(esiEmployee))); 
          sal.setEsiEmployerShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(esiEmployer)));
          sal.setGross(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(gross)));
          sal.setNetPayeble(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(netPay)));
          sal.setGratuty(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(gratuity)));
          sal.setPt(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(pt)));
        salary1Repository.save(sal);
        }
                  Optional<SalaryExtraDetails1> extra= salaryExtraDetails1Repository.findByMonthEid(month,salary.getEID());
          if(extra.isPresent()){
            SalaryExtraDetails1 ex=extra.get();
          
            ex.setExtraPay(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ex.getIv()), String.valueOf(extrapay)));
            ex.setLopindays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ex.getIv()), String.valueOf(lopDays)));
            salaryExtraDetails1Repository.save(ex);

          }
          else{
              byte[] ivnew=getIv();
            SalaryExtraDetails1 ex1=new SalaryExtraDetails1();
            ex1.setMonth(month);
            ex1.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(salary.getEID()));
            ex1.setLopindays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ivnew),String.valueOf(lopDays)));
            ex1.setLopindays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ivnew),String.valueOf(extrapay)));
            ex1.setAddedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
            ex1.setIv(ivnew);
            salaryExtraDetails1Repository.save(ex1);

          }
              response.put("status", "Success");
        response.put("message", "Lop Updated Successfully");

    }
    return response;
}
@Override
public ResponseEntity<Map<String, String>> updateSalaryExisting(int salaryid, int salid, double lopDays, double extraPay,String month)throws Exception {
        return ResponseEntity.ok(updateSalary(salid, lopDays, extraPay, month, salaryid));
}

public byte[] encyptPlainText(SecretKeySpec secretKey,IvParameterSpec iv,String plaintext) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
return encryptedBytes;
}
 public SecretKeySpec getSecretKey(String password) throws Exception{
            MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hexSalt = "";
        for (byte b : md5Bytes) hexSalt += String.format("%02x", b);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), hexSalt.getBytes(StandardCharsets.UTF_8), 10000, 128);
        byte[] rawKey = factory.generateSecret(spec).getEncoded();
        SecretKeySpec secretKey = new SecretKeySpec(rawKey, "AES");
        return secretKey;
 }

 public byte[] getIv() throws Exception{
         byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
       return iv;
 }

public String decryptCipherText(SecretKeySpec secretKeySpec,IvParameterSpec ivParameterSpec,byte[] cipherText)throws Exception
{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
        return decryptedMessage;
}

public List<SalaryStructureDto> getEncryptedPayrollData(){
     List<SalaryStructure1> sal1=salaryStructure1Repository.findAll();
    List<SalaryStructureDto> response=new ArrayList<>();
    try{
            for(SalaryStructure1 s:sal1){
                SecretKeySpec secretKeySpec=getSecretKey(secretKey);
                byte[] iv=s.getIv();
                SalaryStructureDto sal=new SalaryStructureDto();
                sal.setSalid(s.getSalid());
                sal.setEmployeeName(s.getEmployeeBasicInfo().getFirstName()+" "+s.getEmployeeBasicInfo().getLastName());
                sal.setEID(s.getEmployeeBasicInfo().getEID());
                System.out.println(s.getEmployeeBasicInfo().getEID());
                sal.setAnnualCtc(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getAnnualCtc())));
                sal.setBasicPay(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getBasicPay())));
                sal.setConveyance(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getConveyance())));
                sal.setMedicalAllowence(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getMedicalAllowence())));
                sal.setStatutoryBonus(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getStatutoryBonus())));
                sal.setOtherAllowences(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getOtherAllowences())));
                sal.setPfEmployeeShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getPfEmployeeShare())));
                sal.setPfEmployerShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getPfEmployerShare())));
                sal.setEsiEmployeeShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getEsiEmployeeShare())));
                sal.setEsiEmployerShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getEsiEmployerShare())));
                sal.setMonthlyGratuty(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getMonthlyGratuty())));
                sal.setNetPay(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getNetPay())));
                                sal.setMonthlyCtc(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getMonthlyCtc())));

                response.add(sal);
            }
          
    }
    catch(Exception e){

    }
     return response;
}

public SalaryStructureDto getEncryptedPayrollDataById(int id){
    
     Optional<SalaryStructure1> sal1=salaryStructure1Repository.findById(id);
    SalaryStructureDto response=new SalaryStructureDto();
    try{
            if(sal1.isPresent()){
                SalaryStructure1 s=sal1.get();
                SecretKeySpec secretKeySpec=getSecretKey(secretKey);
                byte[] iv=s.getIv();
                SalaryStructureDto sal=new SalaryStructureDto();
                sal.setSalid(s.getSalid());
                sal.setEmployeeName(s.getEmployeeBasicInfo().getFirstName()+" "+s.getEmployeeBasicInfo().getLastName());
                sal.setEID(s.getEmployeeBasicInfo().getEID());
                sal.setAnnualCtc(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getAnnualCtc())));
                sal.setBasicPay(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getBasicPay())));
                sal.setConveyance(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getConveyance())));
                sal.setMedicalAllowence(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getMedicalAllowence())));
                sal.setStatutoryBonus(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getStatutoryBonus())));
                sal.setOtherAllowences(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getOtherAllowences())));
                sal.setPfEmployeeShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getPfEmployeeShare())));
                sal.setPfEmployerShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getPfEmployerShare())));
                sal.setEsiEmployeeShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getEsiEmployeeShare())));
                sal.setEsiEmployerShare(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getEsiEmployerShare())));
                sal.setMonthlyGratuty(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getMonthlyGratuty())));
                sal.setNetPay(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getNetPay())));
                sal.setMonthlyCtc(Double.parseDouble(decryptCipherText(secretKeySpec, new IvParameterSpec(iv), s.getMonthlyCtc())));
                response=sal;

            }
    }
    catch(Exception e){

    }
     return response;
}


public  List<SalaryResponseDecrypted> getSalaryDecrypted() throws NumberFormatException, Exception{
    List<SalaryDto> res=salary1Repository.getAllSalary();
    List<SalaryResponseDecrypted> deccryptedSalary=new ArrayList<>();
    for(SalaryDto sal:res){
        SalaryResponseDecrypted salDec=new SalaryResponseDecrypted();
        salDec.setSalaryId(sal.getSalaryId());
        salDec.setSalid(sal.getSalid());
        salDec.setMonth(sal.getMonth());
        salDec.setEID(sal.getEID());
        salDec.setFirstName(sal.getFirstName());
        salDec.setLastName(sal.getLastName());
        salDec.setAccountNumber(sal.getAccountNumber());
        salDec.setBankName(sal.getBankName());
        salDec.setDateOfJoining(sal.getDateOfJoining());
        salDec.setPan(sal.getPan());
        salDec.setUan(sal.getUan());
        salDec.setPfNo(sal.getPfNo());
        salDec.setNoOfDays(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getNoofdays())));
        salDec.setLopDays(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getLopDays())));
         salDec.setPayableDays(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getPayableDays())));
        salDec.setDesignation(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getSsiv()), sal.getDesignation()));
        salDec.setBasic(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getBasic())));
        salDec.setConveyance(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getConveyance())));
        salDec.setMedicalAllowence(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getMedicalAllowence())));
        salDec.setStatutoryBonus(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getStatutoryBonus())));
        salDec.setOtherAllowences(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getOtherAllowences())));
        salDec.setOtherEarnings(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getOtherEarnings())));
        salDec.setPfEmployeeShare(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getPfEmployeeShare())));
        salDec.setPfEmployerShare(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getPfEmployerShare())));
        salDec.setEsiEmployeeShare(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getEsiEmployeeShare())));
        salDec.setEsiEmployerShare(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getEsiEmployerShare())));
        salDec.setPt(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getPt())));
        salDec.setGross(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getGross())));
        salDec.setNetPayeble(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getNetPayeble())));
        salDec.setPt(Double.parseDouble(decryptCipherText(getSecretKey(secretKey),new IvParameterSpec(sal.getIv()), sal.getPt())));
        deccryptedSalary.add(salDec);
    }
    return deccryptedSalary;
}

public List<SalaryExtraDetailsDecryptedDto> getSalaryExtraDecrypted() throws Exception{
    List<SalaryExtraDto> extras=salaryExtraDetails1Repository.findExtraEncrypted();
    List<SalaryExtraDetailsDecryptedDto> res=new ArrayList<>();
    for(SalaryExtraDto sal:extras){
        SalaryExtraDetailsDecryptedDto temp=new SalaryExtraDetailsDecryptedDto();
        temp.setId(sal.getId());
        temp.setEmployee(sal.getFirstName()+" "+sal.getLastName());
        temp.setEID(sal.getEID());
        temp.setMonth(sal.getMonth());
        temp.setLopindays(Double.parseDouble(decryptCipherText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()), sal.getLopindays())));
        temp.setExtraPay(Double.parseDouble(decryptCipherText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()), sal.getExtraPay())));
        temp.setCreated(sal.getUpdatedOn());
        res.add(temp);
    }
    return res;
}
@Transactional
public ResponseEntity<Map<String,String>> updateExtraDetails(int id,double lopDays,double extraPay) throws Exception{
Map<String,String> response=new HashMap<>();
    Optional<SalaryExtraDetails1> extra =salaryExtraDetails1Repository.findById(id);
    if(extra.isPresent()){
        SalaryExtraDetails1 ex=extra.get();
        Optional<Salary1> exSal=salary1Repository.getSalaryByMonthEid(ex.getEmployeeBasicInfo().getEID(), ex.getMonth());
        if(exSal.isPresent()){
                      int noofdays=AddonServ.noofdaysinmonth(ex.getMonth());

            SalaryStructureDto salary=getEncryptedPayrollDataById(exSal.get().getSalaryStructure().getSalid());
             double basic = lopDays > 0 ? (salary.getBasicPay()-(salary.getBasicPay()/noofdays)*lopDays):salary.getBasicPay();
          double conveyance = lopDays > 0 ? (salary.getConveyance()-(salary.getConveyance()/noofdays)*lopDays):salary.getConveyance();
          double medicalAllowence = lopDays > 0 ?(salary.getMedicalAllowence()-(salary.getMedicalAllowence()/noofdays)*lopDays):salary.getMedicalAllowence();
          double statutoryBonus = lopDays > 0 ? (salary.getStatutoryBonus()-(salary.getStatutoryBonus()/noofdays)*lopDays):salary.getStatutoryBonus();
          double otherAllowences = lopDays > 0 ?(salary.getOtherAllowences()-(salary.getOtherAllowences()/noofdays)*lopDays):salary.getOtherAllowences();
          System.out.println("Basic:"+basic+"\tConveyance:"+conveyance+"\tMedical"+medicalAllowence+"\tStatutory"+statutoryBonus+"\tOthers"+otherAllowences);
          double gross=basic+conveyance+medicalAllowence+statutoryBonus+otherAllowences;
          double pfEmployee=salary.getBasicPay()>14999?1800:salary.getBasicPay()*0.12;
          double pfEmployer=salary.getBasicPay()>14999?1950:salary.getBasicPay()*0.13;
          double esiEmployee=gross>21000?0:salary.getBasicPay()*0.0075;
          double pfEmployeePayable=0;
          if(salary.getEsiEmployeeShare()==0 && esiEmployee>0){
           pfEmployeePayable=(salary.getEsiEmployeeShare()==0 && esiEmployee>0)?esiEmployee:0;
           esiEmployee=0;
          }
         
          double esiEmployer=gross>21000?0:salary.getBasicPay()*0.0325;
          double pt=gross>25000?200:0;
          double netPay=(gross-pfEmployee-esiEmployee-pt)+extraPay+pfEmployeePayable;
          double gratuity=((basic*15)/26)/12;
          
        byte[] iv=exSal.get().getIv();
        SecretKeySpec secretKeySpec=getSecretKey(secretKey);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
          Salary1 sal=exSal.get();
          sal.setMonth(ex.getMonth());
          sal.setNoofdays(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(noofdays)));
          sal.setLopdays(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(lopDays)));
          sal.setPayableDays(encyptPlainText(secretKeySpec, ivSpec,String.valueOf((noofdays-lopDays))));
          sal.setBasic(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(basic)));
          sal.setConveyance(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(conveyance)));
          sal.setMedicalAllowence(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(medicalAllowence)));
          sal.setStatutoryBonus(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(statutoryBonus)));
          sal.setOtherAllowences(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(otherAllowences)));
          sal.setOtherEarnings(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(extraPay)));
          sal.setPfEmployeeShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(pfEmployee)));
          sal.setPfEmployerShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(pfEmployer)));
          sal.setEsiEmployeeShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(esiEmployee))); 
          sal.setEsiEmployerShare(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(esiEmployer)));
          sal.setGross(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(gross)));
          sal.setNetPayeble(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(netPay)));
          sal.setGratuty(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(gratuity)));
          sal.setPt(encyptPlainText(secretKeySpec, ivSpec,String.valueOf(pt)));
        salary1Repository.save(sal);
        }

        ex.setExtraPay(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ex.getIv()), String.valueOf(extraPay)));
        ex.setLopindays (encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ex.getIv()), String.valueOf(lopDays)));
        salaryExtraDetails1Repository.save(ex);

    }
    response.put("status", "Success");
        response.put("message", "Lop Updated Successfully");

    return ResponseEntity.ok(response);
}

public Map<String,String> updateSalary1(int salid, double lopDays, double extrapay, String month, int payid) throws Exception{
    Map<String,String> response=new HashMap<>();
    SalaryStructureDto salary=getEncryptedPayrollDataById(salid);
    if(salary!=null){
          double noofdays=AddonServ.noofdaysinmonth(month);
          double basic = lopDays > 0 ? salary.getBasicPay() - ((salary.getBasicPay()/noofdays)*lopDays): salary.getBasicPay();
          double conveyance = lopDays > 0 ? salary.getConveyance() - ((salary.getConveyance()/noofdays)*lopDays): salary.getConveyance();
          double medicalAllowence = lopDays > 0 ? salary.getMedicalAllowence() - ((salary.getMedicalAllowence()/noofdays)*lopDays): salary.getMedicalAllowence();
          double statutoryBonus = lopDays > 0 ?  salary.getStatutoryBonus() - ((salary.getStatutoryBonus()/noofdays)*lopDays): salary.getStatutoryBonus();
          double otherAllowences = lopDays > 0 ?  salary.getOtherAllowences() - ((salary.getOtherAllowences()/noofdays)*lopDays): salary.getOtherAllowences();
          double gross=basic+conveyance+medicalAllowence+statutoryBonus+otherAllowences;
          double pfEmployee=salary.getBasicPay()>14999?1800:salary.getBasicPay()*0.12;
          double pfEmployer=salary.getBasicPay()>14999?1950:salary.getBasicPay()*0.12;
          double esiEmployee=gross>21000?0:salary.getBasicPay()*0.0075;
          double pfEmployeePayable=(salary.getEsiEmployeeShare()==0 && esiEmployee>0)?esiEmployee:0;
          esiEmployee=0;
          double esiEmployer=gross>21000?0:salary.getBasicPay()*0.0325;
          double pt=gross>25000?200:0;
          double netPay=(gross-pfEmployee-pfEmployee-pt)+extrapay+pfEmployeePayable;
          double gratuity=((basic*15)/26)/12;

          Salary1 sal=salary1Repository.getSalaryByMonthEid(salary.getEID(), month).orElse(null);
                      if(sal!=null){
          sal.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(salary.getEID()));
          sal.setMonth(month);
          sal.setSalaryStructure(salaryStructure1Repository.findById(salary.getSalid()).orElse(null));
          sal.setNoofdays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(noofdays)));
          sal.setLopdays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(lopDays)));
          sal.setPayableDays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf((noofdays-lopDays))));
          sal.setBasic(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(basic)));
          sal.setConveyance(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(conveyance)));
          sal.setMedicalAllowence(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(medicalAllowence)));
          sal.setStatutoryBonus(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(statutoryBonus)));
          sal.setOtherAllowences(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(otherAllowences)));
          sal.setOtherEarnings(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(extrapay)));
          sal.setPfEmployeeShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(pfEmployee)));
          sal.setPfEmployerShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(pfEmployer)));
          sal.setEsiEmployeeShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(esiEmployee))); 
          sal.setEsiEmployerShare(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(esiEmployer)));
          sal.setGross(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(gross)));
          sal.setNetPayeble(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(netPay)));
          sal.setGratuty(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(gratuity)));
          sal.setPt(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(sal.getIv()),String.valueOf(pt)));
        salary1Repository.save(sal);
        }
                  Optional<SalaryExtraDetails1> extra= salaryExtraDetails1Repository.findByMonthEid(month,sal.getEmployeeBasicInfo().getEID());
          if(extra.isPresent()){
            SalaryExtraDetails1 ex=extra.get();
          
            ex.setExtraPay(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ex.getIv()), String.valueOf(extrapay)));
            ex.setLopindays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ex.getIv()), String.valueOf(lopDays)));
            salaryExtraDetails1Repository.save(ex);

          }
          else{
              byte[] ivnew=getIv();
            SalaryExtraDetails1 ex1=new SalaryExtraDetails1();
            ex1.setMonth(month);
            ex1.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(salary.getEID()));
            ex1.setLopindays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ivnew),String.valueOf(lopDays)));
            ex1.setLopindays(encyptPlainText(getSecretKey(secretKey), new IvParameterSpec(ivnew),String.valueOf(extrapay)));
            ex1.setAddedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
            ex1.setIv(ivnew);
            salaryExtraDetails1Repository.save(ex1);

          }
              response.put("status", "Success");
        response.put("message", "Lop Updated Successfully");

    }
    return response;
}


}
