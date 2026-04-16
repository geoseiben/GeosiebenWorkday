package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.SalaryStructure;
@Repository
public interface SalaryStructureRepository extends JpaRepository<SalaryStructure,Integer>{
@Query(value="select * from salarystructure where date_format(createdAt,'%Y-%m')<=:month",nativeQuery=true )
public List<SalaryStructure> getEligibleSalaries(@Param("month") String month);
}
