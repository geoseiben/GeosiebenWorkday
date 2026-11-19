package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;

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
@Table(name="exitinterview")
public class ExitInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "SeparationId")
    private Separation separation;
    private LocalDate exitInterviewDate;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ConductedBy")
    private EmployeeBasicInfo ConductedBy;
    public ExitInterview(Separation separation, LocalDate exitInterviewDate, EmployeeBasicInfo conductedBy) {
        this.separation = separation;
        this.exitInterviewDate = exitInterviewDate;
        ConductedBy = conductedBy;
    }
    
}
