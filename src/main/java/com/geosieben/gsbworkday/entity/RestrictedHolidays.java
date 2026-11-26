package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data 
@Table(name = "restricted_holidays")
public class RestrictedHolidays {
@Id
@GeneratedValue (strategy =GenerationType.IDENTITY)
private int id;
private String holidayName;
private LocalDate holidayDate;
private LocalDateTime createdAt=LocalDateTime.now();
private LocalDateTime updatedAt=LocalDateTime.now();
private String type;


public RestrictedHolidays() {
}


public RestrictedHolidays(int id, String holidayName, LocalDate holidayDate, LocalDateTime createdAt,
        LocalDateTime updatedAt, String type) {
    this.id = id;
    this.holidayName = holidayName;
    this.holidayDate = holidayDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.type = type;
}


}
