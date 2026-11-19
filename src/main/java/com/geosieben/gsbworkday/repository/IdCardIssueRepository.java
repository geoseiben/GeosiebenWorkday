package com.geosieben.gsbworkday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.IdCardIssue;

public interface IdCardIssueRepository extends JpaRepository<IdCardIssue,String> {
    @Query(value = "SELECT * FROM idcardissue where EID=:eid",nativeQuery = true)
    public Optional<IdCardIssue> findByEid(@Param("eid") String eid);
}
