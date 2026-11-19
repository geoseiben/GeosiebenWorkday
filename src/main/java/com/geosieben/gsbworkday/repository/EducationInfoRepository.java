package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeEducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationInfoRepository extends JpaRepository<EmployeeEducationInfo,String> {
}
