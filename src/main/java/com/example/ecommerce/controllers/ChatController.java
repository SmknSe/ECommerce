package com.example.ecommerce.controllers;

import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping()
    public ResponseEntity<? extends BasicResponse> getChatsByName(Authentication authentication){
        var response = chatService.getChatsByName(authentication);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
