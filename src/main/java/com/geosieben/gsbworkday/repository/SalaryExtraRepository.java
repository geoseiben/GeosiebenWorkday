package com.geosieben.gsbworkday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.SalaryExtraDetails;

public interface SalaryExtraRepository extends JpaRepository<SalaryExtraDetails,Integer> {
    @Query(value = "select * from salaryextradetails where month=:month and EID=:eid",nativeQuery = true)
public Optional<SalaryExtraDetails> findExisting(@Param("month")String month,@Param("eid") String eid);
    @Query(value = "select * from salaryextradetails where month=:month and EID=:eid and lopindays> 0 ",nativeQuery = true)
public Optional<SalaryExtraDetails> findExistingLop(@Param("month")String month,@Param("eid") String eid);
}
