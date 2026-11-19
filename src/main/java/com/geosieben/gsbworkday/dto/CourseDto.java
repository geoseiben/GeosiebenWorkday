package com.geosieben.gsbworkday.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class CourseDto {
private int courseId;
private String courseTitle;
private String courseDescription;
private String courseCategory;
private String lastName;
private String firstName;

private String courseImage;
private String proficiencyLevel;
private List<AssigneesDto> assignees;
public CourseDto(int courseId, String courseTitle, String courseDescription, String courseCategory, String lastName,
        String firstName, String courseImage, String proficiencyLevel, List<AssigneesDto> assignees) {
    this.courseId = courseId;
    this.courseTitle = courseTitle;
    this.courseDescription = courseDescription;
    this.courseCategory = courseCategory;
    this.lastName = lastName;
    this.firstName = firstName;
    this.courseImage = courseImage;
    this.proficiencyLevel = proficiencyLevel;
    this.assignees = assignees;
}

}
