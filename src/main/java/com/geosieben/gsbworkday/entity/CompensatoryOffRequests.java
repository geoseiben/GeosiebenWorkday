package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
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
@Data
@NoArgsConstructor
@Entity
@Table(name = "compoffrequests")
public class CompensatoryOffRequests {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne
@JoinColumn(name = "requester")
private EmployeeBasicInfo requester;
private LocalDate compOffDate;
private String requestType;
@Column(columnDefinition = "int default 0")
private int status;
private String remarks;
private LocalDateTime requestedOn=LocalDateTime.now();
@ManyToOne
@JoinColumn(name="coid")
private CompensatoryOff compensatoryOff;
@Nullable
private LocalDate workedDate;
@ManyToOne
@JoinColumn(name="actionBy")
private EmployeeBasicInfo actionBy;
private String adminRemarks;
private LocalDateTime createdAt =LocalDateTime.now();
private LocalDateTime updatedAt =LocalDateTime.now();
public CompensatoryOffRequests(EmployeeBasicInfo requester, LocalDate compOffDate, String requestType, int status,
        String remarks, LocalDateTime requestedOn, CompensatoryOff compensatoryOff, LocalDate workedDate,
        EmployeeBasicInfo actionBy, String adminRemarks, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.requester = requester;
    this.compOffDate = compOffDate;
    this.requestType = requestType;
    this.status = status;
    this.remarks = remarks;
    this.requestedOn = requestedOn;
    this.compensatoryOff = compensatoryOff;
    this.workedDate = workedDate;
    this.actionBy = actionBy;
    this.adminRemarks = adminRemarks;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
}

}
