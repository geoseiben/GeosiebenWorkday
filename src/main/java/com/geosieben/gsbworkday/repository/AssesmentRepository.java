package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.Assessment;
@Repository
public interface AssesmentRepository extends JpaRepository<Assessment,Integer> {

}
