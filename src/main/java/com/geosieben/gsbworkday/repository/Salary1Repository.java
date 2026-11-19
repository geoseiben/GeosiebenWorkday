package com.geosieben.gsbworkday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.SalaryDto;
import com.geosieben.gsbworkday.entity.Salary1;

public interface Salary1Repository extends JpaRepository<Salary1,Integer> {
    @Query(value="SELECT s.salaryId,ss.salid,s.month,s.EID,ss.designation,s.noofdays,"+
        "s.lopdays,s.payableDays,e.firstName,e.lastName,s.basic,s.conveyance,s.medicalAllowence,"+
        "s.statutoryBonus,s.otherAllowences,s.otherEarnings,s.pfEmployeeShare,s.pfEmployerShare ,"+
        "s.esiEmployeeShare,s.esiEmployerShare,s.pt,s.gross,s.netPayeble,st.pan,st.pfNo,st.uan,b.bankName,"+
        "b.accountNumber,j.dateOfJoining,s.iv ,ss.iv as ssiv "+
        "FROM salary1 s "+
        "INNER JOIN salarystructure1 ss on s.salid=ss.salid "+
        " INNER JOIN employee_basic_info e on e.eid=s.eid "+
        "INNER JOIN employee_joining_info j on j.eid=s.eid "+
        "INNER JOIN employee_statutory_info st ON st.eid=s.eid "+
        "INNER JOIN employee_bank_info b on b.eid=s.eid ",nativeQuery=true
    )
    public List<SalaryDto> getAllSalary();
    @Query(value = "select * from salary1 where EID=:eid and month=:month",nativeQuery = true)
    public Optional<Salary1> getSalaryByMonthEid(@Param("eid") String eid,@Param("month") String month);
}
    