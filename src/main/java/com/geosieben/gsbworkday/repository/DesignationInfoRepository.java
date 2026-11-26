package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeDesignationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DesignationInfoRepository extends JpaRepository<EmployeeDesignationInfo,String > {
@Query (value = "SELECT * FROM employee_designation_info where eid=:eid",nativeQuery = true)
public  EmployeeDesignationInfo findDesignationInfo_ByEid(@Param ("eid") String eid);
}
