package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.AssignedCourseDto;
import com.geosieben.gsbworkday.dto.AssigneesDto;
import com.geosieben.gsbworkday.dto.UnAssignedEmployeesDto;
import com.geosieben.gsbworkday.entity.CourseAssignment;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment,Integer> {
@Query(value="SELECT concat(e.firstname,' ',e.lastName) as assignedBy,c.courseId,a.assignmentId,a.assignedOn,c.courseImage,"+
"c.courseDescription,c.category,c.courseTitle,c.profeciencyLevel,a.isCompleted FROM courseassignment a "+
"LEFT JOIN course c on c.courseid=a.courseid "+
"LEFT JOIN employee_basic_info e on e.eid=a.assignedBy "+
 "where assignedTo=:assignedto",
 nativeQuery=true
)
public List<AssignedCourseDto> getAssignedCourses(@Param("assignedto") String assignedto);

@Query(value = " SELECT concat(e.firstName,' ',e.lastName) as name,e.eid,i.image "+
    " FROM employee_basic_info e "+
    " LEFT JOIN images i on i.eid=e.eid "+
    " where e.eid not in( "+
    " select assignedTo from courseassignment "+
    " where courseid=:courseid)",nativeQuery = true
)
public List<UnAssignedEmployeesDto> getunAssignedList(@Param("courseid") int courseid);


    @Query(value="SELECT concat(e.firstName,' ',e.lastName) as assignee,e.eid,i.image, round(((coalesce( cp.completedContents,0)/ct.contents))*100) as percentage "+
        "FROM courseassignment ca "+
        " LEFT JOIN employee_basic_info e on e.eid=ca.assignedTo "+
        " LEFT JOIN images i on i.eid=ca.assignedTo "+
        " LEFT JOIN (select courseid,count(*) as contents from coursecontent group by courseid) ct on ct.courseid=ca.courseId " + 
        " LEFT JOIN  (select courseAssignmentId,count(*) as completedContents from coursecompletion group by courseAssignmentId) cp on cp.courseAssignmentId=ca.assignmentid " +
        " WHERE ca.courseid=:courseId ",nativeQuery = true 
    )
    public List<AssigneesDto> getCourseAssignees(@Param("courseId") int courseId) ;
}
