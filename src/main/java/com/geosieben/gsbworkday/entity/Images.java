package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="EID")
    private EmployeeBasicInfo employeeBasicInfo;
    private String image;
    private LocalDateTime uploadedOn =LocalDateTime.now();
    public Images(EmployeeBasicInfo employeeBasicInfo, String image, LocalDateTime uploadedOn) {
        this.employeeBasicInfo = employeeBasicInfo;
        this.image = image;
        this.uploadedOn = uploadedOn;
    }

}
