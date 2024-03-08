package com.academy.consumer.consumer;

import com.academy.consumer.service.MessageEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatEventsConsumer {

    private final MessageEventsService messageEventsService;

    @KafkaListener(
            topics = {"chat-events"},
            groupId = "events-listener-group"
    )
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord);
        messageEventsService.processEvent(consumerRecord);
    }
}
