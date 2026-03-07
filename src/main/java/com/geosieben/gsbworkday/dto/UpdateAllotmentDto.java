package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateAllotmentDto {
private int allotmentId;
    private String assignedToId;
    private LocalDate deadline;
    private BigDecimal hrsAssigned;
    private LocalDate offlineDeadline;
    private BigDecimal offlineHrs;
    private String qcAssineeId; // Note: Kept original spelling 'qcAssineeId' from your object
    private LocalDate qcDeadline;
    private BigDecimal qcHrs;



    public UpdateAllotmentDto() {
    }
    // Getters and Setters
    public int getAllotmentId() { return allotmentId; }
    public void setAllotmentId(int allotmentId) { this.allotmentId = allotmentId; }

    public String getAssignedToId() { return assignedToId; }
    public void setAssignedToId(String assignedToId) { this.assignedToId = assignedToId; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public BigDecimal getHrsAssigned() { return hrsAssigned; }
    public void setHrsAssigned(BigDecimal hrsAssigned) { this.hrsAssigned = hrsAssigned; }

    public LocalDate getOfflineDeadline() { return offlineDeadline; }
    public void setOfflineDeadline(LocalDate offlineDeadline) { this.offlineDeadline = offlineDeadline; }

    public BigDecimal getOfflineHrs() { return offlineHrs; }
    public void setOfflineHrs(BigDecimal offlineHrs) { this.offlineHrs = offlineHrs; }

    public String getQcAssineeId() { return qcAssineeId; }
    public void setQcAssineeId(String qcAssineeId) { this.qcAssineeId = qcAssineeId; }

    public LocalDate getQcDeadline() { return qcDeadline; }
    public void setQcDeadline(LocalDate qcDeadline) { this.qcDeadline = qcDeadline; }

    public BigDecimal getQcHrs() { return qcHrs; }
    public void setQcHrs(BigDecimal qcHrs) { this.qcHrs = qcHrs; }

}
