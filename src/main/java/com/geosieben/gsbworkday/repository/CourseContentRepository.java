package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.ContentResponse;
import com.geosieben.gsbworkday.entity.CourseContent;

public interface CourseContentRepository extends JpaRepository<CourseContent,Integer> {

    @Query(value="SELECT contentId AS id,contentTitle as title,sequenceNo as seq,contentDescription as description,"+
                 " fileType as type,contentFilePath as fileUrl,durationInSec  "+
                 "FROM coursecontent where courseid=:cid",nativeQuery = true
    )
    public List<ContentResponse> getContents(@Param("cid") int courseid);
}
