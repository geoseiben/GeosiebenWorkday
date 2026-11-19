package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geosieben.gsbworkday.dto.CourseDtoInt;
import com.geosieben.gsbworkday.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Integer> {

    @Query(value="SELECT e.firstName,e.lastName,c.courseId,c.profeciencyLevel, "+
                 " c.category as courseCategory,c.courseImage,c.courseDescription,c.courseTitle "+
                 " FROM course c INNER JOIN employee_basic_info e on e.eid=c.createdBy ",
                 nativeQuery=true
    )
    public List<CourseDtoInt> getCourses(); 


}
