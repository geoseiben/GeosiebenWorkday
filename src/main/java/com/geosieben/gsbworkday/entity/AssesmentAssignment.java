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
@Table(name="assesmentassignment")
public class AssesmentAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @ManyToOne
    @JoinColumn(name="assesmentid")
    private CourseAssesment assesment;
    @ManyToOne
    @JoinColumn(name="assignedTo")
    private EmployeeBasicInfo assignedTo;
    private int status=0;
    private LocalDateTime assignedOn=LocalDateTime.now();
}
