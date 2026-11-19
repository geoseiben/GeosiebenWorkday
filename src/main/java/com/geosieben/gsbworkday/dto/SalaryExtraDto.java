package com.geosieben.gsbworkday.dto;

import java.time.LocalDateTime;

public interface SalaryExtraDto {
String getFirstName();
String getLastName();
int getId();
String getEID();
String getMonth();
byte[] getLopindays();
byte[] getExtraPay();
byte[] getIv(); 
LocalDateTime getUpdatedOn();
}
    