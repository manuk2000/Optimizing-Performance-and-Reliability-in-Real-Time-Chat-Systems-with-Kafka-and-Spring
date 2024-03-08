package com.academy.chatwebsocket.messageSending.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class ChatController {
    public ChatMessage makeGetRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ChatMessage.class);
    }

    public ResponseEntity<MessageEvent> postMessageEvent(MessageEvent messageEvent, String url) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MessageEvent> requestEntity = new HttpEntity<>(messageEvent);

        // Send the POST request and get the response
        ResponseEntity<MessageEvent> response = restTemplate.postForEntity(url, requestEntity, MessageEvent.class);

        return response;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        String PRODUCER_API = "http://localhost:8002/api/v1/store";
        Integer eventKey = (int) (Math.random() * 100);
        MessageEvent messageEvent = new MessageEvent(eventKey, chatMessage);
        postMessageEvent(messageEvent, PRODUCER_API);

        String CONSUMER_API = "http://localhost:8001/api/v1/message";
        return makeGetRequest(CONSUMER_API);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
