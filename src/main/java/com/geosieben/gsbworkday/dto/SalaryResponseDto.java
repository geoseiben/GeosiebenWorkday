package com.geosieben.gsbworkday.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalaryResponseDto {
    private int salaryId;
    private int salid;
    private String EID;
    private String firstname;
    private String lastname;
    private byte[] designation;
    private byte[] ssiv;
    private String aadhar;
    private String pan;
    private String pfNo;
    private String accountNumber;
    private String bankName;
    private byte[] basic;
    private byte[] conveyance;
    private byte[] esiEmployeeShare;
    private byte[] esiEmployerShare;
    private byte[] gratuty;
    private byte[] gross;
    private byte[] iv;
    private byte[] lopdays;
    private byte[] medicalAllowence;
    private String month;
    private byte[] netPayeble;
    private byte[] noofdays;
    private byte[] otherAllowences;
    private byte[] otherEarnings;
    private byte[] payableDays;
    private byte[] pfEmployeeShare;
    private byte[] pfEmployerShare;
    private byte[] pt;
    private byte[] statutoryBonus;
    private int status;
    public SalaryResponseDto(int salaryId, int salid, String eID, String firstname, String lastname, byte[] designation,
            byte[] ssiv, String aadhar, String pan, String pfNo, String accountNumber, String bankName, byte[] basic,
            byte[] conveyance, byte[] esiEmployeeShare, byte[] esiEmployerShare, byte[] gratuty, byte[] gross,
            byte[] iv, byte[] lopdays, byte[] medicalAllowence, String month, byte[] netPayeble, byte[] noofdays,
            byte[] otherAllowences, byte[] otherEarnings, byte[] payableDays, byte[] pfEmployeeShare,
            byte[] pfEmployerShare, byte[] pt, byte[] statutoryBonus, int status) {
        this.salaryId = salaryId;
        this.salid = salid;
        EID = eID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.designation = designation;
        this.ssiv = ssiv;
        this.aadhar = aadhar;
        this.pan = pan;
        this.pfNo = pfNo;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.basic = basic;
        this.conveyance = conveyance;
        this.esiEmployeeShare = esiEmployeeShare;
        this.esiEmployerShare = esiEmployerShare;
        this.gratuty = gratuty;
        this.gross = gross;
        this.iv = iv;
        this.lopdays = lopdays;
        this.medicalAllowence = medicalAllowence;
        this.month = month;
        this.netPayeble = netPayeble;
        this.noofdays = noofdays;
        this.otherAllowences = otherAllowences;
        this.otherEarnings = otherEarnings;
        this.payableDays = payableDays;
        this.pfEmployeeShare = pfEmployeeShare;
        this.pfEmployerShare = pfEmployerShare;
        this.pt = pt;
        this.statutoryBonus = statutoryBonus;
        this.status = status;
    }

}
