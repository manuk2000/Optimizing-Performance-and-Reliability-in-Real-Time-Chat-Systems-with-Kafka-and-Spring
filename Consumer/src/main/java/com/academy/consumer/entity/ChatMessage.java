package com.academy.consumer.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChatMessage {
    @Enumerated(STRING)
    private MessageType type;
    private String content;
    private String sender;
}
