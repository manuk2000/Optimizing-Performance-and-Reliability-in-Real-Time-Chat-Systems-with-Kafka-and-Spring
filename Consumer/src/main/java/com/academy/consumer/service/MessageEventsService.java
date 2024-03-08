package com.academy.consumer.service;

import com.academy.consumer.entity.MessageEvent;
import com.academy.consumer.repository.MessageEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageEventsService {

    private final ObjectMapper objectMapper;
    private final MessageEventRepository messageEventRepository;

    public MessageEvent deserializeMessageEvent(String json) {
        try {
            return objectMapper.readValue(json, MessageEvent.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON to MessageEvent: {}", e.getMessage(), e);
            return null;
        }
    }

    public void processEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        String value = consumerRecord.value();
        MessageEvent messageEvent = deserializeMessageEvent(value);
        log.info("MessageEvent: {} ", messageEvent);

        save(messageEvent);
    }


    private void save(MessageEvent messageEvent) {
        messageEventRepository.save(messageEvent);
        log.info("Successfully saved : {} ", messageEvent);
    }
}
