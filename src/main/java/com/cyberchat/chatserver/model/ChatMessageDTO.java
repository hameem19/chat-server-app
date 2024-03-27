package com.cyberchat.chatserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class ChatMessageDTO {
    private String sender;
    private String roomId;
    private String content;
}

