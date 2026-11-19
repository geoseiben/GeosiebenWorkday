package com.geosieben.gsbworkday.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data 
@NoArgsConstructor 
public class LeaveAvailedDto {
private String employeeName;
private String EID;
private int year;
private double clAvailed;
private double slAvailed;
private double elAvailed;
private double rhAvailed;
private double coAvailed;
private List<LeaveAvailedProjMonth> monthData=null;
public LeaveAvailedDto(String employeeName, String eID, int year, double clAvailed, double slAvailed, double elAvailed,
        double rhAvailed, double coAvailed, List<LeaveAvailedProjMonth> monthData) {
    this.employeeName = employeeName;
    EID = eID;
    this.year = year;
    this.clAvailed = clAvailed;
    this.slAvailed = slAvailed;
    this.elAvailed = elAvailed;
    this.rhAvailed = rhAvailed;
    this.coAvailed = coAvailed;
    this.monthData = monthData;
}

}
