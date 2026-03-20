package com.geosieben.gsbworkday.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="employeetargethours")
public class EmployeeTargetHours {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne
@JoinColumn(name="EID")
private EmployeeBasicInfo employee;
private String month;
private int monthNum;
private int year;
@Column(columnDefinition = "DECIMAL(5,2)")
private BigDecimal targetHours;
@Column(columnDefinition = "DECIMAL(5,2)")
private BigDecimal carriedHours;
private LocalDateTime createdAt=LocalDateTime.now();
private LocalDateTime updatedAt=LocalDateTime.now();



}
