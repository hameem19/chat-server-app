package com.cyberchat.chatserver.controller;

import com.cyberchat.chatserver.model.ChatMessageDTO;
import com.cyberchat.chatserver.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessage) {
        return chatService.sendMessage(chatMessage);
    }

    @MessageMapping("/chat.joinRoom")
    @SendTo("/topic/public")
    public ChatMessageDTO joinRoom(ChatMessageDTO chatMessage) {
        return chatService.joinRoom(chatMessage);
    }

    @MessageMapping("/chat.getHistory")
    @SendTo("/topic/public")
    public List<ChatMessageDTO> getHistory(ChatMessageDTO chatMessage) {
        return chatService.getChatHistory(chatMessage.getRoomId());
    }

}