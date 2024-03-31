package com.cyberchat.chatserver.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDTO {
    private final String roomId;
    private final Set<String> participants = new HashSet<>();

    public ChatRoomDTO(String roomId) {
        this.roomId = roomId;
    }

    public void addParticipant(String participant) {
        participants.add(participant);
    }

    public void removeParticipant(String participant) {
        participants.remove(participant);
    }
}