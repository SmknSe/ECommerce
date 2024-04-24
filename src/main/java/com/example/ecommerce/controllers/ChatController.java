package com.example.ecommerce.controllers;

import com.example.ecommerce.DTO.ChatDTO;
import com.example.ecommerce.models.Chat;
import com.example.ecommerce.services.ChatService;
import com.example.ecommerce.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getChatsByName(@PathVariable String name, Authentication authentication){
        System.out.println(authentication.getName());
        List<Chat> chats = chatService.getChatsByName(name);
        List<ChatDTO> dtos = chats.stream()
                .map(chat -> Mapper.toDto(chat,name))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
