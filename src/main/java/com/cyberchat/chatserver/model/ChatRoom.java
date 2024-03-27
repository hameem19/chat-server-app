package com.cyberchat.chatserver.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private final String roomId;
    private final Set<String> participants = new HashSet<>();

    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public void addParticipant(String participant) {
        participants.add(participant);
    }

    public void removeParticipant(String participant) {
        participants.remove(participant);
    }
}