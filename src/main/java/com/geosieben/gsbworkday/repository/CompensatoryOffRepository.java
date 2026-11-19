package com.geosieben.gsbworkday.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.CompOffHistoryProjection;
import com.geosieben.gsbworkday.dto.MyCompOffProjection;
import com.geosieben.gsbworkday.entity.CompensatoryOff;

public interface CompensatoryOffRepository extends JpaRepository<CompensatoryOff,Integer>{
@Query(value = "SELECT * FROM compensatoryoff where workedDate IS NULL AND compOffDate IS NOT NULL AND EID = :eid ",nativeQuery = true)
public Optional<CompensatoryOff> findPendingCompOffs(@Param("eid") String eid);

@Query(value = "SELECT * FROM compensatoryoff where workeddate=:workedDate and  EID=:eid",nativeQuery = true)
public Optional<CompensatoryOff> findExistingCompOff(@Param("workedDate") LocalDate workedDate,@Param("eid") String eid);

@Query(value = "SELECT id,date_format(workedDate,'%d-%b-%Y') as workedDate,date_format(compOffDate,'%d-%b-%Y') as compOffDate FROM compensatoryoff where eid=:eid",nativeQuery = true)
public List<MyCompOffProjection> getMyCompOff(@Param("eid") String eid);

@Query(value = "SELECT CONCAT(e.firstname, ' ', e.lastname) AS employeeName,"+
                " CONCAT(a.firstname, ' ', a.lastname) AS approvedBy,"+
                " DATE_FORMAT(c.workedDate, '%d-%b-%Y') AS workedDate,"+
                " DATE_FORMAT(c.compOffDate, '%d-%b-%Y') AS compOffDate,"+
                " c.createdOn, c.id"+
                " FROM compensatoryoff c"+ 
                " JOIN employee_basic_info e ON c.EID = e.EID "+  
                " LEFT JOIN employee_basic_info a ON a.eid = c.approvedBy "+
                " ORDER BY c.id DESC",nativeQuery = true)
                public List<CompOffHistoryProjection> getCompoffHistory();

}
