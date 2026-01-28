package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.ItTicket;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface ItTicketServiceInterface {
    public ResponseEntity<Map<String,String>> raiseTicket(String issue,String description,String priority,String hostname,String anydeskid) throws MessagingException, UnsupportedEncodingException;
    public List<ItTicket> getMyTickets();
}
