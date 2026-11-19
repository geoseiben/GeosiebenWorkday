package com.geosieben.gsbworkday.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor 
public class ChatList {
private int id;
private String otherUser;
private String firstName;
private String message;
private int unreadCount;
private int hour;
private int minute;
private int day;
private String image;
private int isFile;
private String fileName;
public ChatList(int id, String otherUser, String firstName, String message, int unreadCount, int hour, int minute,
        int day, String image, int isFile, String fileName) {
    this.id = id;
    this.otherUser = otherUser;
    this.firstName = firstName;
    this.message = message;
    this.unreadCount = unreadCount;
    this.hour = hour;
    this.minute = minute;
    this.day = day;
    this.image = image;
    this.isFile = isFile;
    this.fileName = fileName;
}

}
