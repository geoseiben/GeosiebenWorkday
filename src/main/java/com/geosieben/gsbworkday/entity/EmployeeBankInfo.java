package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data 
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
    private String IFSC;

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;

    public EmployeeBankInfo(String paymentMode, String bankName, String branchName, String accountNumber, EmployeeBasicInfo employeeBasicInfo,String IFSC) {
        this.paymentMode = paymentMode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountNumber = accountNumber;
        this.employeeBasicInfo = employeeBasicInfo;
        this.IFSC=IFSC;
    }

    public EmployeeBankInfo() {

    }
}

 
