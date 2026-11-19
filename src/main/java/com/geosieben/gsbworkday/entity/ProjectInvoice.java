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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="projectinvoice")
public class ProjectInvoice {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="projectid")
private Project project;
private String invoiceNumber;
private String workOrder;
private double clientHours;
private double prodHours;
private String tlRemarks;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="addedBy")
private EmployeeBasicInfo addedBy;
private LocalDateTime addedOn;
public ProjectInvoice(Project project, String invoiceNumber, String workOrder, double clientHours, double prodHours,
        String tlRemarks, EmployeeBasicInfo addedBy, LocalDateTime addedOn) {
    this.project = project;
    this.invoiceNumber = invoiceNumber;
    this.workOrder = workOrder;
    this.clientHours = clientHours;
    this.prodHours = prodHours;
    this.tlRemarks = tlRemarks;
    this.addedBy = addedBy;
    this.addedOn = addedOn;
}


}
