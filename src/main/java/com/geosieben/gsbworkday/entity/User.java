package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    public User(String username, String password, String role, int status, int isfirstlogin, String email,
            EmployeeBasicInfo employeeBasicInfo, String vaultPassword) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.isfirstlogin = isfirstlogin;
        this.email = email;
        this.employeeBasicInfo = employeeBasicInfo;
        this.vaultPassword = vaultPassword;
    }
    
}
