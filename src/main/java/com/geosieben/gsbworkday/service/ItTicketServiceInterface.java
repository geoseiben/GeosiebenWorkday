package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.dto.ActiveTickets;
import com.geosieben.gsbworkday.entity.ItTicket;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface ItTicketServiceInterface {
    public ResponseEntity<Map<String,String>> raiseTicket(String issue,String description,String priority,String hostname,String anydeskid) throws MessagingException, UnsupportedEncodingException;
    public List<ItTicket> getMyTickets();
    public List<ActiveTickets> fetchActiveTickets();
    public ResponseEntity<Map<String, String>> updateTicket(Integer ticketid,Integer status,String remarks) throws UnsupportedEncodingException, MessagingException ;
}
