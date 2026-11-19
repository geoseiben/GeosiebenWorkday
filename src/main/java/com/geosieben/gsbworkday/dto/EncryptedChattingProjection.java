package com.geosieben.gsbworkday.dto;

import java.time.LocalDateTime;

public interface EncryptedChattingProjection {
int  getMsgId();
String getFromUser();
String getToUser();
byte[] getMessage();
LocalDateTime getCreatedAt();
int getIsDelivered();
int getIsRead();
int getIsFile();
byte[] getFileName();
byte[] getFilePath();
}
