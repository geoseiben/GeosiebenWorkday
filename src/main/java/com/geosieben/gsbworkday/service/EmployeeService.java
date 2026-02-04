package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.dto.EmployeeRequestDto;
import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.dto.StatsDto;
import com.geosieben.gsbworkday.entity.*;
import com.geosieben.gsbworkday.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService implements EmployeeServiceInterface {
    @Autowired
    private BasicInfoRepository basicInfoRepository;
    @Autowired
    private JoiningInfoRepository joiningInfoRepository;
    @Autowired
    private DesignationInfoRepository designationInfoRepository;
    @Autowired
    private BankInfoRepository bankInfoRepository;
    @Autowired
    private EducationInfoRepository educationInfoRepository;
    @Autowired
    private StatutoryInfoRepository statutoryInfoRepository;
    @Autowired
    private EmergencyInfoRepository emergencyInfoRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<Map<String, String>> addEmployee(EmployeeRequestDto dto) {

        EmployeeBasicInfo empInp = new EmployeeBasicInfo(
                dto.getMaritalStatus(),
                dto.getEmployeeId(), // Ensure this is NOT NULL in your JSON
                dto.getFirstName(),
                dto.getLastName(),
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
                dto.getConfirmationDate(), dto.getWorkEmail(), null
        );
        joining.setEmployeeBasicInfo(savedEmp); // @MapsId will copy the ID from savedEmp

        EmployeeDesignationInfo designationInfo = new EmployeeDesignationInfo(
                null, dto.getDesignation()
        );
        designationInfo.setEmployeeBasicInfo(savedEmp); // @MapsId will copy the ID from savedEmp

        EmployeeBankInfo bankInfo = new EmployeeBankInfo(dto.getPaymentMode(), dto.getBankName(), dto.getBankBranch(), dto.getAccountNo(), null);
        bankInfo.setEmployeeBasicInfo(savedEmp);

        EmployeeEducationInfo educationInfo = new EmployeeEducationInfo(null, dto.getCollege(), dto.getGpa(), dto.getFieldOfStudy(), dto.getYearOfPassing());
        educationInfo.setEmployeeBasicInfo(savedEmp);

        EmployeeStatutoryInfo employeeStatutoryInfo = new EmployeeStatutoryInfo(dto.getAadhar(), dto.getPan(), dto.getUan(), dto.getPfNo(), dto.getPfStart(), dto.getEsiNo(), dto.getOnsurity(), null);
        employeeStatutoryInfo.setEmployeeBasicInfo(savedEmp);

        EmployeeEmergencyContactInfo employeeEmergencyContactInfo = new EmployeeEmergencyContactInfo(dto.getEmergencyName(), dto.getEmergencyPhone(), dto.getEmergencyRelation(), null);
        employeeEmergencyContactInfo.setEmployeeBasicInfo(savedEmp);
        String employeeName = dto.getLastName() != null ? dto.getFirstName() + " " + dto.getLastName() : dto.getFirstName();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("Pavan@9662");
        User user = new User(employeeName, password, "user", dto.getPersonalEmail(), null);
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




}


