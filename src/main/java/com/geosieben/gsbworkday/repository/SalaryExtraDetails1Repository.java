package com.geosieben.gsbworkday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.SalaryExtraDto;
import com.geosieben.gsbworkday.entity.SalaryExtraDetails1;

public interface SalaryExtraDetails1Repository extends JpaRepository<SalaryExtraDetails1,Integer>{
    @Query(value = "SELECT * FROM salaryextradetails1 where month=:month and EID=:eid",nativeQuery = true)
    Optional<SalaryExtraDetails1> findByMonthEid(@Param("month") String month,@Param("eid") String eid);

    @Query(value = "SELECT e.firstName,e.lastName,s.iv,s.id,s.EID,s.month,s.lopindays,s.extraPay,s.updatedOn from salaryextradetails1 s INNER JOIN employee_basic_info e on e.EID=s.EID ",nativeQuery = true)
    public List<SalaryExtraDto> findExtraEncrypted();

}
