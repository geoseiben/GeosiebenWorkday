package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.ResourceRespnseDto;
import com.geosieben.gsbworkday.dto.SkillsResponse;
import com.geosieben.gsbworkday.entity.Skills;



public interface SkillsRepository extends JpaRepository<Skills,Integer>{
@Query(value = "select e.EID,e.firstName,e.lastName,d.designation,i.image,t.targetHours, "+
                " CASE WHEN b.hours IS NULL THEN  0 ELSE b.hours end as hours, "+
               "td.departmentShortName,case when l.id is null then 'Available' else 'On Leave' end as status "+
               "from employee_basic_info e "+
               "INNER JOIN employee_designation_info d on d.EID=e.EID "+
               "INNER JOIN departments td on d.department=td.id and td.id<>1 "+
               "LEFT JOIN employeetargethours t on e.EID=t.EID and t.month=date_format(current_date,'%b-%Y') "+
               "LEFT JOIN tblleaves l on e.eid=l.eid and l.status =1 and  current_date between l.FromDate and l.ToDate "+
               "LEFT JOIN ("+
               " SELECT EID,date_format(date,'%b-%Y') as month,sum(hoursbooked) as hours "+
               "FROM bookedhours "+
               " WHERE  date_format(date,'%b-%Y')=date_format(current_date,'%b-%Y') "+
               "GROUP  by EID, date_format(date,'%b-%Y') "+
             " ) b on b.EID=e.EID "+
               "LEFT JOIN images i on i.EID=e.EID WHERE e.employmentStatus=1 ",nativeQuery = true
)
public List<ResourceRespnseDto> getResources();

@Query(value="select skill,scale from skills where eid=:eid",nativeQuery =true)
public List<SkillsResponse> getSkillHashMap(@Param("eid") String eid);
}
