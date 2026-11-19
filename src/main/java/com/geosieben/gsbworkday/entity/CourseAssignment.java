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
@Table(name="courseassignment")
public class CourseAssignment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int assignmentid;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="courseId")
private Course course;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="assignedTo")
private EmployeeBasicInfo assigneTo;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="assignedBy")
private EmployeeBasicInfo assignedBy;
@JsonFormat(pattern = "dd-MMM-yyyy")
private LocalDate assignedOn;
@Column(columnDefinition = "integer default 0")
private int isCompleted;
@JsonFormat(pattern = "dd-MMM-yyyy")
@Column(columnDefinition = "DEFAULT NULL")
private LocalDate completionDate;
public CourseAssignment(Course course, EmployeeBasicInfo assigneTo, EmployeeBasicInfo assignedBy, LocalDate assignedOn,
        int isCompleted, LocalDate completionDate) {
    this.course = course;
    this.assigneTo = assigneTo;
    this.assignedBy = assignedBy;
    this.assignedOn = assignedOn;
    this.isCompleted = isCompleted;
    this.completionDate = completionDate;
}

}
