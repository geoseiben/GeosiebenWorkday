package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public interface SubscriptionsProjection {
int getId();
String getName();
String getProvider();
String getPhone();
String getEmail();
LocalDate getStartDate();
LocalDate getExpiryDate();
String getRenewedBy();
String getSubscriptionType();
String getInvoicePath();
String getRefId();
}
