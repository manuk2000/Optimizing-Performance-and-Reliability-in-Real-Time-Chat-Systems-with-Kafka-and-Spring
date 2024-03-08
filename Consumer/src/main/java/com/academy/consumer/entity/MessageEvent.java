package com.academy.consumer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MessageEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    Integer eventKey;
    @Embedded
    ChatMessage message;
}