package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface SalaryDto {
int getSalaryId();
  int  getSalid();
  String getMonth();
  String getEID();
  String getFirstName();
  String getLastName();
  String getPan();
  String getPfNo();
  String getUan();
  String getBankName();
  String getAccountNumber();
  @JsonFormat(pattern = "dd-MMM-YYYY")
  LocalDate getDateOfJoining();
  byte[] getDesignation();
  byte[] getNoofdays();
  byte[] getLopDays();
  byte[] getPayableDays();
  byte[] getBasic();
  byte[] getConveyance();
  byte[] getMedicalAllowence();
  byte[] getStatutoryBonus();
  byte[] getOtherAllowences();
  byte[] getOtherEarnings();
  byte[] getPfEmployeeShare();
  byte[] getPfEmployerShare();
  byte[] getEsiEmployeeShare();
  byte[] getEsiEmployerShare();
  byte[] getPt();
  byte[] getGross();
  byte[] getNetPayeble();
  byte[] getIv();
  byte[] getSsiv();
}
