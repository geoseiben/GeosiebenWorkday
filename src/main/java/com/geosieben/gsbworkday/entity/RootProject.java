package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="rootprojects")
public class RootProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projId;
    private String  projectName;
    private int projectStatus=0;
    private String projectType;
    private LocalDateTime createdOn=LocalDateTime.now();
   @ManyToOne
   @JoinColumn(name="projectLead")
   private EmployeeBasicInfo projectlead;
   @ManyToOne
   @JoinColumn(name="createdBy")
   private EmployeeBasicInfo createdBy;
     @ManyToOne
   @JoinColumn(name="category")
   private ProjectCategories category;
        @ManyToOne
   @JoinColumn(name="clientId")
   private Clients client;

   
        public RootProject() {
        }
        public RootProject(String projectName, int projectStatus, String projectType, LocalDateTime createdOn,
                EmployeeBasicInfo projectlead, EmployeeBasicInfo createdBy, ProjectCategories category,
                Clients client) {
            this.projectName = projectName;
            this.projectStatus = projectStatus;
            this.projectType = projectType;
            this.createdOn = createdOn;
            this.projectlead = projectlead;
            this.createdBy = createdBy;
            this.category = category;
            this.client = client;
        }
        public int getProjId() {
            return projId;
        }
        public void setProjId(int projId) {
            this.projId = projId;
        }
        public String getProjectName() {
            return projectName;
        }
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
        public int getProjectStatus() {
            return projectStatus;
        }
        public void setProjectStatus(int projectStatus) {
            this.projectStatus = projectStatus;
        }
        public String getProjectType() {
            return projectType;
        }
        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }
        public LocalDateTime getCreatedOn() {
            return createdOn;
        }
        public void setCreatedOn(LocalDateTime createdOn) {
            this.createdOn = createdOn;
        }
        public EmployeeBasicInfo getProjectlead() {
            return projectlead;
        }
        public void setProjectlead(EmployeeBasicInfo projectlead) {
            this.projectlead = projectlead;
        }
        public EmployeeBasicInfo getCreatedBy() {
            return createdBy;
        }
        public void setCreatedBy(EmployeeBasicInfo createdBy) {
            this.createdBy = createdBy;
        }
        public ProjectCategories getCategory() {
            return category;
        }
        public void setCategory(ProjectCategories category) {
            this.category = category;
        }
        public Clients getClient() {
            return client;
        }
        public void setClient(Clients client) {
            this.client = client;
        }
       
   
}
