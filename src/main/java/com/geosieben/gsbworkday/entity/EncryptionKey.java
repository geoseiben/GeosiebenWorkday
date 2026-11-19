package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data 
@Entity
@Table(name="encryptionkey")
public class EncryptionKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "EID")
    private EmployeeBasicInfo employeeBasicInfo;
    @Column(name = "secret_key", length = 32)
    private byte[] secret_key;
    @Column(name = "iv", length = 32)
    private byte[] iv;
    @Column(name = "saltKey", length = 4096)
    private byte[] saltKey;
    private LocalDateTime created_at=LocalDateTime.now();

@Column(
    name = "type", 
    nullable = false,
    columnDefinition = "VARCHAR(20) CHECK (type IN ('chat', 'cmanager')) default 'chat' "
)    private String type;

public EncryptionKey(EmployeeBasicInfo employeeBasicInfo, byte[] secret_key, byte[] iv, byte[] saltKey,
        LocalDateTime created_at, String type) {
    this.employeeBasicInfo = employeeBasicInfo;
    this.secret_key = secret_key;
    this.iv = iv;
    this.saltKey = saltKey;
    this.created_at = created_at;
    this.type = type;
}
    
}

