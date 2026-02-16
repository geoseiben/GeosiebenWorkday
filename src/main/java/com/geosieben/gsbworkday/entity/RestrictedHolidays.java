package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Cell;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restricted_holidays")
public class RestrictedHolidays {
@Id
@GeneratedValue (strategy =GenerationType.IDENTITY)
private int id;
private String holidayName;
private LocalDate holidayDate;
private LocalDateTime createdAt=LocalDateTime.now();
private String type;


public RestrictedHolidays() {
}
public RestrictedHolidays(String holidayName, LocalDate holidayDate, String type) {
    this.holidayName = holidayName;
    this.holidayDate = holidayDate;
    this.type = type;
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public String getHolidayName() {
    return holidayName;
}
public void setHolidayName(String holidayName) {
    this.holidayName = holidayName;
}
public LocalDate getHolidayDate() {
    return holidayDate;
}
public void setHolidayDate(LocalDate holidayDate) {
    this.holidayDate = holidayDate;
}
public LocalDateTime getCreatedAt() {
    return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}
public String getType() {
    return type;
}
public void setType(String type) {
    this.type = type;
}


}
