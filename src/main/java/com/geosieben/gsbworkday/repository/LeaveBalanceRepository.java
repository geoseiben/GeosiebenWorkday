package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.geosieben.gsbworkday.dto.LeaveBalanceReposnseDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance,String> {
    @Query("SELECT new com.geosieben.gsbworkday.dto.LeaveBalanceReposnseDto(" +
            "e.EID, CONCAT(e.firstName, ' ', e.lastName), " +
            "lb.casualleaves, lb.sickleaves, lb.restrictedholidays, lb.earnedleaves) " +
            "FROM LeaveBalance lb JOIN lb.employeeBasicInfo e " +
            "WHERE e.EID = :eid")
    LeaveBalanceReposnseDto findLeaveBalanceByEid(@Param("eid") String eid);

LeaveBalance findByEmployeeBasicInfo_EID(String eid);
}
