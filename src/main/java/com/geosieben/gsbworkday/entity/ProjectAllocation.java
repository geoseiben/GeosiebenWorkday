package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "projectallocation")
public class ProjectAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer allotmentid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allottedto", referencedColumnName = "EID")
    private EmployeeBasicInfo allottedTo;

    @Column(length = 500)
    private String feederAlloted;

    @Column(precision = 8, scale = 2)
    private BigDecimal hrsAssigned;

    @Column(precision = 8, scale = 2)
    private BigDecimal qcHrs = BigDecimal.valueOf(0.00);

    private Long elapsedTime = 0L;
    private Long timeSpent = 0L;
    private Long hoursRemaining = 0L;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isRunning = false;

    private Long startTime;
    private Long pausedTime = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allottedBy", referencedColumnName = "EID")
    private EmployeeBasicInfo allottedBy;

    private LocalDateTime allotmentOn;
    private LocalDate deadline;
    private LocalDate completionDate=null;

    private Integer status = 0;
    private LocalDateTime updatedon;

    private Integer qcAllotted = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qcAssinee", referencedColumnName = "EID")   
    private EmployeeBasicInfo qcAssinee=null;

    private LocalDate qcDeadline=null;
    private Integer isDelivered = 0;
    private LocalDate qcCompletionDate=null;

    @Column(precision = 8, scale = 2)
    private BigDecimal deliveryHours=null;

    private LocalDate deliveryDeadline=null;
    private LocalDateTime pausedOn=null;

    @Column(length = 10)
    private String type = "Prod";

    // --- Constructors ---

    /**
     * Required by JPA
     */
    public ProjectAllocation() {
    }


    public ProjectAllocation(Project project, EmployeeBasicInfo allottedTo, BigDecimal hrsAssigned, LocalDate deadline) {
        this.project = project;
        this.allottedTo = allottedTo;
        this.hrsAssigned = hrsAssigned;
        this.deadline = deadline;
        this.allotmentOn = LocalDateTime.now();
    }

    // --- Getters and Setters ---

    public Integer getAllotmentid() { return allotmentid; }
    public void setAllotmentid(Integer allotmentid) { this.allotmentid = allotmentid; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public EmployeeBasicInfo getAllottedTo() { return allottedTo; }
    public void setAllottedTo(EmployeeBasicInfo allottedTo) { this.allottedTo = allottedTo; }

    public String getFeederAlloted() { return feederAlloted; }
    public void setFeederAlloted(String feederAlloted) { this.feederAlloted = feederAlloted; }

    public BigDecimal getHrsAssigned() { return hrsAssigned; }
    public void setHrsAssigned(BigDecimal hrsAssigned) { this.hrsAssigned = hrsAssigned; }

    public BigDecimal getQcHrs() { return qcHrs; }
    public void setQcHrs(BigDecimal qcHrs) { this.qcHrs = qcHrs; }

    public Long getElapsedTime() { return elapsedTime; }
    public void setElapsedTime(Long elapsedTime) { this.elapsedTime = elapsedTime; }

    public Long getTimeSpent() { return timeSpent; }
    public void setTimeSpent(Long timeSpent) { this.timeSpent = timeSpent; }

    public Long getHoursRemaining() { return hoursRemaining; }
    public void setHoursRemaining(Long hoursRemaining) { this.hoursRemaining = hoursRemaining; }

    public Boolean getIsRunning() { return isRunning; }
    public void setIsRunning(Boolean isRunning) { this.isRunning = isRunning; }

    public Long getStartTime() { return startTime; }
    public void setStartTime(Long startTime) { this.startTime = startTime; }

    public Long getPausedTime() { return pausedTime; }
    public void setPausedTime(Long pausedTime) { this.pausedTime = pausedTime; }

    public EmployeeBasicInfo getAllottedBy() { return allottedBy; }
    public void setAllottedBy(EmployeeBasicInfo allottedBy) { this.allottedBy = allottedBy; }

    public LocalDateTime getAllotmentOn() { return allotmentOn; }
    public void setAllotmentOn(LocalDateTime allotmentOn) { this.allotmentOn = allotmentOn; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getUpdatedon() { return updatedon; }
    public void setUpdatedon(LocalDateTime updatedon) { this.updatedon = updatedon; }

    public Integer getQcAllotted() { return qcAllotted; }
    public void setQcAllotted(Integer qcAllotted) { this.qcAllotted = qcAllotted; }

    public EmployeeBasicInfo getQcAssinee() { return qcAssinee; }
    public void setQcAssinee(EmployeeBasicInfo qcAssinee) { this.qcAssinee = qcAssinee; }

    public LocalDate getQcDeadline() { return qcDeadline; }
    public void setQcDeadline(LocalDate qcDeadline) { this.qcDeadline = qcDeadline; }

    public Integer getIsDelivered() { return isDelivered; }
    public void setIsDelivered(Integer isDelivered) { this.isDelivered = isDelivered; }

    public LocalDate getQcCompletionDate() { return qcCompletionDate; }
    public void setQcCompletionDate(LocalDate qcCompletionDate) { this.qcCompletionDate = qcCompletionDate; }

    public BigDecimal getDeliveryHours() { return deliveryHours; }
    public void setDeliveryHours(BigDecimal deliveryHours) { this.deliveryHours = deliveryHours; }

    public LocalDate getDeliveryDeadline() { return deliveryDeadline; }
    public void setDeliveryDeadline(LocalDate deliveryDeadline) { this.deliveryDeadline = deliveryDeadline; }

    public LocalDateTime getPausedOn() { return pausedOn; }
    public void setPausedOn(LocalDateTime pausedOn) { this.pausedOn = pausedOn; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}