package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data 
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
    @SuppressWarnings("unused")
    private LocalDateTime raisedOn =LocalDateTime.now();
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt=LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="raisedBy")
    private EmployeeBasicInfo employeeBasicInfo;
    
    public HRDTickets() {
    }

    public HRDTickets(int id, String priority, String subject, String description, int status, LocalDateTime raisedOn,
            LocalDateTime updatedAt, EmployeeBasicInfo employeeBasicInfo) {
        this.id = id;
        this.priority = priority;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.raisedOn = raisedOn;
        this.updatedAt = updatedAt;
        this.employeeBasicInfo = employeeBasicInfo;
    }




}
