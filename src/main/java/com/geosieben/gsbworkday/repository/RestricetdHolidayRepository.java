package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.RestrictedHolidays;

@Repository
public interface RestricetdHolidayRepository extends JpaRepository<RestrictedHolidays ,Integer> {

}
