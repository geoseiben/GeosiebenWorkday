package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="assesmentdata")
public class Assessment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int  assesmentid;
@ManyToOne
@JoinColumn(name="candidateid")
private Candidate candidate;
private LocalDateTime  aptitudeScheduledOn;
@Column(columnDefinition = "DECIMAL(4,1)")
private double aptitudeScore;
private LocalDateTime  technicalScheduledOn;
@Column(columnDefinition = "DECIMAL(4,1)")
private double technicalScore;
private int status=0;
private LocalDateTime documentationScheduledOn=null;
private LocalDateTime onboardScheduledOn=null;

public Assessment() {
}
public Assessment(int assesmentid, Candidate candidate, LocalDateTime aptitudeScheduledOn, double aptitudeScore,
        LocalDateTime technicalScheduledOn, double technicalScore, int status,LocalDateTime documentationScheduledOn,LocalDateTime onboardScheduledOn) {
    this.assesmentid = assesmentid;
    this.candidate = candidate;
    this.aptitudeScheduledOn = aptitudeScheduledOn;
    this.aptitudeScore = aptitudeScore;
    this.technicalScheduledOn = technicalScheduledOn;
    this.technicalScore = technicalScore;
    this.status = status;
    this.documentationScheduledOn=documentationScheduledOn;
    this.onboardScheduledOn=onboardScheduledOn;
}
public int getAssesmentid() {
    return assesmentid;
}
public void setAssesmentid(int assesmentid) {
    this.assesmentid = assesmentid;
}
public Candidate getCandidate() {
    return candidate;
}
public void setCandidate(Candidate candidate) {
    this.candidate = candidate;
}
public LocalDateTime getAptitudeScheduledOn() {
    return aptitudeScheduledOn;
}
public void setAptitudeScheduledOn(LocalDateTime aptitudeScheduledOn) {
    this.aptitudeScheduledOn = aptitudeScheduledOn;
}
public double getAptitudeScore() {
    return aptitudeScore;
}
public void setAptitudeScore(double aptitudeScore) {
    this.aptitudeScore = aptitudeScore;
}
public LocalDateTime getTechnicalScheduledOn() {
    return technicalScheduledOn;
}
public void setTechnicalScheduledOn(LocalDateTime technicalScheduledOn) {
    this.technicalScheduledOn = technicalScheduledOn;
}
public double getTechnicalScore() {
    return technicalScore;
}
public void setTechnicalScore(double technicalScore) {
    this.technicalScore = technicalScore;
}
public int getStatus() {
    return status;
}
public void setStatus(int status) {
    this.status = status;
}
public LocalDateTime getDocumentationScheduledOn() {
    return documentationScheduledOn;
}
public void setDocumentationScheduledOn(LocalDateTime documentationScheduledOn) {
    this.documentationScheduledOn = documentationScheduledOn;
}
public LocalDateTime getOnboardScheduledOn() {
    return onboardScheduledOn;
}
public void setOnboardScheduledOn(LocalDateTime onboardScheduledOn) {
    this.onboardScheduledOn = onboardScheduledOn;
}

}
