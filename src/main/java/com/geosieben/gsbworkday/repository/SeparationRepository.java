package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.Separation;

@Repository
public interface SeparationRepository extends JpaRepository<Separation,Integer> {

}
