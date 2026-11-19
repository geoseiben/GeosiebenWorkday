package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeDesignationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationInfoRepository extends JpaRepository<EmployeeDesignationInfo,String > {

}
