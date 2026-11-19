package com.geosieben.gsbworkday.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.AssetAssignment;

public interface AssetAssignmentRepository  extends JpaRepository<AssetAssignment,String>{
    @Query(value = "select * from assetassignment order by createdAt desc limit 1",nativeQuery = true)
    Optional<AssetAssignment> findMaxAssignmentid();
    @Modifying 
    @Query(value = "INSERT INTO assetassignment (assignmentid,hostid,assignedDate,assignedBy,assignedTo) VALUES(:assignmentid,:hostid,:assignedDate,:assignedBy,:assignedTo)",nativeQuery = true)
    public void assignHost(@Param("assignmentid") String assignmentid,
    @Param("hostid") int hostid,
    @Param("assignedDate") LocalDate assignedDate,
    @Param("assignedBy") String assignedBy,
    @Param("assignedTo") String assignedTo
);
}
