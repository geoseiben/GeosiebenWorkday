package com.geosieben.gsbworkday.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaryStructureDto {
    private int salid;
    private String employeeName;
    private String EID;
    private double basicPay;
    private double conveyance;
    private double medicalAllowence;
    private double statutoryBonus;
    private double otherAllowences;
    private double pfEmployeeShare;
    private double pfEmployerShare;
    private double esiEmployeeShare;
    private double esiEmployerShare;
    private double monthlyGratuty;
    private double pt;
    private double netPay;
    private double monthlyCtc;
    private double annualCtc;
    public SalaryStructureDto(int salid, String employeeName, String eID, double basicPay, double conveyance,
            double medicalAllowence, double statutoryBonus, double otherAllowences, double pfEmployeeShare,
            double pfEmployerShare, double esiEmployeeShare, double esiEmployerShare, double monthlyGratuty, double pt,
            double netPay, double monthlyCtc, double annualCtc) {
        this.salid = salid;
        this.employeeName = employeeName;
        EID = eID;
        this.basicPay = basicPay;
        this.conveyance = conveyance;
        this.medicalAllowence = medicalAllowence;
        this.statutoryBonus = statutoryBonus;
        this.otherAllowences = otherAllowences;
        this.pfEmployeeShare = pfEmployeeShare;
        this.pfEmployerShare = pfEmployerShare;
        this.esiEmployeeShare = esiEmployeeShare;
        this.esiEmployerShare = esiEmployerShare;
        this.monthlyGratuty = monthlyGratuty;
        this.pt = pt;
        this.netPay = netPay;
        this.monthlyCtc = monthlyCtc;
        this.annualCtc = annualCtc;
    }
    
}
