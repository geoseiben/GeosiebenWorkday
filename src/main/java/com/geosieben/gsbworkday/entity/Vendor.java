package com.geosieben.gsbworkday.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data 
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "vendorName", length = 150, nullable = false)
    private String vendorName;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy")

    private EmployeeBasicInfo createdBy;

    // Default Constructor
    public Vendor() {
    }

    // All-args Constructor (excluding auto-generated fields)
    public Vendor(String vendorName, String address, String phone, String email, EmployeeBasicInfo createdBy) {
        this.vendorName = vendorName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdBy = createdBy;
    }


}
