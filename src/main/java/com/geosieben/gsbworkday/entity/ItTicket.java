package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data 
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="EID")
    private EmployeeBasicInfo employeeBasicInfo;
    @ManyToOne 
    @JoinColumn (name="hostId")
    private Hosts hosts;

    public ItTicket(String issue, String description, String priority, String hostname, String anyDesk) {
        this.issue = issue;
        this.description = description;
        this.priority = priority;
        this.hostname = hostname;
        this.anyDesk = anyDesk;
    }

    public ItTicket() {

    }

    
}
