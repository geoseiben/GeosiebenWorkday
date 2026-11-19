package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "projectallocation")
@NoArgsConstructor
public class ProjectAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer allotmentid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "allottedto", referencedColumnName = "EID")
    private EmployeeBasicInfo allottedTo;

    @Column(length = 500)
    private String feederAlloted;

    @Column(precision = 8, scale = 2)
    private BigDecimal hrsAssigned;

    @Column(precision = 8, scale = 2)
    @ColumnDefault("0.00")
    private BigDecimal qcHrs = BigDecimal.valueOf(0.00);

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long elapsedTime = 0L;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long timeSpent = 0L;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long hoursRemaining = 0L;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isRunning = false;

    private Long startTime;

    @ColumnDefault("0")
    private Long pausedTime = 0L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "allottedBy", referencedColumnName = "EID")
    private EmployeeBasicInfo allottedBy;

    private LocalDateTime allotmentOn;
    private LocalDate deadline;
    private LocalDate completionDate = null;

    @ColumnDefault("0")
    private Integer status = 0;

    private LocalDateTime updatedon;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer qcAllotted = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qcAssinee", referencedColumnName = "EID")   
    private EmployeeBasicInfo qcAssinee = null;

    private LocalDate qcDeadline = null;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer isDelivered = 0;

    private LocalDate qcCompletionDate = null;

    @Column(precision = 8, scale = 2, nullable = false)
    @ColumnDefault("0.00")
    private BigDecimal deliveryHours = BigDecimal.valueOf(0);

    private LocalDate deliveryDeadline = null;
    private LocalDateTime pausedOn = null;

    @Column(length = 10)
    @ColumnDefault("'Prod'")
    private String type = "Prod";

    public ProjectAllocation(Project project, EmployeeBasicInfo allottedTo, String feederAlloted,
            BigDecimal hrsAssigned, BigDecimal qcHrs, Long elapsedTime, Long timeSpent, Long hoursRemaining,
            Boolean isRunning, Long startTime, Long pausedTime, EmployeeBasicInfo allottedBy, LocalDateTime allotmentOn,
            LocalDate deadline, LocalDate completionDate, Integer status, LocalDateTime updatedon, Integer qcAllotted,
            EmployeeBasicInfo qcAssinee, LocalDate qcDeadline, Integer isDelivered, LocalDate qcCompletionDate,
            BigDecimal deliveryHours, LocalDate deliveryDeadline, LocalDateTime pausedOn, String type) {
        this.project = project;
        this.allottedTo = allottedTo;
        this.feederAlloted = feederAlloted;
        this.hrsAssigned = hrsAssigned;
        this.qcHrs = qcHrs;
        this.elapsedTime = elapsedTime;
        this.timeSpent = timeSpent;
        this.hoursRemaining = hoursRemaining;
        this.isRunning = isRunning;
        this.startTime = startTime;
        this.pausedTime = pausedTime;
        this.allottedBy = allottedBy;
        this.allotmentOn = allotmentOn;
        this.deadline = deadline;
        this.completionDate = completionDate;
        this.status = status;
        this.updatedon = updatedon;
        this.qcAllotted = qcAllotted;
        this.qcAssinee = qcAssinee;
        this.qcDeadline = qcDeadline;
        this.isDelivered = isDelivered;
        this.qcCompletionDate = qcCompletionDate;
        this.deliveryHours = deliveryHours;
        this.deliveryDeadline = deliveryDeadline;
        this.pausedOn = pausedOn;
        this.type = type;
    }
    
}