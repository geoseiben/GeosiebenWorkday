package com.geosieben.gsbworkday.dto;

import java.time.LocalDateTime;

public interface CompOffHistoryProjection {
    String getEmployeeName();
    String getApprovedBy();
    String getWorkedDate();
    String getCompOffDate();
    LocalDateTime getCreatedOn();
    int getId();

}
