package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

@Entity
public class TestDesgn {
    @Id
    @Column(name="id",columnDefinition = "varchar(3)")
    private String id;
    private String designation;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private TestInfo testInfo;

    public TestDesgn(String designation, TestInfo testInfo) {
        this.designation = designation;
        this.testInfo = testInfo;
    }

    public TestDesgn() {

    }

    public TestInfo getTestInfo() {
        return testInfo;
    }

    public void setTestInfo(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
