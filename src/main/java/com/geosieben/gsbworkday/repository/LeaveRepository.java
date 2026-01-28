package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.dto.PendingLeaveResponseDto;
import com.geosieben.gsbworkday.entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves,Integer> {
    @Query("SELECT new com.geosieben.gsbworkday.dto.PendingLeaveResponseDto(" +
            "l.id, CONCAT(e.firstName, ' ', e.lastName), e.EID, " +
            "l.leaveType, l.Description, l.fromDate, " +
            " l.toDate,l.NoofDays, l.fromsession, l.toSession) " +
            "FROM Leaves l JOIN l.employeeBasicInfo e where l.status=0")
    List<PendingLeaveResponseDto> findPendingLeaves();


}
