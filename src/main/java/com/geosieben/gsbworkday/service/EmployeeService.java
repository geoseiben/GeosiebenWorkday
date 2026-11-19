package com.geosieben.gsbworkday.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.ActiveAllotments;
import com.geosieben.gsbworkday.dto.AvailableUsers;
import com.geosieben.gsbworkday.dto.EmployeeRequestDto;
import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.dto.LeaveAvailedDto;
import com.geosieben.gsbworkday.dto.LeaveAvailedProj;
import com.geosieben.gsbworkday.dto.LeaveAvailedProjMonth;
import com.geosieben.gsbworkday.dto.ResourceDto;
import com.geosieben.gsbworkday.dto.ResourceRespnseDto;
import com.geosieben.gsbworkday.dto.SkillsResponse;
import com.geosieben.gsbworkday.dto.StatsDto;
import com.geosieben.gsbworkday.entity.*;
import com.geosieben.gsbworkday.repository.*;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService implements EmployeeServiceInterface {
    private final SeparationRepository separationRepository;
    private final DepartmentRepository departmentRepository;
    private final BasicInfoRepository basicInfoRepository;
    private final JoiningInfoRepository joiningInfoRepository;
    private final DesignationInfoRepository designationInfoRepository;
    private final BankInfoRepository bankInfoRepository;
    private final EducationInfoRepository educationInfoRepository;
    private final StatutoryInfoRepository statutoryInfoRepository;
    private final EmergencyInfoRepository emergencyInfoRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final SkillsRepository skillsRepository;
    private final ProjectRepository projectRepository;
    private final IdCardIssueRepository idCardIssueRepository;
    private final HttpSession httpSession;
    private final EmployeeTargetHoursRepository employeeTargetHoursRepository;
    private final LeaveRepository leaveRepository;
    private final DocumentRequestRepository documentRequestRepository;
    @JsonFormat(pattern = "MMM-yyyy")
    private  LocalDate currentDate=LocalDate.now();

    public EmployeeService(SeparationRepository separationRepository, DepartmentRepository departmentRepository,
            BasicInfoRepository basicInfoRepository, JoiningInfoRepository joiningInfoRepository,
            DesignationInfoRepository designationInfoRepository, BankInfoRepository bankInfoRepository,
            EducationInfoRepository educationInfoRepository, StatutoryInfoRepository statutoryInfoRepository,
            EmergencyInfoRepository emergencyInfoRepository, UserRepository userRepository,
            ImageRepository imageRepository, SkillsRepository skillsRepository, ProjectRepository projectRepository,
            IdCardIssueRepository idCardIssueRepository, HttpSession httpSession,
            EmployeeTargetHoursRepository employeeTargetHoursRepository, LeaveRepository leaveRepository,
            DocumentRequestRepository documentRequestRepository) {
        this.separationRepository = separationRepository;
        this.departmentRepository = departmentRepository;
        this.basicInfoRepository = basicInfoRepository;
        this.joiningInfoRepository = joiningInfoRepository;
        this.designationInfoRepository = designationInfoRepository;
        this.bankInfoRepository = bankInfoRepository;
        this.educationInfoRepository = educationInfoRepository;
        this.statutoryInfoRepository = statutoryInfoRepository;
        this.emergencyInfoRepository = emergencyInfoRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.skillsRepository = skillsRepository;
        this.projectRepository = projectRepository;
        this.idCardIssueRepository = idCardIssueRepository;
        this.httpSession = httpSession;
        this.employeeTargetHoursRepository = employeeTargetHoursRepository;
        this.leaveRepository = leaveRepository;
        this.documentRequestRepository = documentRequestRepository;
    }


    @Override
    @Transactional
    public ResponseEntity<Map<String, String>> addEmployee(EmployeeRequestDto dto,String filepath) {

        EmployeeBasicInfo empInp = new EmployeeBasicInfo(
                dto.getMaritalStatus(),
                dto.getEmployeeId(), // Ensure this is NOT NULL in your JSON
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getGender(),
                 dto.getDob(),
                dto.getPersonalEmail(),
                dto.getPhone(),
                dto.getBloodGroup()
        );
        if (dto.getEmployeeId() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "Employee Added Successfully"));
        }
        // Save parent first to establish the ID in the persistence context
        EmployeeBasicInfo savedEmp = basicInfoRepository.save(empInp);


        // 2. Create the Children
        EmployeeJoiningInfo joining = new EmployeeJoiningInfo(
                dto.getEmploymentStatus(), dto.getDoj(),
                dto.getConfirmationDate(), dto.getWorkEmail(),savedEmp,basicInfoRepository.findEmployeeBasicInfoByEID(dto.getManager())
        );

        joining.setEmployeeBasicInfo(savedEmp); // @MapsId will copy the ID from savedEmp

        EmployeeDesignationInfo designationInfo =new EmployeeDesignationInfo();
        designationInfo.setDepartment(departmentRepository.findById(dto.getDepartment()).orElse(null));
        designationInfo.setDesignation(dto.getDesignation());
        designationInfo.setEmployeeBasicInfo(savedEmp); // @MapsId will copy the ID from savedEmp

        int baseTarget=0,extendedTarget=0;
        switch(dto.getDesignation()){
            case "Trainee":
            case "GIS Trainee": baseTarget=110;
                                extendedTarget=110;
                                 break;
            case "Analyst":
            case "GIS Analayst" :baseTarget=160;
                                 extendedTarget=184;
                                break;
            case "Associate":
            case "GIS Associate": baseTarget=140;
                                  extendedTarget=160;
                                  break;
            case "Senior Associate" :
            case "Team Lead": baseTarget=184;
                              extendedTarget=200;
                              break;
            default : baseTarget=140;
                      extendedTarget=160;
            
        }
        EmployeeTargetHours targetHours=new EmployeeTargetHours();
        targetHours.setEmployee(savedEmp);
        targetHours.setExtendedTarget(BigDecimal.valueOf(extendedTarget));
        targetHours.setTargetHours(BigDecimal.valueOf(baseTarget));
        targetHours.setMonth(AddonServ.monthYearFormatter(currentDate));
        targetHours.setMonthNum(LocalDate.now().getMonthValue());
        targetHours.setYear(LocalDate.now().getYear());
        employeeTargetHoursRepository.save(targetHours);


        EmployeeBankInfo bankInfo = new EmployeeBankInfo(dto.getPaymentMode(), dto.getBankName(), dto.getBankBranch(), dto.getAccountNo(), null,null);
        bankInfo.setEmployeeBasicInfo(savedEmp);

        EmployeeEducationInfo educationInfo = new EmployeeEducationInfo(null, dto.getCollege(), dto.getGpa(), dto.getFieldOfStudy(), dto.getYearOfPassing(),null);
        educationInfo.setEmployeeBasicInfo(savedEmp);

        EmployeeStatutoryInfo employeeStatutoryInfo = new EmployeeStatutoryInfo(dto.getAadhar(), dto.getPan(), dto.getUan(), dto.getPfNo(), dto.getPfStart(), dto.getEsiNo(), dto.getOnsurity(), null);
        employeeStatutoryInfo.setEmployeeBasicInfo(savedEmp);

        EmployeeEmergencyContactInfo employeeEmergencyContactInfo = new EmployeeEmergencyContactInfo(dto.getEmergencyName(), dto.getEmergencyPhone(), dto.getEmergencyRelation(), null);
        employeeEmergencyContactInfo.setEmployeeBasicInfo(savedEmp);
        if(filepath.length()>0){
            Images image=new Images();
            image.setEmployeeBasicInfo(savedEmp);
            image.setImage(filepath);

            imageRepository.save(image);
        }
        String employeeName = dto.getLastName() != null ? dto.getFirstName() + " " + dto.getLastName() : dto.getFirstName();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("Pavan@9662");
        User user = new User();
        user.setUsername(employeeName);
        user.setPassword(password);
        user.setRole(dto.getAccessLevel());
        user.setEmail(dto.getPersonalEmail());
        user.setEmployeeBasicInfo(savedEmp);
        // 3. Save Children
        joiningInfoRepository.save(joining);
        designationInfoRepository.save(designationInfo);
        bankInfoRepository.save(bankInfo);
        educationInfoRepository.save(educationInfo);
        statutoryInfoRepository.save(employeeStatutoryInfo);
        emergencyInfoRepository.save(employeeEmergencyContactInfo);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("status", "Employee Added Successfully"));
    }   


    @Override
    public List<EmployeeResponseDto> fetchEmployees() {
        return basicInfoRepository.fetchAllEmployees();
    }

    @Override
    public StatsDto getStats() {
        return basicInfoRepository.fetchAggregates();
    }

    public List<ResourceDto> getResources(){
List<ResourceDto> response= new ArrayList<>();
List<ResourceRespnseDto> res=skillsRepository.getResources();
        for (ResourceRespnseDto r : res) {
            double dvdr=((r.getTargetHours()/AddonServ.noofdaysinmonth(AddonServ.monthYearFormatter(LocalDate.now())))* AddonServ.dateNumExtract(currentDate))/100;
            double util=Math.round(r.getHours().doubleValue() / dvdr);
            List<SkillsResponse> skills=skillsRepository.getSkillHashMap(r.getEID());
            List<ActiveAllotments> projects =projectRepository.getActiveProjects(r.getEID());
            String status =projects.size()>0 && r.getStatus().equals("Available") ? "Allocated" : r.getStatus();
            ResourceDto resu=new ResourceDto(r.getEID(),r.getDepartmentShortName(),r.getDesignation(),r.getFirstName(),r.getLastName(),skills,projects,status,r.getImage(),util);
            response.add(resu);
        }
return response;
    }

    public ResponseEntity<Map<String, String>> issueIdCard(String eid){
        Map<String,String> response=new HashMap<>();
        IdCardIssue id=idCardIssueRepository.findByEid(eid).orElse(null);
        if(id!=null){
            id.setIdIssued(1);
            id.setIssuedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
            id.setIssuedDate(currentDate);
            idCardIssueRepository.save(id);
            response.put("status", "success");
            response.put("message", "ID Card Issued");
        }
        else{
                        response.put("status", "error");
                response.put("message", "Unable to issue ID Card");
        }
        return ResponseEntity.ok(response);
    }
    @Transactional
    public ResponseEntity<Map<String, String>> processManualSeparation(String eid,
    String separationType,
    LocalDate lastDay,
    String reason,
    String employeeRemarks,
    String hrRemarks,
    int rehireEligible){
        Map<String ,String> response=new HashMap<>();
        try{
        Separation sep=new Separation();
        sep.setAppliedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
        sep.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute(eid)));
        sep.setAdminRemarks(hrRemarks);
        sep.setDescription(employeeRemarks);
        sep.setReasonforLeaving(reason);
        sep.setApprovedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
        sep.setApprovedOn(LocalDate.now());
        sep.setStatus(1);

        separationRepository.save(sep);

        EmployeeBasicInfo emp=basicInfoRepository.findEmployeeBasicInfoByEID(eid);
        User user=userRepository.findByEmployeeBasicInfo_EID(eid);
        if(emp!=null && user != null){
            emp.setEmploymentStatus(0);
            user.setStatus(0);
            basicInfoRepository.save(emp);
            userRepository.save(user);
        }
                response.put("status","success");
        response.put("message","Separation Completed");
    }catch(Exception e){
        response.put("status","success");
        response.put("message",e.getMessage());
    }
        return ResponseEntity.ok(response);
    }

    public List<AvailableUsers> getAvailableUsers(String eid){
        return basicInfoRepository.getAvailableUsers(eid);
    }


    public List<LeaveAvailedDto> getLeaveAvailedDtos(){
        List<LeaveAvailedDto> response=new ArrayList<>();
    
    List<LeaveAvailedProj> resp=leaveRepository.getAvailedReport();
        for (LeaveAvailedProj dto : resp) {
            List<LeaveAvailedProjMonth> monthWiseData=leaveRepository.getLeaveAvailedByMonthForYear(dto.getYear(), dto.getEID());
            LeaveAvailedDto leaveAvailed=new LeaveAvailedDto();
            leaveAvailed.setEmployeeName(dto.getEmployeeName());
            leaveAvailed.setEID(dto.getEID());
            leaveAvailed.setYear(dto.getYear());
            leaveAvailed.setClAvailed(dto.getClAvailed());
            leaveAvailed.setSlAvailed(dto.getSlAvailed());
            leaveAvailed.setElAvailed(dto.getElAvailed());
            leaveAvailed.setCoAvailed(dto.getCoAvailed());
            leaveAvailed.setRhAvailed(dto.getRhAvailed());
            leaveAvailed.setMonthData(monthWiseData);

            response.add(leaveAvailed);
        }

        System.out.println(resp.toArray().toString());
        return  response;
    }

    public Map<String,String> documentRequestService(String documentType,String remarks){
        Map<String,String> response=new HashMap<>();
        DocumentRequest documentRequest=new DocumentRequest();
        documentRequest.setReason(remarks);
        documentRequest.setRequestedOn(LocalDateTime.now());
        documentRequest.setRequester(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
        documentRequestRepository.save(documentRequest);
        response.put("status", "success");
        response.put("message", "Document Requested Successfully");
        return response;
    }

    public  List<DocuRequestProjection> getDocumentProjections(){
        return documentRequestRepository.getMyDocRequestProjections((String) httpSession.getAttribute("eid"));
    }
}




