package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="hrdtickets")
public class HRDTickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String priority;
    private String subject;
    private String description;
    private int status;
    private LocalDateTime raisedOn =LocalDateTime.now();
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt=LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name="raisedBy")
    private EmployeeBasicInfo employeeBasicInfo;


    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }
    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
    public HRDTickets() {
    }
    public HRDTickets(String priority, String subject, String description) {
        this.priority = priority;
        this.subject = subject;
        this.description = description;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
