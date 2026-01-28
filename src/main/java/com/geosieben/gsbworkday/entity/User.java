package com.geosieben.gsbworkday.entity;

import jakarta.persistence.*;

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

    public User(String username, String password, String role,String email,EmployeeBasicInfo employeeBasicInfo) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email=email;
        this.employeeBasicInfo=employeeBasicInfo;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeBasicInfo getEmployeeBasicInfo() {
        return employeeBasicInfo;
    }

    public void setEmployeeBasicInfo(EmployeeBasicInfo employeeBasicInfo) {
        this.employeeBasicInfo = employeeBasicInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
