package com.cyberchat.chatserver.service;

import com.cyberchat.chatserver.entity.ChatMessage;
import com.cyberchat.chatserver.entity.ChatRoom;
import com.cyberchat.chatserver.model.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {
    ChatMessageDTO sendMessage(Long chatRoomId, String messageContent,String username);

    List<ChatMessageDTO> getMessagesForChatRoom(Long chatRoomId);

    void deleteMessage(Long messageId);
}
