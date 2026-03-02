package com.geosieben.gsbworkday.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tltargethours")
public class TeamLeadTargetHours {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne
@JoinColumn(name="tlId")
private EmployeeBasicInfo teamLead;
private String month;
private int monthNum;
private int year;
private BigDecimal targethours;
private LocalDateTime updatedOn=LocalDateTime.now();
private BigDecimal carriedHours;


public TeamLeadTargetHours() {
}
public TeamLeadTargetHours(int id, EmployeeBasicInfo teamLead, String month, int monthNum, int year,
        BigDecimal targethours, LocalDateTime updatedOn, BigDecimal carriedHours) {
    this.id = id;
    this.teamLead = teamLead;
    this.month = month;
    this.monthNum = monthNum;
    this.year = year;
    this.targethours = targethours;
    this.updatedOn = updatedOn;
    this.carriedHours = carriedHours;
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public EmployeeBasicInfo getTeamLead() {
    return teamLead;
}
public void setTeamLead(EmployeeBasicInfo teamLead) {
    this.teamLead = teamLead;
}
public String getMonth() {
    return month;
}
public void setMonth(String month) {
    this.month = month;
}
public int getMonthNum() {
    return monthNum;
}
public void setMonthNum(int monthNum) {
    this.monthNum = monthNum;
}
public int getYear() {
    return year;
}
public void setYear(int year) {
    this.year = year;
}
public BigDecimal getTargethours() {
    return targethours;
}
public void setTargethours(BigDecimal targethours) {
    this.targethours = targethours;
}
public LocalDateTime getUpdatedOn() {
    return updatedOn;
}
public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
}
public BigDecimal getCarriedHours() {
    return carriedHours;
}
public void setCarriedHours(BigDecimal carriedHours) {
    this.carriedHours = carriedHours;
}

}
