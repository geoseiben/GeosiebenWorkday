package com.geosieben.gsbworkday.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SalaryExtraDetailsDecryptedDto {
    private int id;
private String employee;
    private String month;
    private String EID;
    private double lopindays;
    private double extraPay;
    @JsonFormat(pattern = "dd-MMM-YYYY")
    private LocalDateTime created;
    public SalaryExtraDetailsDecryptedDto(int id, String employee, String month, String eID, double lopindays,
            double extraPay, LocalDateTime created) {
        this.id = id;
        this.employee = employee;
        this.month = month;
        EID = eID;
        this.lopindays = lopindays;
        this.extraPay = extraPay;
        this.created = created;
    }
    
}
