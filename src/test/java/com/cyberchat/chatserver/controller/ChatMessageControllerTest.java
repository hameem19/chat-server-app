package com.cyberchat.chatserver.controller;

import com.cyberchat.chatserver.model.ChatMessageDTO;
import com.cyberchat.chatserver.service.ChatMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ChatMessageControllerTest {

    @MockBean
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatMessageController chatMessageController;

    private ChatMessageDTO messageDTO;
    private List<ChatMessageDTO> messageDTOs;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        messageDTO = new ChatMessageDTO(1L, "testUser", LocalDateTime.now(),"Hello, world!");
        messageDTOs = Arrays.asList(messageDTO);
        authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
    }
    @Test
    void testSendMessage() {
        Long chatRoomId = 1L;
        String messageContent = "Hello, world!";
        String username = "testUser";

        when(chatMessageService.sendMessage(chatRoomId, messageContent, username)).thenReturn(messageDTO);

        ResponseEntity<ChatMessageDTO> response = chatMessageController.sendMessage(chatRoomId, messageContent, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messageDTO, response.getBody());
        verify(chatMessageService).sendMessage(chatRoomId, messageContent, username);
    }

    @Test
    void testGetMessagesForChatRoom() {
        Long chatRoomId = 1L;

        when(chatMessageService.getMessagesForChatRoom(chatRoomId)).thenReturn(messageDTOs);

        ResponseEntity<List<ChatMessageDTO>> response = chatMessageController.getMessagesForChatRoom(chatRoomId, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messageDTOs, response.getBody());
        verify(chatMessageService).getMessagesForChatRoom(chatRoomId);
    }

    @Test
    void testDeleteMessage() {
        Long messageId = 1L;

        ResponseEntity<Void> response = chatMessageController.deleteMessage(messageId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(chatMessageService).deleteMessage(messageId);
    }

    // Test methods remain the same as the previous example
}