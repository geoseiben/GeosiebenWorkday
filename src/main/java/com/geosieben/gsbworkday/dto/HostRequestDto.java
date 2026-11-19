package com.geosieben.gsbworkday.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HostRequestDto {
private String hostName;
private String hostType;
private String ipAddress;
private String lanno;
private String os;
private String processor;
private String graphics;
private String ram;
private String ssd;
private String hdd;
private String cabinet;
private String cabinet_pcable;
private String monitor1;
private String monitor1_vcable;
private String monitor2;
private String monitor2_vcable;
private String mouse;
private String keyboard;
private String emitter;
private String goggle;
private String headphones;
private String ad1;
private String ad2;
private String ups;
public HostRequestDto(String hostName, String hostType, String ipAddress, String lanno, String os, String processor,
        String graphics, String ram, String ssd, String hdd, String cabinet, String cabinet_pcable, String monitor1,
        String monitor1_vcable, String monitor2, String monitor2_vcable, String mouse, String keyboard, String emitter,
        String goggle, String headphones, String ad1, String ad2, String ups) {
    this.hostName = hostName;
    this.hostType = hostType;
    this.ipAddress = ipAddress;
    this.lanno = lanno;
    this.os = os;
    this.processor = processor;
    this.graphics = graphics;
    this.ram = ram;
    this.ssd = ssd;
    this.hdd = hdd;
    this.cabinet = cabinet;
    this.cabinet_pcable = cabinet_pcable;
    this.monitor1 = monitor1;
    this.monitor1_vcable = monitor1_vcable;
    this.monitor2 = monitor2;
    this.monitor2_vcable = monitor2_vcable;
    this.mouse = mouse;
    this.keyboard = keyboard;
    this.emitter = emitter;
    this.goggle = goggle;
    this.headphones = headphones;
    this.ad1 = ad1;
    this.ad2 = ad2;
    this.ups = ups;
}

}
