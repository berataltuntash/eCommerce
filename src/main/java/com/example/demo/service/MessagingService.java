package com.example.demo.service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public MessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendToTopic(String topicName, String messageData) {
        Message msg = Message.builder()
                .setTopic(topicName)
                .putData("body", messageData)
                .build();
        try {
            return firebaseMessaging.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

