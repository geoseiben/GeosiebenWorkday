package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.CourseCompletion;

public interface CourseCompletionRepository extends JpaRepository<CourseCompletion,Integer> {
@Query(value="select ct.contentId from coursecompletion cc "+
    " inner join coursecontent ct on cc.contentid=ct.contentId "+
    " where cc.courseAssignmentId=:assignmentid order by ct.sequenceNo asc ",nativeQuery=true
)
public Integer[] getCompletedContentIds(@Param("assignmentid") int assignmentid);
}
