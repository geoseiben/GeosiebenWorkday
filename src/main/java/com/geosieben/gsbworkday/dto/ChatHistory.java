package com.geosieben.gsbworkday.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor 
@Data
public class ChatHistory {

    private int msgId;
    private LocalDateTime createdAt;
    private String message;
    private String fromUser;
    private String toUser;
    private int isDelivered;
    private int isRead;
    private int isFile;
    private String fileName;
    private String fileUrl;
    public ChatHistory(int msgId, LocalDateTime createdAt, String message, String fromUser, String toUser,
            int isDelivered, int isRead, int isFile, String fileName, String fileUrl) {
        this.msgId = msgId;
        this.createdAt = createdAt;
        this.message = message;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.isDelivered = isDelivered;
        this.isRead = isRead;
        this.isFile = isFile;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}
