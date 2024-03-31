package com.cyberchat.chatserver.controller;

import com.cyberchat.chatserver.model.ChatMessageDTO;
import com.cyberchat.chatserver.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Slf4j
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/{chatRoomId}")
    public ResponseEntity<ChatMessageDTO> sendMessage(@PathVariable Long chatRoomId,
                                                      @RequestBody String messageContent, Authentication authentication) {
        String username = authentication.getName();
        ChatMessageDTO message = chatMessageService.sendMessage(chatRoomId, messageContent, username);
        log.info("Response :: " + message);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesForChatRoom(@PathVariable Long chatRoomId, Authentication authentication) {
        List<ChatMessageDTO> messages = chatMessageService.getMessagesForChatRoom(chatRoomId);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        chatMessageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}