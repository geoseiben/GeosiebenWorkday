package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data 
@NoArgsConstructor 
@Entity 
@Table (name = "subscriptionhistory")
public class SubscriptionHistory {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne 
@JoinColumn (name="subscriptionId")
private Subscriptions subscription;
private String renewalType;
private LocalDate subscriptionStartDate;
private LocalDate subscriptionExpiryDate;
private String refId;
private String invoicePath;
private LocalDateTime renewedOn=LocalDateTime.now();
@ManyToOne 
@JoinColumn (name="renewedBy")
private EmployeeBasicInfo renewedBy;
public SubscriptionHistory(Subscriptions subscription, String renewalType, LocalDate subscriptionStartDate,
        LocalDate subscriptionExpiryDate, String refId, String invoicePath, LocalDateTime renewedOn,
        EmployeeBasicInfo renewedBy) {
    this.subscription = subscription;
    this.renewalType = renewalType;
    this.subscriptionStartDate = subscriptionStartDate;
    this.subscriptionExpiryDate = subscriptionExpiryDate;
    this.refId = refId;
    this.invoicePath = invoicePath;
    this.renewedOn = renewedOn;
    this.renewedBy = renewedBy;
}

}
