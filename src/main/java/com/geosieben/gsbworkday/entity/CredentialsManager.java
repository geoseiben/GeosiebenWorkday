package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "credentialsmanager")
public class CredentialsManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "credName", length = 4096)
    private byte[] credName;
    @Column(name = "hostip", length = 4096)
    private byte[] hostip;
    @Column(name = "category", length = 4096)
    private byte[] category;
    @Column(name = "userName", length = 4096)
    private byte[] userName;
    @Column(name = "password", length = 4096)
    private byte[] password;
    @Column(name = "remarks", length = 4096)
    private byte[] remarks;
        @Column(name = "iv", length = 32)
    private byte[] iv;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "addedBy")
    private EmployeeBasicInfo addedBy;
    @SuppressWarnings("unused")
    private LocalDateTime createdAt=LocalDateTime.now();
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt=LocalDateTime.now();
}
