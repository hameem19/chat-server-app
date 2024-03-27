package com.cyberchat.chatserver.service;


import com.cyberchat.chatserver.model.ChatMessageDTO;

import java.util.List;

public interface ChatService {
    ChatMessageDTO sendMessage(ChatMessageDTO chatMessage);

   ChatMessageDTO joinRoom(ChatMessageDTO chatMessage);

    List<ChatMessageDTO> getChatHistory(String roomId);
}
