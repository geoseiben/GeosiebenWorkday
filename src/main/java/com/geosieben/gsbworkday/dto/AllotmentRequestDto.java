package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AllotmentRequestDto {
private int rootid;
private String allotmentName;
private LocalDate starDate;
private LocalDate deadline;
private BigDecimal totalHours;
private BigDecimal productionHours;
private BigDecimal qcHours;
private String projectLeadId;
private String filePath;
public int getRootid() {
    return rootid;
}
public void setRootid(int rootid) {
    this.rootid = rootid;
}
public String getAllotmentName() {
    return allotmentName;
}
public void setAllotmentName(String allotmentName) {
    this.allotmentName = allotmentName;
}
public LocalDate getStarDate() {
    return starDate;
}
public void setStarDate(LocalDate starDate) {
    this.starDate = starDate;
}
public LocalDate getDeadline() {
    return deadline;
}
public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
}
public BigDecimal getTotalHours() {
    return totalHours;
}
public void setTotalHours(BigDecimal totalHours) {
    this.totalHours = totalHours;
}
public BigDecimal getProductionHours() {
    return productionHours;
}
public void setProductionHours(BigDecimal productionHours) {
    this.productionHours = productionHours;
}
public BigDecimal getQcHours() {
    return qcHours;
}
public void setQcHours(BigDecimal qcHours) {
    this.qcHours = qcHours;
}
public String getProjectLeadId() {
    return projectLeadId;
}
public void setProjectLeadId(String projectLeadId) {
    this.projectLeadId = projectLeadId;
}
public String getFilePath() {
    return filePath;
}
public void setFilePath(String filePath) {
    this.filePath = filePath;
}


}
