package com.academy.consumer.rest;

import com.academy.consumer.entity.ChatMessage;
import com.academy.consumer.entity.MessageEvent;
import com.academy.consumer.repository.MessageEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageEventsController {
    private final MessageEventRepository messageEventRepository;

    @GetMapping("/api/v1/message")
    public ResponseEntity<ChatMessage> postMessageEvent() {

        MessageEvent messageEvent1 = messageEventRepository.findFirstByOrderByIdDesc();
        log.info("Message event to be sent: {}", messageEvent1);

        return ResponseEntity.status(HttpStatus.CREATED).body(messageEvent1 == null ? new MessageEvent().getMessage() : messageEvent1.getMessage());
    }
}
