package com.geosieben.gsbworkday.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="idcardissue")
public class IdCardIssue {
    @Id
    @Column(length = 8)
    private String EID;
    @OneToOne
    @JoinColumn(name="EID")
    @MapsId
    private EmployeeBasicInfo employeeBasicInfo;
@Column(columnDefinition = "integer default 0", nullable = false)    
private int idIssued;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private LocalDate issuedDate;
    private LocalDateTime createdAt =LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="issuedBy")
    private EmployeeBasicInfo issuedBy;
    public IdCardIssue(String eID, EmployeeBasicInfo employeeBasicInfo, int idIssued, LocalDate issuedDate,
            LocalDateTime createdAt, EmployeeBasicInfo issuedBy) {
        EID = eID;
        this.employeeBasicInfo = employeeBasicInfo;
        this.idIssued = idIssued;
        this.issuedDate = issuedDate;
        this.createdAt = createdAt;
        this.issuedBy = issuedBy;
    }
    
}
