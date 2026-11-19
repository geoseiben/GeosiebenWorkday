package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geosieben.gsbworkday.dto.EmployeeTargetHoursProjection;
import com.geosieben.gsbworkday.entity.EmployeeTargetHours;

public interface EmployeeTargetHoursRepository extends JpaRepository<EmployeeTargetHours,Integer>{
    @Query(value = "SELECT t.id,concat(e.firstname,' ',e.lastname) as employeename,t.eid,t.month,"+
       " t.targethours,t.extendedTarget,j.designation,b.hours,coalesce(ex.hours,0) as extraHours FROM employeetargethours t "+
       " LEFT JOIN employee_basic_info e on e.eid=t.eid "+
       " left join (select eid ,date_format(date,'%b-%Y') as month,sum(hoursbooked) as hours from bookedhours group by eid,month) b on t.eid=b.eid and t.month=b.month "+
       " left join employee_designation_info j on j.eid=t.eid and j.lastDate is null "+
       " and j.lastdate is null "+
       " left join extrahours ex on ex.eid=e.eid and ex.month=t.month "+
       " group by t.id,employeename,t.eid,t.month,t.targetHours,ex.hours,j.designation,b.hours "+
       " order by employeename asc",nativeQuery=true)
        public List<EmployeeTargetHoursProjection> getEmployeeTargetReport();

}
