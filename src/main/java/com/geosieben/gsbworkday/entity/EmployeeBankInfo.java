package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_bank_info")
public class EmployeeBankInfo {
    @Id
    @Column(columnDefinition = "varchar(8)")
    private String EID;
    private String paymentMode;
    private String bankName;
    private String branchName;
    private String accountNumber;

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeBankInfo(String paymentMode, String bankName, String branchName, String accountNumber, EmployeeBasicInfo employeeBasicInfo) {
        this.paymentMode = paymentMode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountNumber = accountNumber;
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public EmployeeBankInfo() {

    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }
}
