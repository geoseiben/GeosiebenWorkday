package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.EmployeeTargetHoursProjection;
import com.geosieben.gsbworkday.dto.TargetHoursReportProjection;
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

    @Query(value = "SELECT t.id,concat(e.firstname,' ',e.lastname) as employeename,t.eid,t.month,"+
       " t.targethours,t.extendedTarget,j.designation,b.hours,coalesce(ex.hours,0) as extraHours FROM employeetargethours t "+
       " LEFT JOIN employee_basic_info e on e.eid=t.eid "+
       " left join (select eid ,date_format(date,'%b-%Y') as month,sum(hoursbooked) as hours from bookedhours group by eid,month) b on t.eid=b.eid and t.month=b.month "+
       " left join employee_designation_info j on j.eid=t.eid and j.lastDate is null "+
       " and j.lastdate is null "+
       " left join extrahours ex on ex.eid=e.eid and ex.month=t.month "+
       " where t.month=:month"+
       " group by t.id,employeename,t.eid,t.month,t.targetHours,ex.hours,j.designation,b.hours "+
       " order by employeename asc",nativeQuery=true)
        public List<EmployeeTargetHoursProjection> getCurrentMonthEmployeeTargetReport(@Param ("month") String month);

        
        @Query (value = "SELECT t.eid,concat(e.firstname,' ',e.lastname) as employeeName,t.month, "+
" t.targetHours,t.extendedTarget,coalesce(b.hours,0) as hours,d.designation, "+
" dt.departmentShortName,concat(m.firstname,' ',m.lastname) as manager,t.year,t.monthnum "+
"  from employeetargethours t  "+
" inner join employee_basic_info e on e.eid=t.eid "+
" left join employee_designation_info d on d.eid=e.eid "+
" left join employee_joining_info j on j.eid=t.eid "+ 
" left join employee_basic_info m on m.eid=j.reportingManager "+
" left join departments dt on d.department=dt.id "+
" left join ( "+
" select eid,date_format(date,'%b-%Y') as month, sum(hoursbooked) as hours "+
 " from bookedhours "+
 " where date_format(date,'%b-%Y')=date_format(current_date,'%b-%Y') "+
 " group by eid,month ) b on b.eid=t.eid and b.month=t.month",nativeQuery=true)
 public  List<TargetHoursReportProjection> getCurrentMonthTargetHoursReportProjections();

}
