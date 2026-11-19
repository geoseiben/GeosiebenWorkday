package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="adminactivities")
public class AdminActivities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String category;
    private String title;
    private String message;
    private LocalDateTime createdAt=LocalDateTime.now();
    public AdminActivities() {
    }
    public AdminActivities(int id, String category, String title, String message, LocalDateTime createdAt) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    

}
