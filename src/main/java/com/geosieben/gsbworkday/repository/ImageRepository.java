package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geosieben.gsbworkday.entity.Images;

public interface ImageRepository extends JpaRepository<Images,Integer> {

}
