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
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    @Column(columnDefinition = "VARCHAR(100)")
    private String courseTitle;
    @Column(columnDefinition = "VARCHAR(4000)")
    private String courseDescription;
    @Column(columnDefinition = "VARCHAR(10)")
    private String category;
    @Column(columnDefinition = "VARCHAR(100)")
    private String courseImage;
    @Column(columnDefinition = "VARCHAR(20)")
    private String profeciencyLevel;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="createdBy")
    private EmployeeBasicInfo createdBy;
    private LocalDateTime createdAt=LocalDateTime.now();
    public Course(String courseTitle, String courseDescription, String category, String courseImage,
            String profeciencyLevel, EmployeeBasicInfo createdBy, LocalDateTime createdAt) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.category = category;
        this.courseImage = courseImage;
        this.profeciencyLevel = profeciencyLevel;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    
}
