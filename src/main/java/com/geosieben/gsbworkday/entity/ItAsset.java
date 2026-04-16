package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // Added to allow updating status and assignments
@Entity
@Table(name="itasset")
public class ItAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assetid;

    private String assetName;
    private String model;
    private String category;

    @Column(unique = true) // Standard JPA way to ensure unique serial numbers
    private String serialNo;

    private String vendor;

    // Consider using an Enum for status (e.g., AVAILABLE, ASSIGNED, SCRAPPED)
    private int status;

    @Column(updatable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="createdBy")
    private EmployeeBasicInfo createdBy;

    @ManyToOne
    @JoinColumn(name="assignedTo")
    private EmployeeBasicInfo assignedTo;
}