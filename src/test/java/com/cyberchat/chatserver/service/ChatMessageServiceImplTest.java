package com.cyberchat.chatserver.service;

import com.cyberchat.chatserver.entity.ChatMessage;
import com.cyberchat.chatserver.entity.ChatRoom;
import com.cyberchat.chatserver.exception.ChatRoomNotFoundException;
import com.cyberchat.chatserver.exception.MessageNotFoundException;
import com.cyberchat.chatserver.model.ChatMessageDTO;
import com.cyberchat.chatserver.repository.ChatMessageRepository;
import com.cyberchat.chatserver.repository.ChatRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class ChatMessageServiceImplTest {

    @MockBean
    private ChatMessageRepository chatMessageRepository;

    @MockBean
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageService chatMessageService;

    private ChatRoom chatRoom;
    private ChatMessage chatMessage;

    @BeforeEach
    void setUp() {
        chatRoom = new ChatRoom();
        chatMessage = new ChatMessage(1L, "Hello, world!", LocalDateTime.now(), "testUser",false,chatRoom);
        chatMessage.setChatRoom(chatRoom);
        chatRoom.getMessages().add(chatMessage);
    }

    @Test
    void testSendMessage() {
        Long chatRoomId = 1L;
        String messageContent = "Hello, Hameem!";
        String username = "testUser";

        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(chatRoom));
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        ChatMessageDTO result = chatMessageService.sendMessage(chatRoomId, messageContent, username);

        assertEquals(chatMessage.getId(), result.getId());
        assertEquals(chatMessage.getContent(), result.getContent());
        assertEquals(chatMessage.getUsername(), result.getUsername());
        verify(chatRoomRepository, times(1)).findById(chatRoomId);
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    void testSendMessageToChatRoomNotFound() {
        Long chatRoomId = 2L;
        String messageContent = "Hello, Hameem!";
        String username = "testUser";

        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.empty());

        assertThrows(ChatRoomNotFoundException.class, () -> chatMessageService.sendMessage(chatRoomId, messageContent, username));
        verify(chatRoomRepository, times(1)).findById(chatRoomId);
        verify(chatMessageRepository, never()).save(any(ChatMessage.class));
    }

    @Test
    void testGetMessagesForChatRoom() {
        Long chatRoomId = 1L;
        List<ChatMessage> messages = Arrays.asList(chatMessage);

        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(chatRoom));

        List<ChatMessageDTO> result = chatMessageService.getMessagesForChatRoom(chatRoomId);

        assertEquals(1, result.size());
        assertEquals(chatMessage.getId(), result.get(0).getId());
        assertEquals(chatMessage.getContent(), result.get(0).getContent());
        assertEquals(chatMessage.getUsername(), result.get(0).getUsername());
        verify(chatRoomRepository, times(1)).findById(chatRoomId);
    }

    @Test
    void testGetMessagesForChatRoomNotFound() {
        Long chatRoomId = 2L;

        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.empty());

        assertThrows(ChatRoomNotFoundException.class, () -> chatMessageService.getMessagesForChatRoom(chatRoomId));
        verify(chatRoomRepository, times(1)).findById(chatRoomId);
    }

    @Test
    void testDeleteMessage() {
        Long messageId = 1L;

        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.of(chatMessage));

        chatMessageService.deleteMessage(messageId);

        verify(chatMessageRepository, times(1)).findById(messageId);
        verify(chatMessageRepository, times(1)).save(chatMessage);
        assert(chatMessage.isDeleted());
    }

    @Test
    void testDeleteMessageNotFound() {
        Long messageId = 2L;

        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class, () -> chatMessageService.deleteMessage(messageId));
        verify(chatMessageRepository, times(1)).findById(messageId);
        verify(chatMessageRepository, never()).save(any(ChatMessage.class));
    }
}