package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import javax.annotation.processing.Generated;

@Entity 
@Table(name="projectcategories")
public class ProjectCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;
    private String catagoryShortname;
    private LocalDateTime createdOn=LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name="createdBy")
    private EmployeeBasicInfo employeeBasicInfo;

    @ManyToOne
    @JoinColumn(name="department")
    private Department department;

    public ProjectCategories(String categoryName, String catagoryShortname, EmployeeBasicInfo employeeBasicInfo,
            Department department) {
        this.categoryName = categoryName;
        this.catagoryShortname = catagoryShortname;
        this.employeeBasicInfo = employeeBasicInfo;
        this.department = department;
    }
    


    public ProjectCategories() {
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCatagoryShortname() {
        return catagoryShortname;
    }

    public void setCatagoryShortname(String catagoryShortname) {
        this.catagoryShortname = catagoryShortname;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public int getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    
}
