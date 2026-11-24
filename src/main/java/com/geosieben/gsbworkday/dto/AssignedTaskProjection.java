package com.geosieben.gsbworkday.dto;

public interface AssignedTaskProjection {
    String getFeederAlloted();
    String getDeadline();
    String getStatus();
    Double getCompletionPercentage();
    Double getHours();
    Double getHrsAssigned();
    String getType();
}
