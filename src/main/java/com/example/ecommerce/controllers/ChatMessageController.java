package com.example.ecommerce.controllers;

import com.example.ecommerce.models.ChatMessage;
import com.example.ecommerce.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/private-message")
    public ChatMessage sendPrivateMessage(@Payload ChatMessage chatMessage){
        chatMessageService.save(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiver(),"/private-message",chatMessage);
        return chatMessage;
    }

    @GetMapping("/api/messages/{sender}/{receiver}")
    public ResponseEntity<?> getAllChatMessages(@PathVariable String sender, @PathVariable String receiver){
        return ResponseEntity.ok(chatMessageService.readAllByChat(sender,receiver));
    }

}
