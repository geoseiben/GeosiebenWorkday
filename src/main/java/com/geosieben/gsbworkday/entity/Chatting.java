package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
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
@Table(name="chatting")
public class Chatting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msgid;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="fromUser")
    private EmployeeBasicInfo fromUser;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="toUser")
    private EmployeeBasicInfo toUser;
    private LocalDateTime createdAt=LocalDateTime.now();
    @Column(columnDefinition = "int default 0")
    private int isRead=0;
    @Column(columnDefinition = "int default 0")
    private int isDelivered=0;
    @Column (columnDefinition = "VARBINARY(2000)")
    private byte[] message;
    @Column (columnDefinition = "int default 0")
    @Nullable
    private int isFile;
    @Column (columnDefinition = "VARBINARY(2000)")
    private byte[] filePath;
    @Column (columnDefinition = "VARBINARY(2000)")
    private byte[] fileName;
    public Chatting(int msgid, EmployeeBasicInfo fromUser, EmployeeBasicInfo toUser, LocalDateTime createdAt,
            int isRead, int isDelivered, byte[] message, Integer isFile, byte[] filePath, byte[] fileName) {
        this.msgid = msgid;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.isDelivered = isDelivered;
        this.message = message;
        this.isFile = isFile;
        this.filePath = filePath;
        this.fileName = fileName;
    }
    
}
