package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="salaryprocess")
public class SalaryProcess {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String month;
private int status=0;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="processedBy")
private EmployeeBasicInfo processedBy;
private LocalDateTime processedOn=LocalDateTime.now();
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="approvedBy")
private EmployeeBasicInfo approvedBy;
private LocalDateTime approvedOn=null;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="releasedBy")
private EmployeeBasicInfo releasedBy;
private LocalDateTime releasedOn=null;
public SalaryProcess(String month, int status, EmployeeBasicInfo processedBy, LocalDateTime processedOn,
        EmployeeBasicInfo approvedBy, LocalDateTime approvedOn, EmployeeBasicInfo releasedBy,
        LocalDateTime releasedOn) {
    this.month = month;
    this.status = status;
    this.processedBy = processedBy;
    this.processedOn = processedOn;
    this.approvedBy = approvedBy;
    this.approvedOn = approvedOn;
    this.releasedBy = releasedBy;
    this.releasedOn = releasedOn;
}


}
