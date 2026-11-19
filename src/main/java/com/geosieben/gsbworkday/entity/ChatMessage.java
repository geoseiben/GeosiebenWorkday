package com.geosieben.gsbworkday.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class ChatMessage {
    private String sender;
    private String reciever;
    private String message;

    // File / Screenshot fields
    private int isFile;
    private String fileUrl;
    private String fileName;
    private String fileType;
}


