package com.cyberchat.chatserver.mapper;

import com.cyberchat.chatserver.entity.ChatMessage;
import com.cyberchat.chatserver.model.ChatMessageDTO;

public class ChatMessageMapper {

    public static ChatMessage fromDTO(ChatMessageDTO dto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(dto.getSender());
        chatMessage.setRoomId(dto.getRoomId());
        chatMessage.setContent(dto.getContent());
        return chatMessage;
    }

    public static ChatMessageDTO mapToDTO(ChatMessage chatMessage) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setSender(chatMessage.getSender());
        dto.setRoomId(chatMessage.getRoomId());
        dto.setContent(chatMessage.getContent());
        return dto;
    }
}
