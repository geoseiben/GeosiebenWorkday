package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.TeamLeadTargetHours;

@Repository
public interface TlTargetHoursRepository extends JpaRepository<TeamLeadTargetHours,Integer>{
@Query(value = "select * from tltargethours where month =:month and monthnum=:monthnum and year=:year and tlid=:tlid",nativeQuery = true)
TeamLeadTargetHours findLeadTargetHoursForCurrentMonth(@Param("month") String month,@Param("monthnum") int monthNum,@Param("year") int year,@Param("tlid") String  tlid);
}
