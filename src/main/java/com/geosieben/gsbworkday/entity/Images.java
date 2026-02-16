package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @Column(columnDefinition = "varchar(8) not null")
    private String EID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;
}
