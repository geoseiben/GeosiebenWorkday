package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.CourseAssesmentDto;
import com.geosieben.gsbworkday.entity.AssesmentQuestions;

import lombok.val;

public interface AssesmentQuestionsRepository extends JpaRepository<AssesmentQuestions,Integer> {
@Query(value = "SELECT a.assesmentid ,a.assesmentDescription,a.assesmentTitle,"+
        " a.createdOn,a.durationInMinutes,a.passingScore, coalesce( c.questions,0) as questions "+
        " FROM courseassesment a "+
        " LEFT JOIN "+ 
	    " (SELECT assesmentid, COUNT(*) AS questions FROM assesmentquestions GROUP BY assesmentid) c ON a.assesmentid = c.assesmentid "+
"WHERE a.courseId = :cid",nativeQuery = true)
public List<CourseAssesmentDto> getCourseAssesments(@Param("cid") int cid);

@Query(value="select * from assesmentquestions where assesmentid=:aid",nativeQuery=true)
public List<AssesmentQuestions> getCourseAssesmentQuestions(@Param("aid") int assesmanetid);
}
