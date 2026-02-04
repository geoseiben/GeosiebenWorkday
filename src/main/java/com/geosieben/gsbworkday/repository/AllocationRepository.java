package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.ProjectAllocation;

@Repository
public interface AllocationRepository extends JpaRepository<ProjectAllocation ,Integer>{


}
