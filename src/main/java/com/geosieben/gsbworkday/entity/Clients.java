
package com.geosieben.gsbworkday.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity 
@Table(name="clientdata")
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;
    private String clientId;
    private String clientName;
    private LocalDateTime createdOn=LocalDateTime.now();
   @ManyToOne
   @JoinColumn(name="addedBy")
   private EmployeeBasicInfo employeeBasicInfo;


   
   public Clients() {
}
   public Clients(String clientId, String clientName, LocalDateTime createdOn, EmployeeBasicInfo employeeBasicInfo) {
    this.clientId = clientId;
    this.clientName = clientName;
    this.createdOn = createdOn;
    this.employeeBasicInfo = employeeBasicInfo;
}

   public String getClientId() {
    return clientId;
   }
   public void setClientId(String clientId) {
    this.clientId = clientId;
   }
   public String getClientName() {
    return clientName;
   }
   public void setClientName(String clientName) {
    this.clientName = clientName;
   }
   public LocalDateTime getCreatedOn() {
    return createdOn;
   }
   public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
   }
   public EmployeeBasicInfo getEmployeeBasicInfo() {
    return employeeBasicInfo;
   }
   public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
    this.employeeBasicInfo = employeeBasicInfo;
   }
   public int getCid() {
      return cid;
   }
   public void setCid(int cid) {
      this.cid = cid;
   }

   
}
