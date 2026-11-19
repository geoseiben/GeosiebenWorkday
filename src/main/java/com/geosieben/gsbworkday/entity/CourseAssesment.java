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
@Table(name="courseassesment")
public class CourseAssesment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int assesmentid;
@ManyToOne
@JoinColumn(name="courseId")
private Course course;
@ManyToOne
@JoinColumn(name="createdBy")
private EmployeeBasicInfo createdBy;
private LocalDateTime createdOn =LocalDateTime.now();
private String assesmentTitle;
private String difficulty;
private String assesmentDescription;
private double passingScore;
private int durationInMinutes;
}
