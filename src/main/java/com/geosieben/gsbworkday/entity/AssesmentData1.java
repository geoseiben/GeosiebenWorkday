package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
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
@Table (name="assesmentdata1")
public class AssesmentData1 {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int assesmentId;
@ManyToOne 
@JoinColumn(name="candidateId")
private Candidate candidate;
private int status;
private LocalDateTime aptitudeScheduledOn;
private int aptitudeScore;
private int aptitudeStatus;
private String aptitudeRemarks;
private LocalDateTime technicalScheduledOn;
private int technicalScore;
private int technicalStatus;
private String technicalRemarks;
private LocalDateTime documentationScheduledOn;
private int documentationStatus;
private String documentationRemarks;
private LocalDateTime offerLetterGeneratedOn;
private int offerLetterAcceptenceStatus;
private String offerLetterAcceptanceRemarks;
private LocalDateTime onboardingScheduledOn;
private LocalDate onboardingDate;
private int onboardScheduleStatus;
private String onboardScheduleRemarks;
private LocalDate onboardedOn;
private int onboardingStatus;
private String onboardingRemarks;
private String aptitudeSkipRemarks;
private String technicalSkipRemarks;
@ManyToOne 
@JoinColumn(name="techConductedBy")
private EmployeeBasicInfo techConductedBy;



public AssesmentData1() {
}



public AssesmentData1(int assesmentId, Candidate candidate, int status, LocalDateTime aptitudeScheduledOn,
        int aptitudeScore, int aptitudeStatus, String aptitudeRemarks, LocalDateTime technicalScheduledOn,
        int technicalScore, int technicalStatus, String technicalRemarks, LocalDateTime documentationScheduledOn,
        int documentationStatus, String documentationRemarks, LocalDateTime offerLetterGeneratedOn,
        int offerLetterAcceptenceStatus, String offerLetterAcceptanceRemarks, LocalDateTime onboardingScheduledOn,
        LocalDate onboardingDate, int onboardScheduleStatus, String onboardScheduleRemarks, LocalDate onboardedOn,
        int onboardingStatus, String onboardingRemarks, String aptitudeSkipRemarks, String technicalSkipRemarks,
        EmployeeBasicInfo techConductedBy) {
    this.assesmentId = assesmentId;
    this.candidate = candidate;
    this.status = status;
    this.aptitudeScheduledOn = aptitudeScheduledOn;
    this.aptitudeScore = aptitudeScore;
    this.aptitudeStatus = aptitudeStatus;
    this.aptitudeRemarks = aptitudeRemarks;
    this.technicalScheduledOn = technicalScheduledOn;
    this.technicalScore = technicalScore;
    this.technicalStatus = technicalStatus;
    this.technicalRemarks = technicalRemarks;
    this.documentationScheduledOn = documentationScheduledOn;
    this.documentationStatus = documentationStatus;
    this.documentationRemarks = documentationRemarks;
    this.offerLetterGeneratedOn = offerLetterGeneratedOn;
    this.offerLetterAcceptenceStatus = offerLetterAcceptenceStatus;
    this.offerLetterAcceptanceRemarks = offerLetterAcceptanceRemarks;
    this.onboardingScheduledOn = onboardingScheduledOn;
    this.onboardingDate = onboardingDate;
    this.onboardScheduleStatus = onboardScheduleStatus;
    this.onboardScheduleRemarks = onboardScheduleRemarks;
    this.onboardedOn = onboardedOn;
    this.onboardingStatus = onboardingStatus;
    this.onboardingRemarks = onboardingRemarks;
    this.aptitudeSkipRemarks = aptitudeSkipRemarks;
    this.technicalSkipRemarks = technicalSkipRemarks;
    this.techConductedBy = techConductedBy;
}


}
