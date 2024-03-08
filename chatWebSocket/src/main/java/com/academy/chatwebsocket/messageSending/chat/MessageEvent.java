package com.academy.chatwebsocket.messageSending.chat;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class MessageEvent {
    @NonNull
    Integer eventKey;
    @NonNull
    ChatMessage message;
}