package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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

@NoArgsConstructor
@Data
@Entity
@Table(name="coursecompletion")
public class CourseCompletion {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "courseAssignmentId")
private CourseAssignment courseAssignment;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "contentId")
private CourseContent courseContent;
@Column(columnDefinition = "integer default 0")
private int isCompleted;
@JsonFormat(pattern = "dd-MMM-yyyy")
private LocalDate completedDate;
public CourseCompletion(CourseAssignment courseAssignment, CourseContent courseContent, int isCompleted,
        LocalDate completedDate) {
    this.courseAssignment = courseAssignment;
    this.courseContent = courseContent;
    this.isCompleted = isCompleted;
    this.completedDate = completedDate;
}

}
