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
@Table(name = "assesmentattempt")
public class AssesmentAttempt {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int attemptid;
@ManyToOne
@JoinColumn(name="assignmentid")
private AssesmentAssignment assesmentAssignment;
private double scoredPercentage;
private double passingScore;
private int noOfQuestions;
private int correctAnswers;
private LocalDateTime attemptedOn=LocalDateTime.now();
}
