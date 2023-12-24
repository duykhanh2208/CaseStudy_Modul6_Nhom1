package com.houserenting.controller.test;

import com.houserenting.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

//    @MessageMapping("/chat")
//    @SendTo("/topic")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//        chatMessage.setTimestamp(new Date()); // Set the current timestamp
//        return chatMessage;
//    }

    @MessageMapping("/chat")
    @SendTo("/topic")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(new Date()); // Set the current timestamp
        return chatMessage;
    }

    @MessageMapping("/notify")
    public void notify(Notification notification) {
        String destinationReceive = "/notify/" + notification.getReceiver().getId();
        simpMessagingTemplate.convertAndSend(destinationReceive, notification);
    }
}