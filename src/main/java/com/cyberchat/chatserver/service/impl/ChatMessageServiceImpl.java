package com.cyberchat.chatserver.service.impl;

import com.cyberchat.chatserver.entity.ChatMessage;
import com.cyberchat.chatserver.entity.ChatRoom;
import com.cyberchat.chatserver.exception.ChatRoomNotFoundException;
import com.cyberchat.chatserver.exception.MessageNotFoundException;
import com.cyberchat.chatserver.model.ChatMessageDTO;
import com.cyberchat.chatserver.repository.ChatMessageRepository;
import com.cyberchat.chatserver.repository.ChatRoomRepository;
import com.cyberchat.chatserver.service.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatMessageDTO sendMessage(Long chatRoomId, String messageContent, String username) {
        log.info("Sending message to chat room with ID: {}", chatRoomId);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> {
                    log.error("Chat room not found with ID: {}", chatRoomId);
                    return new ChatRoomNotFoundException("Chat room not found with ID: " + chatRoomId);
                });

        ChatMessage message = new ChatMessage(messageContent);
        message.setChatRoom(chatRoom);
        message.setUsername(username);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        log.info("Message sent successfully. Message ID: {}", savedMessage.getId());

        return new ChatMessageDTO(savedMessage.getId(), savedMessage.getContent(), savedMessage.getCreatedAt(), savedMessage.getUsername());
    }

    @Override
    public List<ChatMessageDTO> getMessagesForChatRoom(Long chatRoomId) {
        log.info("Retrieving messages for chat room with ID: {}", chatRoomId);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> {
                    log.error("Chat room not found with ID: {}", chatRoomId);
                    return new ChatRoomNotFoundException("Chat room not found with ID: " + chatRoomId);
                });

        List<ChatMessage> messages = chatRoom.getMessages();
        log.info("Retrieved {} messages for chat room with ID: {}", messages.size(), chatRoomId);

        return messages.stream()
                .map(message -> new ChatMessageDTO(message.getId(), message.getContent(), message.getCreatedAt(), message.getUsername()))
                .toList();
    }

    @Override
    public void deleteMessage(Long messageId) {
        log.info("Deleting message with ID: {}", messageId);
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> {
                    log.error("Message not found with ID: {}", messageId);
                    return new MessageNotFoundException("Message not found with ID: " + messageId);
                });

        message.setDeleted(true);
        chatMessageRepository.save(message);
        log.info("Message deleted successfully. Message ID: {}", messageId);
    }
}