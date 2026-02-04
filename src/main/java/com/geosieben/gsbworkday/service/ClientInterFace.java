package com.geosieben.gsbworkday.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.entity.Clients;

public interface ClientInterFace {
public ResponseEntity<Map<String,String>> saveclientInfo(String clientid,String clientName);
public List<Clients> getClients();
}
