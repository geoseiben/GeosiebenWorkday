package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeDesignationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationInfoRepository extends JpaRepository<EmployeeDesignationInfo,String > {

}
