package com.geosieben.gsbworkday.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.*;

@ControllerAdvice
public class GlobalChatControllerAdvice {

    @ModelAttribute
    public void addChatAttributes(Model model) {
        // In a real app, fetch these from your ChatService
        List<Map<String, Object>> chats = new ArrayList<>();
        chats.add(createChat("Alex Rivera", "https://i.pravatar.cc/150?u=1", "Design is ready", "10:30", true));
        chats.add(createChat("Sarah Chen", "https://i.pravatar.cc/150?u=2", "Did you see the mail?", "09:15", false));

        model.addAttribute("globalChats", chats);
        model.addAttribute("unreadCount", 2);
    }

    private Map<String, Object> createChat(String name, String avatar, String msg, String time, boolean online) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("avatarUrl", avatar);
        map.put("lastMessage", msg);
        map.put("lastTime", time);
        map.put("online", online);
        return map;
    }
}