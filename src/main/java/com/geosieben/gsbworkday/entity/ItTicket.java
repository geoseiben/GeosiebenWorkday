package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ittickets")
public class ItTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketid;
    private String issue;
    private String description;
    private int status=0;
    private String priority;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "Remarks")
    private String adminRemarks;
    private  String hostname;
    @Column(name = "anydeskid")
    private String anyDesk;
    @ManyToOne
    @JoinColumn(name="EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public ItTicket(String issue, String description, String priority, String hostname, String anyDesk) {
        this.issue = issue;
        this.description = description;
        this.priority = priority;
        this.hostname = hostname;
        this.anyDesk = anyDesk;
    }

    public ItTicket() {

    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAdminRemarks() {
        return adminRemarks;
    }

    public void setAdminRemarks(String adminRemarks) {
        this.adminRemarks = adminRemarks;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAnyDesk() {
        return anyDesk;
    }

    public void setAnyDesk(String anyDesk) {
        this.anyDesk = anyDesk;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
