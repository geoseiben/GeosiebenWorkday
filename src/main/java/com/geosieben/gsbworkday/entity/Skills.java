package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@NoArgsConstructor
@Entity
@Table(name="skills")
public class Skills {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="EID")
private EmployeeBasicInfo employeeBasicInfo;
private String skill;
private int scale;
private LocalDateTime createdAt=LocalDateTime.now();
@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
private LocalDateTime updatedAt;
public Skills(EmployeeBasicInfo employeeBasicInfo, String skill, int scale, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    this.employeeBasicInfo = employeeBasicInfo;
    this.skill = skill;
    this.scale = scale;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
}

}
