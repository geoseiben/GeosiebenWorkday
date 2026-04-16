package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PayRollRequest {
private BigDecimal basic;
private BigDecimal annual;
private BigDecimal bonus;
private BigDecimal conveyance;
private BigDecimal esiEmployee;
private BigDecimal esiEmployer;
private BigDecimal gratuityMonthly;
private BigDecimal grossMonthly;
private BigDecimal medical;
private BigDecimal monthly;
private BigDecimal netPay;
private String name;
private BigDecimal other;
private BigDecimal pfEmployee;
private BigDecimal pfEmployer;
private BigDecimal pt;
}
