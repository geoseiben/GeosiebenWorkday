package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

// DTO Import
import com.geosieben.gsbworkday.dto.CandidateAssessmentDTO;

// Standard JPA Annotations
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Native Query Mapping Annotations (The ones you were missing)
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.ColumnResult;
@NamedNativeQuery(
    name = "find_candidate_details_dto", // The name used in the Repository
    query = "SELECT c.candidateid as cid, a.assesmentid, c.candidateName, c.email, c.phone, " +
            "c.degree, c.expinyears, c.fieldOfStudy, c.specialization, c.yearOfPassout, " +
            "a.status, a.aptitudeScheduledOn, a.aptitudeScore, a.technicalScheduledOn, a.technicalScore " +
            "FROM candidatedate c LEFT JOIN assesmentdata a ON c.candidateid = a.candidateid",
    resultSetMapping = "Mapping.CandidateAssessmentDTO"
)
@SqlResultSetMapping(
    name = "Mapping.CandidateAssessmentDTO",
    classes = @ConstructorResult(
        targetClass = CandidateAssessmentDTO.class,
        columns = {
            @ColumnResult(name = "cid", type = Integer.class),
            @ColumnResult(name = "assesmentid", type = Integer.class), // Changed from Long to Integer
            @ColumnResult(name = "candidateName"),
            @ColumnResult(name = "email"),
            @ColumnResult(name = "phone"),
            @ColumnResult(name = "degree"),
            @ColumnResult(name = "expinyears", type = Double.class),
            @ColumnResult(name = "fieldOfStudy"),
            @ColumnResult(name = "specialization"),
            @ColumnResult(name = "yearOfPassout"),
            @ColumnResult(name = "status", type = Integer.class),      // Changed from String to Integer
            @ColumnResult(name = "aptitudeScheduledOn", type = LocalDateTime.class),
            @ColumnResult(name = "aptitudeScore", type = Double.class),
            @ColumnResult(name = "technicalScheduledOn", type = LocalDateTime.class),
            @ColumnResult(name = "technicalScore", type = Double.class)
        }
    )
)
@Entity
@Table(name="candidatedate")
public class Candidate {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int candidateid;
private String candidateName;
private String email;
private String phone;
private String degree;
private String yearOfPassout;
private String fieldOfStudy;
@Column(columnDefinition = "DECIMAL(3,1)")
private double expinyears; 
private String specialization;
private String resumeFilepath;
private String location;
private LocalDateTime createdOn=LocalDateTime.now();
@ManyToOne
@JoinColumn(name="createdBy")
private EmployeeBasicInfo createdBy;


public Candidate() {
}
public Candidate(int candidateid, String candidateName, String email, String phone, String degree, String yearOfPassout,
        String fieldOfStudy, double expinyears, String specialization, String resumeFilepath, LocalDateTime createdOn,
        EmployeeBasicInfo createdBy,String location) {
    this.candidateid = candidateid;
    this.candidateName = candidateName;
    this.email = email;
    this.phone = phone;
    this.degree = degree;
    this.yearOfPassout = yearOfPassout;
    this.fieldOfStudy = fieldOfStudy;
    this.expinyears = expinyears;
    this.specialization = specialization;
    this.resumeFilepath = resumeFilepath;
    this.createdOn = createdOn;
    this.createdBy = createdBy;
    this.location=location;
}
public int getCandidateid() {
    return candidateid;
}
public void setCandidateid(int candidateid) {
    this.candidateid = candidateid;
}
public String getCandidateName() {
    return candidateName;
}
public void setCandidateName(String candidateName) {
    this.candidateName = candidateName;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPhone() {
    return phone;
}
public void setPhone(String phone) {
    this.phone = phone;
}
public String getDegree() {
    return degree;
}
public void setDegree(String degree) {
    this.degree = degree;
}
public String getYearOfPassout() {
    return yearOfPassout;
}
public void setYearOfPassout(String yearOfPassout) {
    this.yearOfPassout = yearOfPassout;
}
public String getFieldOfStudy() {
    return fieldOfStudy;
}
public void setFieldOfStudy(String fieldOfStudy) {
    this.fieldOfStudy = fieldOfStudy;
}
public double getExpinyears() {
    return expinyears;
}
public void setExpinyears(double expinyears) {
    this.expinyears = expinyears;
}
public String getSpecialization() {
    return specialization;
}
public void setSpecialization(String specialization) {
    this.specialization = specialization;
}
public String getResumeFilepath() {
    return resumeFilepath;
}
public void setResumeFilepath(String resumeFilepath) {
    this.resumeFilepath = resumeFilepath;
}
public LocalDateTime getCreatedOn() {
    return createdOn;
}
public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
}
public EmployeeBasicInfo getCreatedBy() {
    return createdBy;
}
public void setCreatedBy(EmployeeBasicInfo createdBy) {
    this.createdBy = createdBy;
}
public String getLocation() {
    return location;
}
public void setLocation(String location) {
    this.location = location;
}


}
