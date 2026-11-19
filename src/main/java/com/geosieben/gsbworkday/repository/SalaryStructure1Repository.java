package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.SalaryStructure1;

public interface SalaryStructure1Repository extends JpaRepository<SalaryStructure1,Integer>{
@Query(value="select * from salarystructure1 where date_format(createdAt,'%Y-%m')<=:month",nativeQuery=true )
public List<SalaryStructure1> getEligibleSalaries(@Param("month") String month);
}
