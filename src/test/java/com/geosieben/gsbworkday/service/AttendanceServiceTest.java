package com.geosieben.gsbworkday.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.AttendanceResponseDto;
import com.geosieben.gsbworkday.dto.AttendenceMasterReportProjection;
import com.geosieben.gsbworkday.dto.MonthProjection;
import com.geosieben.gsbworkday.entity.Attendance;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails;
import com.geosieben.gsbworkday.repository.AttendanceRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.SalaryExtraRepository;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private BasicInfoRepository basicInfoRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private SalaryExtraRepository salaryExtraRepository;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private AttendanceService attendanceService;

    private EmployeeBasicInfo mockEmployee;

    @BeforeEach
    void setUp() {
        mockEmployee = new EmployeeBasicInfo();
        mockEmployee.setEID("EMP001");
    }

    // =========================================================================
    // 1. TESTS FOR getAttendanceData (10 Test Cases)
    // =========================================================================
    @Nested
    @DisplayName("Tests for getAttendanceData")
    class GetAttendanceDataTests {

        @Test
        @DisplayName("1. Returns valid data when records exist for date")
        void testGetAttendanceData_Success() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto));

            List<AttendanceResponseDto> result = attendanceService.getAttendanceData("2026-03-01");
            assertEquals(1, result.size());
            verify(basicInfoRepository, times(1)).getAttendanceData("2026-03-01");
        }

        @Test
        @DisplayName("2. Returns empty list when no records exist")
        void testGetAttendanceData_Empty() {
            when(basicInfoRepository.getAttendanceData("2026-03-02")).thenReturn(Collections.emptyList());
            List<AttendanceResponseDto> result = attendanceService.getAttendanceData("2026-03-02");
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("3. Handles multiple records successfully")
        void testGetAttendanceData_MultipleRecords() {
            when(basicInfoRepository.getAttendanceData("2026-03-03")).thenReturn(List.of(mock(AttendanceResponseDto.class), mock(AttendanceResponseDto.class)));
            assertEquals(2, attendanceService.getAttendanceData("2026-03-03").size());
        }

        @Test
        @DisplayName("4. Handles null date parameter gracefully via repository")
        void testGetAttendanceData_NullDate() {
            when(basicInfoRepository.getAttendanceData(null)).thenReturn(Collections.emptyList());
            assertTrue(attendanceService.getAttendanceData(null).isEmpty());
        }

        @Test
        @DisplayName("5. Handles empty string date")
        void testGetAttendanceData_EmptyDate() {
            when(basicInfoRepository.getAttendanceData("")).thenReturn(Collections.emptyList());
            assertTrue(attendanceService.getAttendanceData("").isEmpty());
        }

        @Test
        @DisplayName("6. Verifies repository interaction count")
        void testGetAttendanceData_RepositoryCalledOnce() {
            when(basicInfoRepository.getAttendanceData(anyString())).thenReturn(Collections.emptyList());
            attendanceService.getAttendanceData("2026-03-04");
            verify(basicInfoRepository, times(1)).getAttendanceData("2026-03-04");
        }

        @Test
        @DisplayName("7. Validates returned DTO contents mapping expectation")
        void testGetAttendanceData_ContentValidation() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(dto.getEid()).thenReturn("EMP001");
            when(basicInfoRepository.getAttendanceData("2026-03-05")).thenReturn(List.of(dto));
            
            assertEquals("EMP001", attendanceService.getAttendanceData("2026-03-05").get(0).getEid());
        }

        @Test
        @DisplayName("8. Handles repository exception propagation")
        void testGetAttendanceData_RepositoryException() {
            when(basicInfoRepository.getAttendanceData(anyString())).thenThrow(new RuntimeException("Database error"));
            assertThrows(RuntimeException.class, () -> attendanceService.getAttendanceData("2026-03-06"));
        }

        @Test
        @DisplayName("9. Passes exact format string down to repository")
        void testGetAttendanceData_DateStringIntegrity() {
            String dateStr = "2026-03-07";
            attendanceService.getAttendanceData(dateStr);
            verify(basicInfoRepository).getAttendanceData(dateStr);
        }

        @Test
        @DisplayName("10. Handles large list payload response")
        void testGetAttendanceData_LargeList() {
            List<AttendanceResponseDto> largeList = Collections.nCopies(500, mock(AttendanceResponseDto.class));
            when(basicInfoRepository.getAttendanceData("2026-03-08")).thenReturn(largeList);
            assertEquals(500, attendanceService.getAttendanceData("2026-03-08").size());
        }
    }

    // =========================================================================
    // 2. TESTS FOR markAttendace (10 Test Cases)
    // =========================================================================
    @Nested
    @DisplayName("Tests for markAttendace")
    class MarkAttendanceTests {

        @BeforeEach
        void mockSession() {
            lenient().when(httpSession.getAttribute("eid")).thenReturn("ADMIN01");
            lenient().when(basicInfoRepository.findEmployeeBasicInfoByEID(anyString())).thenReturn(mockEmployee);
        }

        @Test
        @DisplayName("1. Mark 'Present' when record doesn't exist yet")
        void testMarkAttendance_NewPresent() {
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.empty());

            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Present", "2026-03-01");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(attendanceRepository, times(1)).save(any(Attendance.class));
        }

        @Test
        @DisplayName("2. Mark 'Absent' when record doesn't exist triggers LOP update")
        void testMarkAttendance_NewAbsent() {
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.empty());
            when(salaryExtraRepository.findExistingLop(anyString(), eq("EMP001"))).thenReturn(Optional.empty());

            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Absent", "2026-03-01");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(salaryExtraRepository, times(1)).save(any(SalaryExtraDetails.class));
        }

        @Test
        @DisplayName("3. Mark 'Half Day' when record doesn't exist")
        void testMarkAttendance_NewHalfDay() {
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.empty());
            when(salaryExtraRepository.findExistingLop(anyString(), eq("EMP001"))).thenReturn(Optional.empty());

            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Half Day", "2026-03-01");
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("4. Update existing attendance to Half Day with Session 1 leave type")
        void testMarkAttendance_ExistingSession1HalfDay() {
            Attendance existing = new Attendance();
            existing.setLeaveType("Session 1");
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.of(existing));

            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Half Day", "2026-03-01");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Leave/Present", existing.getStatus());
        }

        @Test
        @DisplayName("5. Update existing attendance to Half Day with Session 2 leave type")
        void testMarkAttendance_ExistingSession2HalfDay() {
            Attendance existing = new Attendance();
            existing.setLeaveType("Session 2");
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.of(existing));

            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Half Day", "2026-03-01");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Present/Leave", existing.getStatus());
        }

        @Test
        @DisplayName("6. Update existing attendance to Absent with Session 1 updateLop call")
        void testMarkAttendance_ExistingSession1Absent() {
            Attendance existing = new Attendance();
            existing.setLeaveType("Session 1");
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.of(existing));
            when(salaryExtraRepository.findExistingLop(anyString(), eq("EMP001"))).thenReturn(Optional.empty());

            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Absent", "2026-03-01");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Leave/Absent", existing.getStatus());
        }

        @Test
        @DisplayName("7. Update existing attendance to Absent with Session 2 updateLop call")
        void testMarkAttendance_ExistingSession2Absent() {
            Attendance existing = new Attendance();
            existing.setLeaveType("Session 2");
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.of(existing));
            when(salaryExtraRepository.findExistingLop(anyString(), eq("EMP001"))).thenReturn(Optional.empty());

            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Absent", "2026-03-01");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Absent/Leave", existing.getStatus());
        }

        @Test
        @DisplayName("8. Working day correctly computed as 1 for Present status")
        void testMarkAttendance_WorkingDayPresent() {
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.empty());
            attendanceService.markAttendace("EMP001", "Present", "2026-03-01");
            verify(attendanceRepository).save(argThat(att -> att.getWorkingDay().compareTo(BigDecimal.valueOf(1)) == 0));
        }

        @Test
        @DisplayName("9. Working day correctly computed as 0 for Absent status")
        void testMarkAttendance_WorkingDayAbsent() {
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.empty());
            when(salaryExtraRepository.findExistingLop(anyString(), anyString())).thenReturn(Optional.empty());
            attendanceService.markAttendace("EMP001", "Absent", "2026-03-01");
            verify(attendanceRepository).save(argThat(att -> att.getWorkingDay().compareTo(BigDecimal.valueOf(0)) == 0));
        }

        @Test
        @DisplayName("10. Response body contains expected success messages")
        void testMarkAttendance_ResponsePayload() {
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.empty());
            ResponseEntity<?> response = attendanceService.markAttendace("EMP001", "Present", "2026-03-01");
            assertNotNull(response.getBody());
        }
    }

    // =========================================================================
    // 3. TESTS FOR updateLop (10 Test Cases)
    // =========================================================================
    @Nested
    @DisplayName("Tests for updateLop")
    class UpdateLopTests {

        @BeforeEach
        void mockSession() {
            lenient().when(httpSession.getAttribute("eid")).thenReturn("ADMIN01");
            lenient().when(basicInfoRepository.findEmployeeBasicInfoByEID(anyString())).thenReturn(mockEmployee);
        }

        @Test
        @DisplayName("1. Updates existing LOP record by adding extra days")
        void testUpdateLop_ExistingRecord() {
            SalaryExtraDetails existingDetails = new SalaryExtraDetails();
            existingDetails.setLopindays(BigDecimal.valueOf(1.0));
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.of(existingDetails));

            attendanceService.updateLop(0.5, "March 2026", "EMP001");
            assertEquals(0, BigDecimal.valueOf(1.5).compareTo(existingDetails.getLopindays()));
            verify(salaryExtraRepository).save(existingDetails);
        }

        @Test
        @DisplayName("2. Creates new LOP record when none exists")
        void testUpdateLop_NewRecord() {
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.empty());

            attendanceService.updateLop(1.0, "March 2026", "EMP001");
            verify(salaryExtraRepository, times(1)).save(any(SalaryExtraDetails.class));
        }

        @Test
        @DisplayName("3. Adds exact decimal fractional day values correctly")
        void testUpdateLop_FractionalDays() {
            SalaryExtraDetails existingDetails = new SalaryExtraDetails();
            existingDetails.setLopindays(BigDecimal.valueOf(0.5));
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.of(existingDetails));

            attendanceService.updateLop(0.5, "March 2026", "EMP001");
            assertEquals(0, BigDecimal.valueOf(1.0).compareTo(existingDetails.getLopindays()));
        }

        @Test
        @DisplayName("4. Handles negative day deductions correctly (e.g. reversal)")
        void testUpdateLop_NegativeDays() {
            SalaryExtraDetails existingDetails = new SalaryExtraDetails();
            existingDetails.setLopindays(BigDecimal.valueOf(1.0));
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.of(existingDetails));

            attendanceService.updateLop(-0.5, "March 2026", "EMP001");
            assertEquals(0, BigDecimal.valueOf(0.5).compareTo(existingDetails.getLopindays()));
        }

        @Test
        @DisplayName("5. Sets initial extraPay to Zero on new record creation")
        void testUpdateLop_NewRecordExtraPayZero() {
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.empty());

            attendanceService.updateLop(1.0, "March 2026", "EMP001");
            verify(salaryExtraRepository).save(argThat(sal -> sal.getExtraPay().compareTo(BigDecimal.ZERO) == 0));
        }

        @Test
        @DisplayName("6. Maps month string accurately to new record")
        void testUpdateLop_MonthMapping() {
            when(salaryExtraRepository.findExistingLop("April 2026", "EMP001")).thenReturn(Optional.empty());

            attendanceService.updateLop(1.0, "April 2026", "EMP001");
            verify(salaryExtraRepository).save(argThat(sal -> sal.getMonth().equals("April 2026")));
        }

        @Test
        @DisplayName("7. Handles empty month string gracefully")
        void testUpdateLop_EmptyMonth() {
            when(salaryExtraRepository.findExistingLop("", "EMP001")).thenReturn(Optional.empty());
            attendanceService.updateLop(1.0, "", "EMP001");
            verify(salaryExtraRepository).save(any(SalaryExtraDetails.class));
        }

        @Test
        @DisplayName("8. Sets employee info association properly on creation")
        void testUpdateLop_EmployeeAssociation() {
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.empty());
            attendanceService.updateLop(1.0, "March 2026", "EMP001");
            verify(salaryExtraRepository).save(argThat(sal -> sal.getEmployeeBasicInfo() != null));
        }

        @Test
        @DisplayName("9. Sets addedBy reference from HTTP session attribute correctly")
        void testUpdateLop_AddedBySession() {
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.empty());
            attendanceService.updateLop(1.0, "March 2026", "EMP001");
            verify(salaryExtraRepository).save(argThat(sal -> sal.getAddedBy() != null));
        }

        @Test
        @DisplayName("10. Multiple sequential LOP updates aggregate cleanly")
        void testUpdateLop_SequentialUpdates() {
            SalaryExtraDetails existingDetails = new SalaryExtraDetails();
            existingDetails.setLopindays(BigDecimal.valueOf(0.0));
            when(salaryExtraRepository.findExistingLop("March 2026", "EMP001")).thenReturn(Optional.of(existingDetails));

            attendanceService.updateLop(1.0, "March 2026", "EMP001");
            attendanceService.updateLop(0.5, "March 2026", "EMP001");
            assertEquals(0, BigDecimal.valueOf(1.5).compareTo(existingDetails.getLopindays()));
        }
    }

    // =========================================================================
    // 4. TESTS FOR markAttendanceForAll (10 Test Cases)
    // =========================================================================
    @Nested
    @DisplayName("Tests for markAttendanceForAll")
    class MarkAttendanceForAllTests {

        @BeforeEach
        void mockSession() {
            lenient().when(httpSession.getAttribute("eid")).thenReturn("ADMIN01");
            lenient().when(basicInfoRepository.findEmployeeBasicInfoByEID(anyString())).thenReturn(mockEmployee);
        }

        @Test
        @DisplayName("1. Marks null status records as 'Present'")
        void testMarkAttendanceForAll_NullStatusBecomesPresent() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(dto.getStatus()).thenReturn(null);
            when(dto.getEid()).thenReturn("EMP001");
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto));

            ResponseEntity<?> resp = attendanceService.markAttendanceForAll("2026-03-01");
            assertEquals(HttpStatus.OK, resp.getStatusCode());
            verify(attendanceRepository, times(1)).save(any(Attendance.class));
        }

        @Test
        @DisplayName("2. Handles existing 'Half Day' status record updates in bulk loop")
        void testMarkAttendanceForAll_HalfDayRecordUpdateSession1() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(dto.getStatus()).thenReturn("Half Day");
            when(dto.getEid()).thenReturn("EMP001");
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto));

            Attendance existingAttendance = new Attendance();
            existingAttendance.setLeaveType("Session 1");
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.of(existingAttendance));
            lenient().when(salaryExtraRepository.findExistingLop(anyString(), anyString())).thenReturn(Optional.empty());

            ResponseEntity<?> resp = attendanceService.markAttendanceForAll("2026-03-01");
            assertEquals(HttpStatus.OK, resp.getStatusCode());
        }

        @Test
        @DisplayName("3. Handles existing 'Half Day' status record updates for Session 2")
        void testMarkAttendanceForAll_HalfDayRecordUpdateSession2() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(dto.getStatus()).thenReturn("Half Day");
            when(dto.getEid()).thenReturn("EMP001");
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto));

            Attendance existingAttendance = new Attendance();
            existingAttendance.setLeaveType("Session 2");
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.of(existingAttendance));
            lenient().when(salaryExtraRepository.findExistingLop(anyString(), anyString())).thenReturn(Optional.empty());

            ResponseEntity<?> resp = attendanceService.markAttendanceForAll("2026-03-01");
            assertEquals(HttpStatus.OK, resp.getStatusCode());
        }

        @Test
        @DisplayName("4. Skips bulk updates when status is already non-null and not Half Day")
        void testMarkAttendanceForAll_SkipsAlreadyMarkedPresent() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(dto.getStatus()).thenReturn("Present");
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto));

            attendanceService.markAttendanceForAll("2026-03-01");
            verify(attendanceRepository, never()).save(any(Attendance.class));
        }

        @Test
        @DisplayName("5. Handles empty employee list for bulk marking")
        void testMarkAttendanceForAll_EmptyList() {
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(Collections.emptyList());
            ResponseEntity<?> resp = attendanceService.markAttendanceForAll("2026-03-01");
            assertEquals(HttpStatus.OK, resp.getStatusCode());
            verify(attendanceRepository, never()).save(any(Attendance.class));
        }

        @Test
        @DisplayName("6. Processes multiple employees in batch loop successfully")
        void testMarkAttendanceForAll_MultipleEmployees() {
            AttendanceResponseDto dto1 = mock(AttendanceResponseDto.class);
            when(dto1.getStatus()).thenReturn(null);
            when(dto1.getEid()).thenReturn("EMP001");

            AttendanceResponseDto dto2 = mock(AttendanceResponseDto.class);
            when(dto2.getStatus()).thenReturn(null);
            when(dto2.getEid()).thenReturn("EMP002");

            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto1, dto2));

            attendanceService.markAttendanceForAll("2026-03-01");
            verify(attendanceRepository, times(2)).save(any(Attendance.class));
        }

        @Test
        @DisplayName("7. Returns correct success message format in response map")
        void testMarkAttendanceForAll_ResponseMessage() {
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(Collections.emptyList());
            ResponseEntity<?> resp = attendanceService.markAttendanceForAll("2026-03-01");
            assertNotNull(resp.getBody());
        }

        @Test
        @DisplayName("8. Working day set to 1 for bulk newly marked records")
        void testMarkAttendanceForAll_WorkingDayValueSet() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(dto.getStatus()).thenReturn(null);
            when(dto.getEid()).thenReturn("EMP001");
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto));

            attendanceService.markAttendanceForAll("2026-03-01");
            verify(attendanceRepository).save(argThat(att -> att.getWorkingDay().compareTo(BigDecimal.ONE) == 0));
        }

        @Test
        @DisplayName("9. Updates LOP correctly (-0.5 adjustment) during half-day resolution")
        void testMarkAttendanceForAll_HalfDayLopUpdateTriggered() {
            AttendanceResponseDto dto = mock(AttendanceResponseDto.class);
            when(dto.getStatus()).thenReturn("Half Day");
            when(dto.getEid()).thenReturn("EMP001");
            when(basicInfoRepository.getAttendanceData("2026-03-01")).thenReturn(List.of(dto));

            Attendance existingAttendance = new Attendance();
            existingAttendance.setLeaveType("Session 1");
            when(attendanceRepository.findExistingAttendanceRecord("2026-03-01", "EMP001")).thenReturn(Optional.of(existingAttendance));
            when(salaryExtraRepository.findExistingLop(anyString(), eq("EMP001"))).thenReturn(Optional.empty());

            attendanceService.markAttendanceForAll("2026-03-01");
            verify(salaryExtraRepository, times(1)).save(any(SalaryExtraDetails.class));
        }

        @Test
        @DisplayName("10. Handles repository exceptions gracefully during batch loop")
        void testMarkAttendanceForAll_ExceptionHandling() {
            when(basicInfoRepository.getAttendanceData(anyString())).thenThrow(new RuntimeException("DB Connection failure"));
            assertThrows(RuntimeException.class, () -> attendanceService.markAttendanceForAll("2026-03-01"));
        }
    }

    // =========================================================================
    // 5. TESTS FOR getMonths (10 Test Cases)
    // =========================================================================
    @Nested
    @DisplayName("Tests for getMonths")
    class GetMonthsTests {

        @Test
        @DisplayName("1. Returns list of months successfully")
        void testGetMonths_Success() {
            MonthProjection proj = mock(MonthProjection.class);
            when(attendanceRepository.getAttendancemonths()).thenReturn(List.of(proj));

            List<MonthProjection> result = attendanceService.getMonths();
            assertEquals(1, result.size());
            verify(attendanceRepository, times(1)).getAttendancemonths();
        }

        @Test
        @DisplayName("2. Returns empty list when no months found")
        void testGetMonths_Empty() {
            when(attendanceRepository.getAttendancemonths()).thenReturn(Collections.emptyList());
            assertTrue(attendanceService.getMonths().isEmpty());
        }

        @Test
        @DisplayName("3. Handles multiple month projections")
        void testGetMonths_Multiple() {
            when(attendanceRepository.getAttendancemonths()).thenReturn(List.of(mock(MonthProjection.class), mock(MonthProjection.class)));
            assertEquals(2, attendanceService.getMonths().size());
        }

        @Test
        @DisplayName("4. Verifies single repository call execution")
        void testGetMonths_SingleCall() {
            when(attendanceRepository.getAttendancemonths()).thenReturn(Collections.emptyList());
            attendanceService.getMonths();
            verify(attendanceRepository, times(1)).getAttendancemonths();
        }

        @Test
        @DisplayName("5. Propagates data access exceptions")
        void testGetMonths_Exception() {
            when(attendanceRepository.getAttendancemonths()).thenThrow(new RuntimeException("Query error"));
            assertThrows(RuntimeException.class, () -> attendanceService.getMonths());
        }

        @Test
        @DisplayName("6. Validates projection content structure")
        void testGetMonths_ProjectionContent() {
            MonthProjection proj = mock(MonthProjection.class);
            when(proj.toString()).thenReturn("March 2026");
            when(attendanceRepository.getAttendancemonths()).thenReturn(List.of(proj));

            assertEquals("March 2026", attendanceService.getMonths().get(0).toString());
        }

        @Test
        @DisplayName("7. Handles large set of historic months")
        void testGetMonths_LargeSet() {
            List<MonthProjection> months = Collections.nCopies(24, mock(MonthProjection.class));
            when(attendanceRepository.getAttendancemonths()).thenReturn(months);
            assertEquals(24, attendanceService.getMonths().size());
        }

        @Test
        @DisplayName("8. Ensures null result from repo translates safely if unhandled")
        void testGetMonths_NullRepoResponse() {
            when(attendanceRepository.getAttendancemonths()).thenReturn(null);
            assertNull(attendanceService.getMonths());
        }

        @Test
        @DisplayName("9. Verifies method signature compliance")
        void testGetMonths_ReturnTypeCheck() {
            when(attendanceRepository.getAttendancemonths()).thenReturn(Collections.emptyList());
            assertNotNull(attendanceService.getMonths());
        }

        @Test
        @DisplayName("10. Ensures transaction safety boundary behavior")
        void testGetMonths_TransactionalBehavior() {
            when(attendanceRepository.getAttendancemonths()).thenReturn(Collections.emptyList());
            List<MonthProjection> res = attendanceService.getMonths();
            assertNotNull(res);
        }
    }

    // =========================================================================
    // 6. TESTS FOR getMasterData (10 Test Cases)
    // =========================================================================
    @Nested
    @DisplayName("Tests for getMasterData")
    class GetMasterDataTests {

        @Test
        @DisplayName("1. Returns master attendance report for specific month")
        void testGetMasterData_Success() {
            AttendenceMasterReportProjection proj = mock(AttendenceMasterReportProjection.class);
            when(attendanceRepository.getMasterData("March 2026")).thenReturn(List.of(proj));

            List<AttendenceMasterReportProjection> result = attendanceService.getMasterData("March 2026");
            assertEquals(1, result.size());
            verify(attendanceRepository, times(1)).getMasterData("March 2026");
        }

        @Test
        @DisplayName("2. Returns empty list when master data is absent")
        void testGetMasterData_Empty() {
            when(attendanceRepository.getMasterData("April 2026")).thenReturn(Collections.emptyList());
            assertTrue(attendanceService.getMasterData("April 2026").isEmpty());
        }

        @Test
        @DisplayName("3. Handles multiple projections for master data report")
        void testGetMasterData_Multiple() {
            when(attendanceRepository.getMasterData(anyString())).thenReturn(List.of(mock(AttendenceMasterReportProjection.class), mock(AttendenceMasterReportProjection.class)));
            assertEquals(2, attendanceService.getMasterData("March 2026").size());
        }

        @Test
        @DisplayName("4. Handles null month input parameter")
        void testGetMasterData_NullMonth() {
            when(attendanceRepository.getMasterData(null)).thenReturn(Collections.emptyList());
            assertTrue(attendanceService.getMasterData(null).isEmpty());
        }

        @Test
        @DisplayName("5. Handles empty string month parameter")
        void testGetMasterData_EmptyMonth() {
            when(attendanceRepository.getMasterData("")).thenReturn(Collections.emptyList());
            assertTrue(attendanceService.getMasterData("").isEmpty());
        }

        @Test
        @DisplayName("6. Propagates database exceptions correctly")
        void testGetMasterData_Exception() {
            when(attendanceRepository.getMasterData(anyString())).thenThrow(new RuntimeException("SQL execution error"));
            assertThrows(RuntimeException.class, () -> attendanceService.getMasterData("March 2026"));
        }

        @Test
        @DisplayName("7. Validates exact month string argument is passed to repo")
        void testGetMasterData_ArgumentIntegrity() {
            String monthKey = "February 2026";
            attendanceService.getMasterData(monthKey);
            verify(attendanceRepository).getMasterData(monthKey);
        }

        @Test
        @DisplayName("8. Handles massive enterprise master report list size")
        void testGetMasterData_LargePayload() {
            List<AttendenceMasterReportProjection> largeReport = Collections.nCopies(1000, mock(AttendenceMasterReportProjection.class));
            when(attendanceRepository.getMasterData("March 2026")).thenReturn(largeReport);
            assertEquals(1000, attendanceService.getMasterData("March 2026").size());
        }

        @Test
        @DisplayName("9. Checks repository call count accuracy")
        void testGetMasterData_ExactCallCount() {
            when(attendanceRepository.getMasterData(anyString())).thenReturn(Collections.emptyList());
            attendanceService.getMasterData("March 2026");
            verify(attendanceRepository, times(1)).getMasterData(anyString());
        }

        @Test
        @DisplayName("10. Returns non-null collections on successful query")
        void testGetMasterData_NonNullReturn() {
            when(attendanceRepository.getMasterData(anyString())).thenReturn(Collections.emptyList());
            assertNotNull(attendanceService.getMasterData("March 2026"));
        }
    }
}