package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeEmergencyContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyInfoRepository extends JpaRepository<EmployeeEmergencyContactInfo,String> {
}
