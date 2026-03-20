package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter

@Entity
@Table(name="itasset")
public class ItAsset {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int assetid;
private String assetName;
private String model;
private String category;
@Column(columnDefinition = "UNIQUE")
private String serialNo;
private String vendor;
private int status;
private LocalDateTime createdOn=LocalDateTime.now();
@ManyToOne
@JoinColumn(name="createdBy")
private EmployeeBasicInfo createdBy;
@ManyToOne
@JoinColumn(name="assignedTo")
private EmployeeBasicInfo assignedTo;

}
