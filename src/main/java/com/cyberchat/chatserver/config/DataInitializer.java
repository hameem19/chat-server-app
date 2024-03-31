package com.cyberchat.chatserver.config;

import com.cyberchat.chatserver.entity.ChatRoom;
import com.cyberchat.chatserver.service.ChatRoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ChatRoomService chatRoomService;

    public DataInitializer(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @Override
    public void run(String... args) {
        chatRoomService.createChatRoom("Default Chat Room");
    }
}