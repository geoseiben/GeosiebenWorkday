package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeStatutoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutoryInfoRepository extends JpaRepository<EmployeeStatutoryInfo,String> {
}
