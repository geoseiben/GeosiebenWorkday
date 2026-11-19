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
@Table(name = "projectdelivery")
public class ProjectDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="projectId")
    private Project project;
    private String feeder;
    private LocalDateTime deliveryDate=LocalDateTime.now();
        @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="deliveredBy")
    private EmployeeBasicInfo deliveredBy;
    private String deliveryRemarks;

}
