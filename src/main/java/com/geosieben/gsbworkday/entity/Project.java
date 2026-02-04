package com.geosieben.gsbworkday.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity 
@Table(name="projects")
public class Project {
@Id 
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int pid;
private String allotmentName;
private BigDecimal totalHours;
private BigDecimal productionHrs;
private BigDecimal qaHrs;
private LocalDate starDate;
private LocalDate endDate;
private LocalDateTime createdAt=LocalDateTime.now();
private int status=0;
private String filePath;

@ManyToOne
@JoinColumn(name="projectLead")
private EmployeeBasicInfo projectLead;
@ManyToOne
@JoinColumn(name="createdBy")
private EmployeeBasicInfo createdBy;

@ManyToOne
@JoinColumn(name="rootId")
private RootProject rootProject;
public Project(String allotmentName, BigDecimal totalHours, BigDecimal productionHrs, BigDecimal qaHrs, LocalDate starDate,
        LocalDate endDate, String filePath, EmployeeBasicInfo projectLead, EmployeeBasicInfo createdBy,
        RootProject rootProject) {
    this.allotmentName = allotmentName;
    this.totalHours = totalHours;
    this.productionHrs = productionHrs;
    this.qaHrs = qaHrs;
    this.starDate = starDate;
    this.endDate = endDate;
    this.filePath = filePath;
    this.projectLead = projectLead;
    this.createdBy = createdBy;
    this.rootProject = rootProject;
}


public Project() {
}


public String getAllotmentName() {
    return allotmentName;
}
public void setAllotmentName(String allotmentName) {
    this.allotmentName = allotmentName;
}
public BigDecimal getTotalHours() {
    return totalHours;
}
public void setTotalHours(BigDecimal totalHours) {
    this.totalHours = totalHours;
}
public BigDecimal getProductionHrs() {
    return productionHrs;
}
public void setProductionHrs(BigDecimal productionHrs) {
    this.productionHrs = productionHrs;
}
public BigDecimal getQaHrs() {
    return qaHrs;
}
public void setQaHrs(BigDecimal qaHrs) {
    this.qaHrs = qaHrs;
}
public LocalDate getStarDate() {
    return starDate;
}
public void setStarDate(LocalDate starDate) {
    this.starDate = starDate;
}
public LocalDate getEndDate() {
    return endDate;
}
public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
}
public int getStatus() {
    return status;
}
public void setStatus(int status) {
    this.status = status;
}
public String getFilePath() {
    return filePath;
}
public void setFilePath(String filePath) {
    this.filePath = filePath;
}
public EmployeeBasicInfo getProjectLead() {
    return projectLead;
}
public void setProjectLead(EmployeeBasicInfo projectLead) {
    this.projectLead = projectLead;
}
public EmployeeBasicInfo getCreatedBy() {
    return createdBy;
}
public void setCreatedBy(EmployeeBasicInfo createdBy) {
    this.createdBy = createdBy;
}

public RootProject getRootProject() {
    return rootProject;
}
public void setRootProject(RootProject rootProject) {
    this.rootProject = rootProject;
}


}
