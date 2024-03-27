package com.cyberchat.chatserver.service.impl;

import com.cyberchat.chatserver.entity.ChatMessage;
import com.cyberchat.chatserver.mapper.ChatMessageMapper;
import com.cyberchat.chatserver.model.ChatMessageDTO;
import com.cyberchat.chatserver.model.ChatRoom;
import com.cyberchat.chatserver.repository.ChatMessageRepository;
import com.cyberchat.chatserver.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = ChatMessageMapper.fromDTO(chatMessageDTO);
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        ChatMessageDTO messageDTO = ChatMessageMapper.mapToDTO(savedMessage);
        broadcastMessage(messageDTO, chatRooms.get(savedMessage.getRoomId()));
        return messageDTO;
    }

    public ChatMessageDTO joinRoom(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = ChatMessageMapper.fromDTO(chatMessageDTO);
        String roomId = chatMessage.getRoomId();
        String sender = chatMessage.getSender();

        chatRooms.putIfAbsent(roomId, new ChatRoom(roomId));
        ChatRoom chatRoom = chatRooms.get(roomId);
        chatRoom.addParticipant(sender);

        ChatMessageDTO notification = new ChatMessageDTO(sender, roomId, sender + " has joined the room");
        broadcastMessage(notification, chatRoom);

        return chatMessageDTO;
    }

    public List<ChatMessageDTO> getChatHistory(String roomId) {
        List<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId);
        return messages.stream()
                .map(ChatMessageMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    private void broadcastMessage(ChatMessageDTO messageDTO, ChatRoom chatRoom) {
        messagingTemplate.convertAndSend("/topic/public", messageDTO);
    }
}