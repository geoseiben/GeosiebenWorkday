    package com.geosieben.gsbworkday.service;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;

    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import com.geosieben.gsbworkday.addon.AddonServ;
    import com.geosieben.gsbworkday.dto.AttendanceResponseDto;
    import com.geosieben.gsbworkday.entity.Attendance;
    import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
    import com.geosieben.gsbworkday.repository.AttendanceRepository;
    import com.geosieben.gsbworkday.repository.BasicInfoRepository;
    import com.geosieben.gsbworkday.repository.SalaryExtraRepository;
    import com.geosieben.gsbworkday.dto.AttendenceMasterReportProjection;
    import com.geosieben.gsbworkday.dto.MonthProjection;

    import jakarta.servlet.http.HttpSession;
    import jakarta.transaction.Transactional;
    @Service
    public class AttendanceService implements AttendanceInterface {
        private BasicInfoRepository basicInfoRepository;
        private AttendanceRepository attendanceRepository;
        private SalaryExtraRepository salaryExtraRepository;
        private HttpSession httpSession;

        public AttendanceService(BasicInfoRepository basicInfoRepository, AttendanceRepository attendanceRepository,
                SalaryExtraRepository salaryExtraRepository, HttpSession httpSession) {
            this.basicInfoRepository = basicInfoRepository;
            this.attendanceRepository = attendanceRepository;
            this.salaryExtraRepository = salaryExtraRepository;
            this.httpSession = httpSession;
        }

        @Override

        public List<AttendanceResponseDto> getAttendanceData(String date) {
        return basicInfoRepository.getAttendanceData(date);
        }

        @Override
        @Transactional
        public ResponseEntity<?> markAttendace(String eid, String status, String date) {
            String aid=(String) httpSession.getAttribute("eid");
            Map<String,String> response=new HashMap<>();
        Optional<Attendance> existing=attendanceRepository.findExistingAttendanceRecord(date, eid);
        if(existing.isPresent()){
            Attendance at=existing.get();
            String sts=status;
            if(status.equals("Half Day") && at.getLeaveType().equals("Session 1")){
                sts="Leave/Present";
            }
            else if(status.equals("Half Day") && at.getLeaveType().equals("Session 2")){
                sts="Present/Leave";
        }
        else if(status.equals("Absent") && at.getLeaveType().equals("Session 1")){
                sts="Leave/Absent";
                updateLop(0.5, String.valueOf(AddonServ.monthYearFormatter(LocalDate.parse(date))), eid);
        }
        else if(status.equals("Absent") && at.getLeaveType().equals("Session 2")){
            sts="Absent/Leave";
            updateLop(0.5, String.valueOf(AddonServ.monthYearFormatter(LocalDate.parse(date))), eid);
        }
            double workingDay=status.equals("Present")? 1 :(status.equals("Absent")? 0:0.5 );

        at.setStatus(sts);
        at.setUpdatedBy(basicInfoRepository.findEmployeeBasicInfoByEID(aid));
        at.setUpdatedOn(LocalDateTime.now());
        at.setWorkingDay(BigDecimal.valueOf(workingDay));
        attendanceRepository.save(at);
        }
        
        else{
            double workingDay=status.equals("Present")? 1 :(status.equals("Absent")? 0:0.5 );
            Attendance attendance=new Attendance();
            attendance.setStatus(status);
            attendance.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
            attendance.setUpdatedBy(basicInfoRepository.findEmployeeBasicInfoByEID(aid));
            attendance.setLeaveDays(BigDecimal.valueOf(0)); 
            attendance.setWorkingDay(BigDecimal.valueOf(workingDay));   
            attendance.setDate(LocalDate.parse(date));
            attendanceRepository.save(attendance);

            if(status.equals("Absent")|| status.equals("Half Day")){
                double lopdays=status.equals("Absent")?1:0.5;
                        updateLop(lopdays, String.valueOf(AddonServ.monthYearFormatter(LocalDate.parse(date))), eid);



        }
        }
        response.put("status","success");
        response.put("mesage", "Attendance Marked");
        return ResponseEntity.ok(response);
        }

        public void updateLop(double days,String month,String eid){
            Optional<SalaryExtraDetails> lop=salaryExtraRepository.findExistingLop(month, eid);
            if(lop.isPresent()){
                SalaryExtraDetails extra=lop.get();
                extra.setLopindays(extra.getLopindays().add(BigDecimal.valueOf(days)));
                salaryExtraRepository.save(extra);
            }
            else{
                SalaryExtraDetails salExtra=new SalaryExtraDetails();
                salExtra.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
                salExtra.setLopindays(BigDecimal.valueOf(days));
                salExtra.setMonth(month);
                salExtra.setAddedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
                salExtra.setExtraPay(BigDecimal.ZERO);
                salaryExtraRepository.save(salExtra);    
            }
        }

        @Override
        public ResponseEntity<?> markAttendanceForAll(String date) {

            List<AttendanceResponseDto> attendanceData=basicInfoRepository.getAttendanceData(date);
            for(AttendanceResponseDto data:attendanceData){
                if(data.getStatus()==null){
                    Attendance attendance=new Attendance();
                    attendance.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(data.getEid()));
                    attendance.setUpdatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
                    attendance.setLeaveDays(BigDecimal.valueOf(0)); 
                    attendance.setWorkingDay(BigDecimal.valueOf(1));   
                    attendance.setDate(LocalDate.parse(date));
                    attendance.setStatus("Present");
                    attendanceRepository.save(attendance);
                }
                else if(data.getStatus().equals("Half Day")){
                    Optional<Attendance> existing=attendanceRepository.findExistingAttendanceRecord(date, data.getEid());
                    String newStatus="";
                    if(existing.isPresent()){
                                        Attendance at=existing.get();
                        if(at.getLeaveType().equals("Session 1")){
                            newStatus="Leave/Present";
                        }
                        else if(at.getLeaveType().equals("Session 2")){
                        newStatus="Present/Leave";
                        }
                        at.setMarkedAt(LocalDateTime.now());
                        at.setStatus("Present");
                        at.setStatus(newStatus);
                        at.setWorkingDay(BigDecimal.valueOf(1));
                        at.setUpdatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
                        at.setUpdatedOn(LocalDateTime.now());
                        attendanceRepository.save(at);
                        updateLop(-0.5, String.valueOf(AddonServ.monthYearFormatter(LocalDate.parse(date))), data.getEid());
                    }
                }
            }

            Map<String,String> response=new HashMap<>();
            response.put("status","success");
            response.put("mesage", "Attendance Marked for All");
            return ResponseEntity.ok(response);
        }
    public  List<MonthProjection> getMonths(){
        return attendanceRepository.getAttendancemonths();
    }
    public List<AttendenceMasterReportProjection> getMasterData(String month){
        return attendanceRepository.getMasterData(month);
    }
    }
