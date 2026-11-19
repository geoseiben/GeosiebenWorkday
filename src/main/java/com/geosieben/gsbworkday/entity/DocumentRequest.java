package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
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
import lombok.NoArgsConstructor;
@Data 
@NoArgsConstructor 
@Entity 
@Table (name="documentrequests")
public class DocumentRequest {
@Id 
@GeneratedValue (strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "requester")
private EmployeeBasicInfo requester;
private String requestedDocument;
private LocalDateTime requestedOn=LocalDateTime.now();
private String reason;
@Column (nullable = true)
private LocalDate deliveryDate=null;
private String adminRemarks=null;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "actionBy",nullable = true)
private EmployeeBasicInfo actionBy;
@Column (columnDefinition = "default int 0")
private int status=0;// 0 Created  1 Accepted 2 Delivered 3 Rejected
private String documentPath=null;
public DocumentRequest(EmployeeBasicInfo requester, String requestedDocument, LocalDateTime requestedOn, String reason,
        LocalDate deliveryDate, String adminRemarks, EmployeeBasicInfo actionBy, int status, String documentPath) {
    this.requester = requester;
    this.requestedDocument = requestedDocument;
    this.requestedOn = requestedOn;
    this.reason = reason;
    this.deliveryDate = deliveryDate;
    this.adminRemarks = adminRemarks;
    this.actionBy = actionBy;
    this.status = status;
    this.documentPath = documentPath;
}

}
