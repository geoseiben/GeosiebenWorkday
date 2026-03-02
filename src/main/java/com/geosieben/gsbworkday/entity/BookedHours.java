package com.geosieben.gsbworkday.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Cache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="bookedhours")
public class BookedHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
@ManyToOne
@JoinColumn(name="EID")
private EmployeeBasicInfo employeeBasicInfo;
@ManyToOne
@JoinColumn(name="allotmentid")
private ProjectAllocation projectAllocation;
private LocalDate date;
@Column(columnDefinition = "DECIMAL(4,1)")
private BigDecimal hoursBooked;
private int completionPercentage;
private LocalDateTime updatedOn =LocalDateTime.now();
private String type;


public BookedHours() {
}
public BookedHours(int id, EmployeeBasicInfo employeeBasicInfo, ProjectAllocation projectAllocation, LocalDate date,
        BigDecimal hoursBooked, int completionPercentage, LocalDateTime updatedOn, String type) {
    this.id = id;
    this.employeeBasicInfo = employeeBasicInfo;
    this.projectAllocation = projectAllocation;
    this.date = date;
    this.hoursBooked = hoursBooked;
    this.completionPercentage = completionPercentage;
    this.updatedOn = updatedOn;
    this.type = type;
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public EmployeeBasicInfo getEmployeeBasicInfo() {
    return employeeBasicInfo;
}
public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
    this.employeeBasicInfo = employeeBasicInfo;
}
public ProjectAllocation getProjectAllocation() {
    return projectAllocation;
}
public void setProjectAllocation(ProjectAllocation projectAllocation) {
    this.projectAllocation = projectAllocation;
}
public LocalDate getDate() {
    return date;
}
public void setDate(LocalDate date) {
    this.date = date;
}
public BigDecimal getHoursBooked() {
    return hoursBooked;
}
public void setHoursBooked(BigDecimal hoursBooked) {
    this.hoursBooked = hoursBooked;
}
public int getCompletionPercentage() {
    return completionPercentage;
}
public void setCompletionPercentage(int completionPercentage) {
    this.completionPercentage = completionPercentage;
}
public LocalDateTime getUpdatedOn() {
    return updatedOn;
}
public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
}
public String getType() {
    return type;
}
public void setType(String type) {
    this.type = type;
}

}
