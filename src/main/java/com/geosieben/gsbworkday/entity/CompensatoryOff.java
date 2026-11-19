package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name = "compensatoryoff")
public class CompensatoryOff {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "EID")
private EmployeeBasicInfo employeeBasicInfo;
private LocalDate workedDate;
private LocalDate compOffDate;
@OneToOne
@JoinColumn(name="lid")
private Leaves leaves;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "approvedBy")
private EmployeeBasicInfo approvedBy;
private LocalDateTime createdAt =LocalDateTime.now();
public CompensatoryOff(EmployeeBasicInfo employeeBasicInfo, LocalDate workedDate, LocalDate compOffDate, Leaves leaves,
        EmployeeBasicInfo approvedBy, LocalDateTime createdAt) {
    this.employeeBasicInfo = employeeBasicInfo;
    this.workedDate = workedDate;
    this.compOffDate = compOffDate;
    this.leaves = leaves;
    this.approvedBy = approvedBy;
    this.createdAt = createdAt;
}

}
