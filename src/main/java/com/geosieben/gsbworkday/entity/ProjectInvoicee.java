package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data 
@Entity 
@Table (name = "projectinvoice")
public class ProjectInvoicee {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne 
    @JoinColumn (name="projectid")
    private Project project;
    private String invoiceNo;
    private String workOrder;
    private Double clientHours;
    private Double prodHours;
    private String tlRemarks;
    @ManyToOne 
    @JoinColumn (name="addedBy")
    private EmployeeBasicInfo addedBy;
    private LocalDateTime addedOn=LocalDateTime.now();


    
    public ProjectInvoicee() {
    }



    public ProjectInvoicee(int id, Project project, String invoiceNo, String workOrder, Double clientHours,
            Double prodHours, String tlRemarks, EmployeeBasicInfo addedBy, LocalDateTime addedOn) {
        this.id = id;
        this.project = project;
        this.invoiceNo = invoiceNo;
        this.workOrder = workOrder;
        this.clientHours = clientHours;
        this.prodHours = prodHours;
        this.tlRemarks = tlRemarks;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
    }



}
