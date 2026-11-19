package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data 
@Entity
@Table(name="itasset")
public class ItAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assetid;
    @Column(columnDefinition = "int default 0")
    private int isAvailable;
    private String assetName;
    private String brand;
    private String type;
    private String model;
    private String category;
    @Column(unique = true) 
    private String serialNo;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name ="vendor")
    private Vendor vendor;
    private int status;
    @Column(updatable = false)
    @JsonFormat(pattern = "dd-MMM-YYYY")
    private LocalDateTime createdOn = LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="createdBy")
    private EmployeeBasicInfo createdBy;
    private int available=1;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="assignedTo")
    private EmployeeBasicInfo assignedTo;
    private String batchId;
    public ItAsset(int assetid, int isAvailable, String assetName, String brand, String type, String model,
            String category, String serialNo, Vendor vendor, int status, LocalDateTime createdOn,
            EmployeeBasicInfo createdBy, int available, EmployeeBasicInfo assignedTo, String batchId) {
        this.assetid = assetid;
        this.isAvailable = isAvailable;
        this.assetName = assetName;
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.category = category;
        this.serialNo = serialNo;
        this.vendor = vendor;
        this.status = status;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.available = available;
        this.assignedTo = assignedTo;
        this.batchId = batchId;
    }

}