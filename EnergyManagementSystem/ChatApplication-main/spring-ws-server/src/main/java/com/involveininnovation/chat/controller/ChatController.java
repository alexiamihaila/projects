package com.involveininnovation.chat.controller;

import com.involveininnovation.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static Integer id = 0;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){

        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        id++; // Increment first to ensure each message has a unique ID
        message.setMessageId(id.toString());
        System.out.println("Assigned Message ID: " + message.getMessageId());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        return message;
    }

    @MessageMapping("/typing")
    public void handleTyping(@Payload Message message) {
        String recipientName = message.getReceiverName();
        String destination;
        destination = "/user/" + recipientName + "/queue/typing";

        simpMessagingTemplate.convertAndSend(destination, message);
    }

    @MessageMapping("/message-read")
    public void handleMessageRead(@Payload Message message) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("Read Message ID: " + message.getMessageId());
        String senderName = message.getSenderName();
        message.setRead(true);
        message.setDate(formattedDateTime);
        simpMessagingTemplate.convertAndSend("/user/" + senderName + "/queue/read-receipt", message);
    }
}
