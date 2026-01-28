package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeStatutoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutoryInfoRepository extends JpaRepository<EmployeeStatutoryInfo,String> {
}
