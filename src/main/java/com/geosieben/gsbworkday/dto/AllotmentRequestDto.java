package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class AllotmentRequestDto {
private int rootid;
private String allotmentName;
private LocalDate startDate;
private LocalDate deadline;
private BigDecimal totalHours;
private BigDecimal productionHours;
private BigDecimal qcHours;
private String projectLeadId;
private String filePath;
private int category;
private int isHoursFixed;
private String area;
public AllotmentRequestDto(int rootid, String allotmentName, LocalDate startDate, LocalDate deadline,
        BigDecimal totalHours, BigDecimal productionHours, BigDecimal qcHours, String projectLeadId, String filePath,
        int category, int isHoursFixed, String area) {
    this.rootid = rootid;
    this.allotmentName = allotmentName;
    this.startDate = startDate;
    this.deadline = deadline;
    this.totalHours = totalHours;
    this.productionHours = productionHours;
    this.qcHours = qcHours;
    this.projectLeadId = projectLeadId;
    this.filePath = filePath;
    this.category = category;
    this.isHoursFixed = isHoursFixed;
    this.area = area;
}



}
