package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geosieben.gsbworkday.entity.Warnings;

public interface WarningsRepository extends JpaRepository<Warnings,Integer> {

}
