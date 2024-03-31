package com.cyberchat.chatserver.service.impl;

import com.cyberchat.chatserver.entity.ChatRoom;
import com.cyberchat.chatserver.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements com.cyberchat.chatserver.service.ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom getChatRoom(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
    }
}