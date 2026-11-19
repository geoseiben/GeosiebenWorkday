package com.geosieben.gsbworkday.dto;

import java.util.List;

import lombok.Data;
@Data
public class ResourceDto {
private String EID;
private String departmentShortName;
private String designation;
private String firstName;
private String lastName;
private List<SkillsResponse> skills;
private List<ActiveAllotments> projects;
private String status;
private String image;
private double util;
public ResourceDto(String EID, String departmentShortName, String designation, String firstName, String lastName,
        List<SkillsResponse> skills, List<ActiveAllotments> projects, String status,String image,double util) {
    this.EID = EID;
    this.departmentShortName = departmentShortName;
    this.designation = designation;
    this.firstName = firstName;
    this.lastName = lastName;
    this.skills = skills;
    this.projects = projects;
    this.status = status;
    this.image=image;
    this.util=util;
}
}
