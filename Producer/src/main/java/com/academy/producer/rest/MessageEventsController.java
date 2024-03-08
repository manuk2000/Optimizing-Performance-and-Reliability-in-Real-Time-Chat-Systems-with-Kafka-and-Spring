package com.academy.producer.rest;

import com.academy.producer.payload.MessageEvent;
import com.academy.producer.producer.EventsProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageEventsController {

    private final EventsProducer eventsProducer;

    @PostMapping("/api/v1/store")
    public ResponseEntity<MessageEvent> postMessageEvent(
            @RequestBody @Validated MessageEvent messageEvent) {
        log.info("Message event to be sent: {}", messageEvent);

        eventsProducer.sendMessageEvent(messageEvent);

        log.info("After sending message event: {}", messageEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(messageEvent);
    }
}
