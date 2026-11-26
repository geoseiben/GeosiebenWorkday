package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geosieben.gsbworkday.entity.RestrictedHolidays;

public interface RestricetdHolidayRepository extends JpaRepository<RestrictedHolidays ,Integer> {

    @Query (value = "SELECT * FROM restricted_holidays where holidayDate>current_date and type='RH'",nativeQuery = true)
    public  List<RestrictedHolidays> getRhAvailable();
}
