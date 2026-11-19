package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class SalaryResponseDecrypted {
int salaryId;
  int  salid;
  String month;
  String designation;
  double noOfDays;
  double lopDays;
  double payableDays;
    String EID;
  String firstName;
  String lastName;
  double basic;
  double conveyance;
  double medicalAllowence;
  double statutoryBonus;
  double otherAllowences;
  double otherEarnings;
  double pfEmployeeShare;
  double pfEmployerShare;
    double esiEmployeeShare;
  double esiEmployerShare;
  double pt;
  double gross;
  double netPayeble;
  String pan;
  String pfNo;
  String uan;
  String bankName;
  String accountNumber;
  @JsonFormat(pattern = "dd-MMM-YYYY")
LocalDate dateOfJoining;
  public SalaryResponseDecrypted(int salaryId, int salid, String month, String designation, double noOfDays,
      double lopDays, double payableDays, String eID, String firstName, String lastName, double basic,
      double conveyance, double medicalAllowence, double statutoryBonus, double otherAllowences, double otherEarnings,
      double pfEmployeeShare, double pfEmployerShare, double esiEmployeeShare, double esiEmployerShare, double pt,
      double gross, double netPayeble, String pan, String pfNo, String uan, String bankName, String accountNumber,
      LocalDate dateOfJoining) {
    this.salaryId = salaryId;
    this.salid = salid;
    this.month = month;
    this.designation = designation;
    this.noOfDays = noOfDays;
    this.lopDays = lopDays;
    this.payableDays = payableDays;
    EID = eID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.basic = basic;
    this.conveyance = conveyance;
    this.medicalAllowence = medicalAllowence;
    this.statutoryBonus = statutoryBonus;
    this.otherAllowences = otherAllowences;
    this.otherEarnings = otherEarnings;
    this.pfEmployeeShare = pfEmployeeShare;
    this.pfEmployerShare = pfEmployerShare;
    this.esiEmployeeShare = esiEmployeeShare;
    this.esiEmployerShare = esiEmployerShare;
    this.pt = pt;
    this.gross = gross;
    this.netPayeble = netPayeble;
    this.pan = pan;
    this.pfNo = pfNo;
    this.uan = uan;
    this.bankName = bankName;
    this.accountNumber = accountNumber;
    this.dateOfJoining = dateOfJoining;
  }

}
