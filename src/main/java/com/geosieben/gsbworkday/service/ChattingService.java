package com.geosieben.gsbworkday.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.dto.ChatHistory;
import com.geosieben.gsbworkday.dto.ChatList;
import com.geosieben.gsbworkday.dto.ChatListProjection;
import com.geosieben.gsbworkday.dto.EncryptedChattingProjection;
import com.geosieben.gsbworkday.entity.Chatting;
import com.geosieben.gsbworkday.entity.EncryptionKey;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.ChattingRepository;
import com.geosieben.gsbworkday.repository.EncryptionKeyRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ChattingService {

    private final BasicInfoRepository basicInfoRepository;
    private final HttpSession httpSession;
    private final ChattingRepository chattingRepository;
    private final EncryptionKeyRepository encryptionKeyRepository;

    public ChattingService(HttpSession httpSession, ChattingRepository chattingRepository,
                           EncryptionKeyRepository encryptionKeyRepository, BasicInfoRepository basicInfoRepository) {
        this.httpSession = httpSession;
        this.chattingRepository = chattingRepository;
        this.encryptionKeyRepository = encryptionKeyRepository;
        this.basicInfoRepository = basicInfoRepository;
    }

    public ResponseEntity<Map<String, String>> markAllRead(String recipient) {
        Map<String, String> response = new HashMap<>();
        try {
            chattingRepository.markRead(recipient, (String) httpSession.getAttribute("eid"));
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> markAllDelivered() {
        Map<String, String> response = new HashMap<>();
        try {
            chattingRepository.markDelivered((String) httpSession.getAttribute("eid"));
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
        }
        return ResponseEntity.ok(response);
    }

    public List<ChatHistory> getChatHistory(String me, String eid) throws Exception {
        List<ChatHistory> response = new ArrayList<>();
        chattingRepository.markRead(me, eid);
        List<EncryptedChattingProjection> messageLists = chattingRepository.getChatHistory(me, eid);

        for (EncryptedChattingProjection msg : messageLists) {
            ChatHistory message = new ChatHistory();
            String senderEid = msg.getFromUser().equals("me")
                    ? (String) httpSession.getAttribute("eid")
                    : msg.getFromUser();

            Optional<EncryptionKey> keyOpt = encryptionKeyRepository.getCredByEidType(senderEid, "chat");

            message.setFromUser(msg.getFromUser());
            message.setToUser(msg.getToUser());
            message.setMsgId(msg.getMsgId());
            message.setCreatedAt(msg.getCreatedAt());
            message.setIsDelivered(msg.getIsDelivered());
            message.setIsRead(msg.getIsRead());
            message.setIsFile(msg.getIsFile());

            if (keyOpt.isPresent()) {
                SecretKeySpec keySpec = new SecretKeySpec(keyOpt.get().getSecret_key(), "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(keyOpt.get().getIv());

                if (msg.getMessage() != null && msg.getMessage().length > 0) {
                    message.setMessage(AddonServ.decryptCipherText(keySpec, ivSpec, msg.getMessage()));
                } else {
                    message.setMessage("");
                }

                if ( msg.getIsFile() == 1
                        && msg.getFilePath() != null && msg.getFilePath().length > 0) {
                    message.setFileUrl(AddonServ.decryptCipherText(keySpec, ivSpec, msg.getFilePath()));
                    if (msg.getFileName() != null && msg.getFileName().length > 0) {
                        message.setFileName(AddonServ.decryptCipherText(keySpec, ivSpec, msg.getFileName()));
                    } else {
                        message.setFileName("Attachment");
                    }
                }
            } else {
                message.setMessage("");
            }

            response.add(message);
        }
        return response;
    }

    public List<ChatList> getChatList(String eid) throws Exception {
        List<ChatListProjection> list = chattingRepository.getChatLists(eid);
        List<ChatList> response = new ArrayList<>();
        chattingRepository.markDelivered(eid);

        for (ChatListProjection l : list) {
            Chatting ch = chattingRepository.findById(Integer.valueOf(l.getId())).orElse(null);
            if (ch != null) {
                ChatList cl = new ChatList();
                cl.setId(l.getId());
                cl.setOtherUser(l.getOtherUser());
                cl.setFirstName(l.getFirstName());
                cl.setUnreadCount(l.getUnreadCount());
                cl.setDay(l.getDay());
                cl.setHour(l.getHour());
                cl.setMinute(l.getMinute());
                cl.setImage(l.getImage());
                cl.setIsFile(ch.getIsFile());

                EncryptionKey key = encryptionKeyRepository
                        .getCredByEidType(ch.getFromUser().getEID(), "chat")
                        .orElse(null);

                if (key != null) {
                    SecretKeySpec keySpec = new SecretKeySpec(key.getSecret_key(), "AES");
                    IvParameterSpec ivSpec = new IvParameterSpec(key.getIv());

                    if (ch.getMessage() != null && ch.getMessage().length > 0) {
                        cl.setMessage(AddonServ.decryptCipherText(keySpec, ivSpec, ch.getMessage()));
                    }

                    if ( ch.getIsFile() == 1
                            && ch.getFileName() != null && ch.getFileName().length > 0) {
                        cl.setFileName(AddonServ.decryptCipherText(keySpec, ivSpec, ch.getFileName()));
                    }
                }
                response.add(cl);
            }
        }
        return response;
    }

    public void saveMessage(String message, String fromuser, String touser,
                            boolean isFile, String filePath, String fileName) throws Exception {
        System.out.println(message+"\t"+fromuser+"\t"+touser+"\t"+isFile+"\t"+fileName+"\t"+filePath);

        Optional<EncryptionKey> keyOpt = encryptionKeyRepository.getCredByEidType(fromuser, "chat");
        byte[] iv;
        byte[] secretKey;

        if (keyOpt.isEmpty()) {
            iv = AddonServ.getIv();
            secretKey = AddonServ.getSecretKey(AddonServ.randomPasswordGenerator());

            EncryptionKey eKey = new EncryptionKey();
            eKey.setEmployeeBasicInfo(basicInfoRepository.findEmployeeBasicInfoByEID(fromuser));
            eKey.setIv(iv);
            eKey.setSecret_key(secretKey);
            eKey.setType("chat");
            encryptionKeyRepository.save(eKey);
        } else {
            EncryptionKey existingKey = keyOpt.get();
            iv = existingKey.getIv();
            secretKey = existingKey.getSecret_key();
        }

        SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Chatting chatMessage = new Chatting();
        chatMessage.setFromUser(basicInfoRepository.findEmployeeBasicInfoByEID(fromuser));
        chatMessage.setToUser(basicInfoRepository.findEmployeeBasicInfoByEID(touser));
        chatMessage.setCreatedAt(LocalDateTime.now());
        chatMessage.setIsRead(0);
        chatMessage.setIsDelivered(0);

        if (message != null && !message.trim().isEmpty()) {
            chatMessage.setMessage(AddonServ.encyptPlainText(keySpec, ivSpec, message));
        }

        if (isFile && filePath != null && !filePath.isEmpty()) {
            chatMessage.setIsFile(1);
            chatMessage.setFilePath(AddonServ.encyptPlainText(keySpec, ivSpec, filePath));
            chatMessage.setFileName(AddonServ.encyptPlainText(keySpec, ivSpec, fileName != null ? fileName : "attachment"));
        } else {
            chatMessage.setIsFile(0);
        }

        chattingRepository.save(chatMessage);
    }

    // Overloaded helper for backward compatibility with text-only callers
    public void saveMessage(String message, String fromuser, String touser) throws Exception {
        saveMessage(message, fromuser, touser, false, null, null);
    }
}