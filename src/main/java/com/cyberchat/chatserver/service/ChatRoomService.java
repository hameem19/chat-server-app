package com.cyberchat.chatserver.service;

import com.cyberchat.chatserver.entity.ChatRoom;

public interface ChatRoomService {
    ChatRoom createChatRoom(String name);

    ChatRoom getChatRoom(Long id);
}
