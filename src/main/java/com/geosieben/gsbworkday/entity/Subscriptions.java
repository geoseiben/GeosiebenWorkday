package com.geosieben.gsbworkday.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor 
@Data 
@Entity 
@Table (name = "subscriptions")
public class Subscriptions {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int subscriptionId;
private String subscriptionName;
private String subscriptionProvider;
private String subscriptionType;
private String contactNumber;
private String contactEmail;
private LocalDate createdOn=LocalDate.now();

}
