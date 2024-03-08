//package com.academy.producer.producer;
//
//import com.academy.producer.payload.MessageEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.CompletableFuture;
//
//@Component
//@Slf4j
//public class EventsProducer {
//
//    private final KafkaTemplate<Integer, String> kafkaTemplate;
//    @Value("${spring.kafka.topic}")
//    public String topic;
//
//    public EventsProducer(KafkaTemplate<Integer, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendMessageEvent(MessageEvent messageEvent) {
//        int key = messageEvent.getEventKey();
//        String value = messageEvent.toString();
//
//        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topic, null, key, value);
//
//        CompletableFuture<SendResult<Integer, String>> completableFuture = kafkaTemplate.send(producerRecord);
//
//        completableFuture
//                .whenComplete((sendResult, throwable) -> {
//                    if (throwable != null) {
//                        log.error("Failed to send the message. Exception: {} ",
//                                throwable.getMessage(), throwable);
//                    } else {
//                        log.info("Successfully sent message with key: {} and value: {}, assigned to partition: {} ",
//                                key, value, sendResult.getRecordMetadata().partition());
//
//                    }
//                });
//    }
//}


package com.academy.producer.producer;

import com.academy.producer.payload.MessageEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class EventsProducer {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    @Value("${spring.kafka.topic}")
    private String topic;
    private final ObjectMapper objectMapper;

    public EventsProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageEvent(MessageEvent messageEvent) {
        int key = messageEvent.getEventKey();
        String value;
        try {
            value = objectMapper.writeValueAsString(messageEvent);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize MessageEvent to JSON: {}", e.getMessage(), e);
            return;
        }

        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topic, key, value);

        CompletableFuture<SendResult<Integer, String>> completableFuture = kafkaTemplate.send(producerRecord);

        completableFuture
                .whenComplete((sendResult, throwable) -> {
                    if (throwable != null) {
                        log.error("Failed to send the message. Exception: {} ", throwable.getMessage(), throwable);
                    } else {
                        log.info("Successfully sent message with key: {} and value: {}, assigned to partition: {} ",
                                key, value, sendResult.getRecordMetadata().partition());
                    }
                });
    }
}

