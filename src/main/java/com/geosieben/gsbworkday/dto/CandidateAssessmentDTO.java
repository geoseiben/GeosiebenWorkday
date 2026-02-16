package com.geosieben.gsbworkday.dto;
import java.time.LocalDateTime;

public class CandidateAssessmentDTO {

    private Integer cid;
    private Integer assesmentid;
    private String candidateName;
    private String email;
    private String phone;
    private String degree;
    private Double expinyears;
    private String fieldOfStudy;
    private String specialization;
    private String yearOfPassout; // Changed to String to match your Candidate Entity
    private Integer status;
    private LocalDateTime aptitudeScheduledOn;
    private Double aptitudeScore;
    private LocalDateTime technicalScheduledOn;
    private Double technicalScore;

    // IMPORTANT: The order of arguments must EXACTLY match the @SqlResultSetMapping
    public CandidateAssessmentDTO(
            Integer cid, 
            Integer assesmentid, 
            String candidateName, 
            String email, 
            String phone, 
            String degree, 
            Double expinyears, 
            String fieldOfStudy, 
            String specialization, 
            String yearOfPassout, 
            Integer status, 
            LocalDateTime aptitudeScheduledOn, 
            Double aptitudeScore, 
            LocalDateTime technicalScheduledOn, 
            Double technicalScore) {
        this.cid = cid;
        this.assesmentid = assesmentid;
        this.candidateName = candidateName;
        this.email = email;
        this.phone = phone;
        this.degree = degree;
        this.expinyears = expinyears;
        this.fieldOfStudy = fieldOfStudy;
        this.specialization = specialization;
        this.yearOfPassout = yearOfPassout;
        this.status = status;
        this.aptitudeScheduledOn = aptitudeScheduledOn;
        this.aptitudeScore = aptitudeScore;
        this.technicalScheduledOn = technicalScheduledOn;
        this.technicalScore = technicalScore;
    }

    // Getters and Setters (Standard Boilerplate)
    public Integer getCid() { return cid; }
    public void setCid(Integer cid) { this.cid = cid; }

    public Integer getAssesmentid() { return assesmentid; }
    public void setAssesmentid(Integer assesmentid) { this.assesmentid = assesmentid; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public Double getExpinyears() { return expinyears; }
    public void setExpinyears(Double expinyears) { this.expinyears = expinyears; }

    public String getFieldOfStudy() { return fieldOfStudy; }
    public void setFieldOfStudy(String fieldOfStudy) { this.fieldOfStudy = fieldOfStudy; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getYearOfPassout() { return yearOfPassout; }
    public void setYearOfPassout(String yearOfPassout) { this.yearOfPassout = yearOfPassout; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getAptitudeScheduledOn() { return aptitudeScheduledOn; }
    public void setAptitudeScheduledOn(LocalDateTime aptitudeScheduledOn) { this.aptitudeScheduledOn = aptitudeScheduledOn; }

    public Double getAptitudeScore() { return aptitudeScore; }
    public void setAptitudeScore(Double aptitudeScore) { this.aptitudeScore = aptitudeScore; }

    public LocalDateTime getTechnicalScheduledOn() { return technicalScheduledOn; }
    public void setTechnicalScheduledOn(LocalDateTime technicalScheduledOn) { this.technicalScheduledOn = technicalScheduledOn; }

    public Double getTechnicalScore() { return technicalScore; }
    public void setTechnicalScore(Double technicalScore) { this.technicalScore = technicalScore; }
}