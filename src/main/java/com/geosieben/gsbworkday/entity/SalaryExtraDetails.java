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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="salaryextradetails")
public class SalaryExtraDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne
@JoinColumn(name="EID")
private EmployeeBasicInfo employeeBasicInfo;
private String month;
@Column(columnDefinition = "decimal(3,1) default 0")
private BigDecimal lopindays;
@Column(columnDefinition = "decimal(8,2) default 0")
private BigDecimal extraPay;
@ManyToOne
@JoinColumn(name="addedBy")
private EmployeeBasicInfo addedBy;
private LocalDateTime createdAt=LocalDateTime.now();

}
