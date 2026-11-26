package com.geosieben.gsbworkday.controller;

import org.springframework.web.bind.annotation.RestController;

import com.geosieben.gsbworkday.dto.ChatHistory;
import com.geosieben.gsbworkday.dto.ChatList;
import com.geosieben.gsbworkday.repository.ChattingRepository;
import com.geosieben.gsbworkday.service.ChattingService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
public class ChatRestController {
private final HttpSession httpSession;
private final ChattingService chattingService;


public ChatRestController( HttpSession httpSession,
        ChattingService chattingService) {
    this.httpSession = httpSession;
    this.chattingService = chattingService;
}
@GetMapping("/getChatList")
public List<ChatList> getChatList() throws Exception {
    return chattingService.getChatList((String)httpSession.getAttribute("eid"));
}
@GetMapping("/getChatHistory")
public List<ChatHistory> getChatHistory(@RequestParam("eid") String eid) throws Exception {
    return chattingService.getChatHistory((String) httpSession.getAttribute("eid"), eid) ;
}

@PutMapping("/markRead")
public ResponseEntity<Map<String,String>> markMessagesRead(@RequestParam("eid") String eid) {
   
    return chattingService.markAllRead(eid);
}
@PutMapping("/markDelivered")
public ResponseEntity<Map<String,String>> markDelivered() {
    //TODO: process PUT request
    
    return chattingService.markAllDelivered();
}

}
