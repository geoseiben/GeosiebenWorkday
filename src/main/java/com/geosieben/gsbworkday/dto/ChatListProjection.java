package com.geosieben.gsbworkday.dto;

public interface ChatListProjection {
int getId();
String getOtherUser();
String getFirstName();
int getUnreadCount();
int getHour();
int getMinute();
int getDay();
byte[] getMessage();
String getImage();
int getIsFile();
}
