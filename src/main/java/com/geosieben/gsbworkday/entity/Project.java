package com.geosieben.gsbworkday.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity 
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="projects")
public class Project {
@Id 
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int pid;
private  String projectId;
private String allotmentName;
private BigDecimal totalHours;
private BigDecimal productionHrs;
private BigDecimal qaHrs;
private LocalDate startDate;
private LocalDate endDate;
private String area;
@ColumnDefault("1")
@Column(columnDefinition = "int default 0")
private int isHoursFixed=1;
@SuppressWarnings("unused")
private LocalDateTime createdAt=LocalDateTime.now();
private int status=0;
private String filePath;
private double addHours;
@Nullable
private LocalDateTime finishedOn;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="projectLead")
private EmployeeBasicInfo projectLead;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="category")
private ProjectCategories category;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="createdBy")
private EmployeeBasicInfo createdBy;

@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="rootId")
private RootProject rootProject;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="department")
private Department department;
public Project(int pid, String projectId, String allotmentName, BigDecimal totalHours, BigDecimal productionHrs,
        BigDecimal qaHrs, LocalDate startDate, LocalDate endDate, String area, int isHoursFixed,
        LocalDateTime createdAt, int status, String filePath, double addHours, LocalDateTime finishedOn,
        EmployeeBasicInfo projectLead, ProjectCategories category, EmployeeBasicInfo createdBy, RootProject rootProject,
        Department department) {
    this.pid = pid;
    this.projectId = projectId;
    this.allotmentName = allotmentName;
    this.totalHours = totalHours;
    this.productionHrs = productionHrs;
    this.qaHrs = qaHrs;
    this.startDate = startDate;
    this.endDate = endDate;
    this.area = area;
    this.isHoursFixed = isHoursFixed;
    this.createdAt = createdAt;
    this.status = status;
    this.filePath = filePath;
    this.addHours = addHours;
    this.finishedOn = finishedOn;
    this.projectLead = projectLead;
    this.category = category;
    this.createdBy = createdBy;
    this.rootProject = rootProject;
    this.department = department;
}


}
