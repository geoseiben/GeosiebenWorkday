package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
@Table(name = "assetlease")
public class AssetLease {
@Id
private String leaseId;
@ManyToOne
@JoinColumn(name="hostid")
private  Hosts host;
@ManyToOne
@JoinColumn(name="leasedTo")
private EmployeeBasicInfo leasedTo;
private String remarks;
private String leaseDocumentPath;
private LocalDate leaseddate;
private LocalDate leaseReturnDate;
private String leaseReturnDocumentPath;
private String returnRemarks;
@ManyToOne
@JoinColumn(name="leasedBy")
private EmployeeBasicInfo leasedBy;
@ManyToOne
@JoinColumn(name="leaseReturnedBy")
private EmployeeBasicInfo leaseReturnedBy;
private LocalDateTime createdOn=LocalDateTime.now();
private LocalDateTime updatedOn=LocalDateTime.now();
public AssetLease(String leaseId, Hosts host, EmployeeBasicInfo leasedTo, String remarks, String leaseDocumentPath,
        LocalDate leaseddate, LocalDate leaseReturnDate, String leaseReturnDocumentPath, String returnRemarks,
        EmployeeBasicInfo leasedBy, EmployeeBasicInfo leaseReturnedBy, LocalDateTime createdOn,
        LocalDateTime updatedOn) {
    this.leaseId = leaseId;
    this.host = host;
    this.leasedTo = leasedTo;
    this.remarks = remarks;
    this.leaseDocumentPath = leaseDocumentPath;
    this.leaseddate = leaseddate;
    this.leaseReturnDate = leaseReturnDate;
    this.leaseReturnDocumentPath = leaseReturnDocumentPath;
    this.returnRemarks = returnRemarks;
    this.leasedBy = leasedBy;
    this.leaseReturnedBy = leaseReturnedBy;
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
}

}
