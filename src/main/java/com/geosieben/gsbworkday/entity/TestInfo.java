package com.geosieben.gsbworkday.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestInfo {
    @Id
    @Column(name="id",columnDefinition = "varchar(3)")
    private String id;
    private String name;

    public TestInfo(String id,String name) {
        this.id=id;
        this.name = name;
    }

    public TestInfo() {

    }
}
