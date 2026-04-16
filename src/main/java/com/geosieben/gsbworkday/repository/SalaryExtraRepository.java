package com.geosieben.gsbworkday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.SalaryExtraDetails;

@Repository
public interface SalaryExtraRepository extends JpaRepository<SalaryExtraDetails,Integer> {
    @Query(value = "select * from salaryextradetails where month=:month and EID=:eid",nativeQuery = true)
public Optional<SalaryExtraDetails> findExisting(@Param("month")String month,@Param("eid") String eid);
}
