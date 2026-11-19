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
@Table(name = "assesmentquestions")
public class AssesmentQuestions {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int qid;
@ManyToOne
@JoinColumn(name="assesmentid")
private CourseAssesment assesment;
private String question;
private String optionA;
private String optionB;
private String optionC;
private String optionD;
private String answer;
private LocalDateTime createdOn=LocalDateTime.now();

}
