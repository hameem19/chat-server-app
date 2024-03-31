package com.cyberchat.chatserver.model;

import lombok.*;

import java.time.LocalDateTime;
@Data@NoArgsConstructor@AllArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String username;

}

