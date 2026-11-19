package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="assetassignment")
public class AssetAssignment {
    @Id
    @Column (name = "assignmentId")
    private String assignmentId;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "laptop")
    private ItAsset laptop=null;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="assignedTo")
    private EmployeeBasicInfo assignedTo;
    @Column(nullable = false)
    @JsonFormat(pattern="DD-MMM-YYYY")
    private LocalDate assignedDate=LocalDate.now();
    @JsonFormat(pattern="DD-MMM-YYYY")
    private LocalDate returnDate=null;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="assignedBy")
    private EmployeeBasicInfo assignedBy;
    @Column(nullable = true)
    private String assignmentRemarks;
    @Column(nullable = true)
    private String returnRemarks =null;
        @Column(nullable = true)
    private String ackFilePath=null;
    private LocalDateTime createdAt=LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="hostid")
    private Hosts host;
    public AssetAssignment(String assignmentId, ItAsset laptop, EmployeeBasicInfo assignedTo, LocalDate assignedDate,
            LocalDate returnDate, EmployeeBasicInfo assignedBy, String assignmentRemarks, String returnRemarks,
            String ackFilePath, LocalDateTime createdAt, Hosts host) {
        this.assignmentId = assignmentId;
        this.laptop = laptop;
        this.assignedTo = assignedTo;
        this.assignedDate = assignedDate;
        this.returnDate = returnDate;
        this.assignedBy = assignedBy;
        this.assignmentRemarks = assignmentRemarks;
        this.returnRemarks = returnRemarks;
        this.ackFilePath = ackFilePath;
        this.createdAt = createdAt;
        this.host = host;
    }

}
