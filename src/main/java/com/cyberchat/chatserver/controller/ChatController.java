package com.cyberchat.chatserver.controller;

import com.cyberchat.chatserver.model.ChatMessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {

    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{chatRoomId}")
    public void processMessage(@DestinationVariable Long chatRoomId, @Payload ChatMessageDTO messageDTO) {
        //TODO
        // Process the received message
        // Save the message to the database or perform any other necessary operations

        // Broadcast the message to all subscribers of the chat room topic
        messagingTemplate.convertAndSend("/topic/chat/" + chatRoomId, messageDTO);
    }
}