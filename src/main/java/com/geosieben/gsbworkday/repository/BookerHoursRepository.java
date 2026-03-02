package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.BookeHoursProjection;
import com.geosieben.gsbworkday.dto.MonthlyHoursProjection;
import com.geosieben.gsbworkday.dto.ProjectHoursProjection;
import com.geosieben.gsbworkday.entity.BookedHours;


public interface BookerHoursRepository extends JpaRepository<BookedHours,Integer>{
    @Query(value = "select * from bookedhours where EID=:eid and allotmentid=:allotmentid and date=current_date",nativeQuery = true)
    BookedHours findbydateAndAllotmentforToday(@Param("eid") String eid,@Param("allotmentid") int allotmentid );
    @Query(value = "select * from bookedhours where EID=:eid and allotmentid=:allotmentid order by id desc",nativeQuery = true)
    BookedHours findbyAndAllotmentforBookedSoFar(@Param("eid") String eid,@Param("allotmentid") int allotmentid );
@Query(value="SELECT e.firstName as firstName, e.lastName as lastName, " +
             "SUM(CASE WHEN b.type = 'Prod' THEN b.hoursbooked ELSE 0 END) as prodHours, " +
             "SUM(CASE WHEN b.type = 'QC' THEN b.hoursbooked ELSE 0 END) as qcHours, " +
             "SUM(CASE WHEN b.type = 'Delivery' THEN b.hoursbooked ELSE 0 END) as deliveryHours, " +
             "SUM(CASE WHEN b.type = 'Pilot' THEN b.hoursbooked ELSE 0 END) as pilotHours, " +
             "SUM(b.hoursbooked) as totalHours " +
             "FROM bookedhours b INNER JOIN employee_basic_info e ON e.eid = b.eid " +
             "GROUP BY e.firstName, e.lastName",
       nativeQuery = true)
List<BookeHoursProjection> getHoursByEmployeePivot();
@Query(value="SELECT p.allotmentName, " +
             "SUM(CASE WHEN b.type = 'Prod' THEN b.hoursbooked ELSE 0 END) as prodHours, " +
             "SUM(CASE WHEN b.type = 'QC' THEN b.hoursbooked ELSE 0 END) as qcHours, " +
             "SUM(CASE WHEN b.type = 'Delivery' THEN b.hoursbooked ELSE 0 END) as deliveryHours, " +
             "SUM(CASE WHEN b.type = 'Pilot' THEN b.hoursbooked ELSE 0 END) as pilotHours, " +
             "SUM(b.hoursbooked) as totalHours " +
             "FROM bookedhours b INNER JOIN projectallocation a on b.allotmentid=a.allotmentid " +
             "inner join  projects p on p.pid=a.pid "+
             "GROUP BY p.allotmentName",
       nativeQuery = true)
List<ProjectHoursProjection> getHoursByProjects();
@Query(value="SELECT date_format(b.date,'%b-%Y') as monthName, " +
             "SUM(CASE WHEN b.type = 'Prod' THEN b.hoursbooked ELSE 0 END) as prodHours, " +
             "SUM(CASE WHEN b.type = 'QC' THEN b.hoursbooked ELSE 0 END) as qcHours, " +
             "SUM(CASE WHEN b.type = 'Delivery' THEN b.hoursbooked ELSE 0 END) as deliveryHours, " +
             "SUM(CASE WHEN b.type = 'Pilot' THEN b.hoursbooked ELSE 0 END) as pilotHours, " +
             "SUM(b.hoursbooked) as totalHours " +
             "FROM bookedhours b group by date_format(b.date,'%b-%Y')",
       nativeQuery = true)
List<MonthlyHoursProjection> getHoursByMonthly();


}
