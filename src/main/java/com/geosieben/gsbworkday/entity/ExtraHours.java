package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity 
@Table (name = "extrahours")
public class ExtraHours {
@Id 
@GeneratedValue (strategy = GenerationType.IDENTITY)
private int id;
private String month;
private int montNum;
private int year;
@ManyToOne
@JoinColumn (name = "EID")
private EmployeeBasicInfo employeeBasicInfo;
private double hours;
private LocalDateTime createdAt=LocalDateTime.now();
public ExtraHours(int id, String month, int montNum, int year, EmployeeBasicInfo employeeBasicInfo, double hours,
        LocalDateTime createdAt) {
    this.id = id;
    this.month = month;
    this.montNum = montNum;
    this.year = year;
    this.employeeBasicInfo = employeeBasicInfo;
    this.hours = hours;
    this.createdAt = createdAt;
}

}
