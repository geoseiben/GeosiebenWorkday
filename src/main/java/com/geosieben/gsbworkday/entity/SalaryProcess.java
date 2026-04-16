package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
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
@ManyToOne
@JoinColumn(name="updatedBy")
private EmployeeBasicInfo updatedBy;
private LocalDateTime updatedOn=LocalDateTime.now();
}
