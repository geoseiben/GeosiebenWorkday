package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data 
@Table(name="rootprojects")
public class RootProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projId;
    private String  projectName;
    private int projectStatus=0;
    private String projectType;
    private LocalDateTime createdOn=LocalDateTime.now();
   @ManyToOne(cascade = CascadeType.MERGE)
   @JoinColumn(name="projectLead")
   private EmployeeBasicInfo projectlead;
   @ManyToOne(cascade = CascadeType.MERGE)
   @JoinColumn(name="createdBy")
   private EmployeeBasicInfo createdBy;
     @ManyToOne(cascade = CascadeType.MERGE)
   @JoinColumn(name="category")
   private ProjectCategories category;
        @ManyToOne(cascade = CascadeType.MERGE)
   @JoinColumn(name="clientId")
   private Clients client;
private LocalDate completionDate;

   
        public RootProject() {
        }

        public RootProject(int projId, String projectName, int projectStatus, String projectType,
                LocalDateTime createdOn, EmployeeBasicInfo projectlead, EmployeeBasicInfo createdBy,
                ProjectCategories category, Clients client, LocalDate completionDate) {
            this.projId = projId;
            this.projectName = projectName;
            this.projectStatus = projectStatus;
            this.projectType = projectType;
            this.createdOn = createdOn;
            this.projectlead = projectlead;
            this.createdBy = createdBy;
            this.category = category;
            this.client = client;
            this.completionDate = completionDate;
        }

   
}
