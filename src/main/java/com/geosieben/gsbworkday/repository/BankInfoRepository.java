package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeBankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankInfoRepository extends JpaRepository<EmployeeBankInfo,String> {
}
