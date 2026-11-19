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

@NoArgsConstructor
@Data
@Entity
@Table(name="coursecontent")
public class CourseContent {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int contentId;
@Column(nullable=false)
private int sequenceNo;
@Column(nullable = false)

private String contentTitle;
@Column(nullable = false)
private String contentDescription;
@Column(nullable = true)
private String contentFilePath;
@Column(nullable = false)
private int  fileType;
private LocalDateTime createdAt=LocalDateTime.now();
private LocalDateTime updatedAt =LocalDateTime.now();
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="createdBy")
private EmployeeBasicInfo createdBy;
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name="courseid")
private Course course;
private String originalFileName;
@Column(columnDefinition = "int default 0")
private long durationInSec;
public CourseContent(int sequenceNo, String contentTitle, String contentDescription, String contentFilePath,
        int fileType, LocalDateTime createdAt, LocalDateTime updatedAt, EmployeeBasicInfo createdBy, Course course,
        String originalFileName, long durationInSec) {
    this.sequenceNo = sequenceNo;
    this.contentTitle = contentTitle;
    this.contentDescription = contentDescription;
    this.contentFilePath = contentFilePath;
    this.fileType = fileType;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.course = course;
    this.originalFileName = originalFileName;
    this.durationInSec = durationInSec;
}

}
