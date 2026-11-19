package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.repository.BasicInfoRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.geosieben.gsbworkday.entity.ChatMessage;
import com.geosieben.gsbworkday.repository.ChattingRepository;
import com.geosieben.gsbworkday.service.ChattingService;

import jakarta.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
@Controller
public class ChatController {
    private final BasicInfoRepository basicInfoRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChattingRepository chattingRepository;
    private final ChattingService chattingService;
    private final HttpSession httpSession;

    public ChatController(BasicInfoRepository basicInfoRepository, SimpMessagingTemplate messagingTemplate,
            ChattingRepository chattingRepository, ChattingService chattingService,HttpSession httpSession) {
        this.basicInfoRepository = basicInfoRepository;
        this.messagingTemplate = messagingTemplate;
        this.chattingRepository = chattingRepository;
        this.chattingService = chattingService;
        this.httpSession=httpSession;
    }

@MessageMapping("/private-message")
public void sendPrivateMessage(@Payload ChatMessage message, Principal principal) throws Exception {
    String sender = principal.getName();
    message.setSender(sender);
    System.out.println(message.getIsFile());
    chattingService.saveMessage(
        message.getMessage(),
        sender,
        message.getReciever(),
        message.getIsFile()==1?true:false,
        message.getFileUrl(),
        message.getFileName()
    );

    messagingTemplate.convertAndSendToUser(
        message.getReciever(),
        "/queue/private",
        message
    );
}
@PostMapping("/uploadChatFile")
@ResponseBody
public ResponseEntity<Map<String, Object>> uploadChatFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("reciever") String reciever,
        Principal principal) throws Exception {

    String uploadDir = "uploads/chat/";
    Files.createDirectories(Paths.get(uploadDir));

    String safeOriginalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    String storedFileName = UUID.randomUUID() + "_" + safeOriginalName;
    Path targetPath = Paths.get(uploadDir).resolve(storedFileName);
    Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
   // chattingService.saveMessage("",(String)httpSession.getAttribute("eid"), reciever,  true, uploadDir + storedFileName, safeOriginalName);

    Map<String, Object> response = new HashMap<>();
    response.put("isFile", 1);
    response.put("fileUrl", "/" + uploadDir + storedFileName);
    response.put("fileName", safeOriginalName);
    response.put("fileType", file.getContentType());
    return ResponseEntity.ok(response);
}
    
}
