package com.example.demo.controller;

import com.example.demo.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    private final MessagingService messagingService;

    @Autowired
    public MessageController(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessageToTopic(@RequestBody String messageData) {
        String messageId = messagingService.sendToTopic("your-topic-name", messageData);
        System.out.println("messageId: " + messageId);
        return ResponseEntity.ok("Message sent with ID: " + messageId);
    }
}
