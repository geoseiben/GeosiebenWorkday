package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeBankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankInfoRepository extends JpaRepository<EmployeeBankInfo,String> {
    @Query (value = "SELECT * FROM employee_bank_info where eid=:eid",nativeQuery = true)
    public  EmployeeBankInfo findEmployeeBankInfoByEid(@Param("eid") String eid);
}
