package com.geosieben.gsbworkday.dto;

public interface HostsProjection {
String getUsername();
    String getLeaseUser();
    String getEid(); // Change data type if EID is String/UUID
    String getWorkmail();

    // Host & Lease Details
    Long getHostid();
    String getStatus();
    String getAssignmentid();
    String getLeaseid();
    String getLeaseReturned(); // Use LocalDate / LocalDateTime if timestamp
    String getLeaseEid();
    String getLeaseDocumentPath();
    String getAckFilepath();

    // System Specifications
    String getGraphics();
    String getHostname();
    String getHosttype();
    String getLanno();
    String getProcessor();
    String getRam();
    String getOs();
    String getIpAddress();

    // Asset IDs
    Long getMonitor1_assetid();
    Long getMonitor2_assetid();
    Long getMouse_assetid();
    Long getKeyboard_assetid();
    Long getEmitter_assetid();
    Long getEmitter_charger_assetid();
    Long getGoggle_assetid();
    Long getHeadphone_assetid();
    Long getCabinet_assetid();
    Long getUps_assetid();

    // Formatted Asset Names (Name + Serial)
    String getMonitor1();
    String getMonitor1vc();
    String getMonitor2();
    String getMonitor2vc();
    String getMouse();
    String getKeyboard();
    String getEmitter();
    String getGoggle();
    String getHeadphone();
    String getCabinet();
    String getCabpc();
    String getSsd();
    String getHdd();
    String getAd1();
    String getAd2();
    String getUps();
}
