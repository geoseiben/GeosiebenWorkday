package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
@Table(name="clientinteraction")
public class ClientInteractions{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "clientInfo", length = 255)
    private String clientInfo;

    @Column(name = "primaryContactPerson", length = 255)
    private String primaryContactPerson;

    @Column(name = "primaryPhone", length = 20)
    private String primaryPhone;

    @Column(name = "primaryMail", length = 255)
    private String primaryMail;

    @Column(name = "secondaryContactPerson", length = 255)
    private String secondaryContactPerson;

    @Column(name = "secondaryPhone", length = 20)
    private String secondaryPhone;

    @Column(name = "secondaryMail", length = 255)
    private String secondaryMail;

    @Column(name = "clientLocation", length = 255)
    private String clientLocation;

    @Column(name = "status", length = 50)
    private String status;
    @ManyToOne
    @JoinColumn(name="addedBy")
    private EmployeeBasicInfo addedBy;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ClientInteractions(Long id, String clientInfo, String primaryContactPerson, String primaryPhone,
            String primaryMail, String secondaryContactPerson, String secondaryPhone, String secondaryMail,
            String clientLocation, String status, EmployeeBasicInfo addedBy, LocalDateTime createdAt) {
        this.id = id;
        this.clientInfo = clientInfo;
        this.primaryContactPerson = primaryContactPerson;
        this.primaryPhone = primaryPhone;
        this.primaryMail = primaryMail;
        this.secondaryContactPerson = secondaryContactPerson;
        this.secondaryPhone = secondaryPhone;
        this.secondaryMail = secondaryMail;
        this.clientLocation = clientLocation;
        this.status = status;
        this.addedBy = addedBy;
        this.createdAt = createdAt;
    }

    
}
