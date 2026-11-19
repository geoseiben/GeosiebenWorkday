package com.geosieben.gsbworkday.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@NoArgsConstructor
@Data
@Entity
@Table(name = "warnings")
public class Warnings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="issuedTo")
    private EmployeeBasicInfo issuedTo;

    @CreationTimestamp
    @Column(name = "issuedOn", nullable = false, updatable = false)
    private LocalDateTime issuedOn;

    @Column(name = "warningType", length = 20)
    private String warningType;

    @Column(name = "warningDate")
    private LocalDate warningDate;

    @Column(name = "loginTime")
    private LocalTime loginTime;

    @Column(name = "logoutTime")
    private LocalTime logoutTime;

    @Column(name = "workingHours", precision = 4, scale = 2)
    private BigDecimal workingHours;

    @Column(name = "breakHours", precision = 4, scale = 2)
    private BigDecimal breakHours;
    @ManyToOne
    @JoinColumn(name="issuedBy")
    private EmployeeBasicInfo issuedBy;
    @ManyToOne
    @JoinColumn(name="reportingManager")
    private EmployeeBasicInfo reportingManager;
    public Warnings(EmployeeBasicInfo issuedTo, LocalDateTime issuedOn, String warningType, LocalDate warningDate,
            LocalTime loginTime, LocalTime logoutTime, BigDecimal workingHours, BigDecimal breakHours,
            EmployeeBasicInfo issuedBy, EmployeeBasicInfo reportingManager) {
        this.issuedTo = issuedTo;
        this.issuedOn = issuedOn;
        this.warningType = warningType;
        this.warningDate = warningDate;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.workingHours = workingHours;
        this.breakHours = breakHours;
        this.issuedBy = issuedBy;
        this.reportingManager = reportingManager;
    }
    
}