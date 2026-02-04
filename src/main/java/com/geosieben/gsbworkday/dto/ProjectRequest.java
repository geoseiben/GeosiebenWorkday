package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectRequest {
    public String projectName;
    public String projectLeadId;
    public String projectType;
    public int categoryId;
    public int clientId;
    public LocalDate startDate;
    public LocalDate deadline;
    private BigDecimal hours;
    private String filepath;
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectLeadId() {
        return projectLeadId;
    }
    public void setProjectLeadId(String projectLeadId) {
        this.projectLeadId = projectLeadId;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public BigDecimal getHours() {
        return hours;
    }
    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    
}
