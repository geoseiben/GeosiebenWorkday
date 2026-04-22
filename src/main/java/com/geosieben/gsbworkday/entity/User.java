package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @Column(columnDefinition = "varchar(8)")
    private String password;
    @Column(
            name="role",
            columnDefinition = "varchar(10) check(role in('hr','admin','user','it'))",
            nullable = false
    )
    private String role;
    private int status =1;
    private int isfirstlogin=1;
    private String email;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="EID")
    private EmployeeBasicInfo employeeBasicInfo;
    private String vaultPassword=null;
}
