package com.example.ecommerce.services;

import com.example.ecommerce.models.Chat;
import com.example.ecommerce.persistence.ChatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepo chatRepo;

    public Chat getChat(String sender, String receiver){
        String firstUser, secondUser;
        if (sender.compareTo(receiver) > 0){
            firstUser = sender;
            secondUser = receiver;
        }
        else {
            firstUser = receiver;
            secondUser = sender;
        }
        Optional<Chat> chat;
        chat = chatRepo.findByFirstUserAndSecondUser(firstUser,secondUser);
        if (chat.isPresent()) return chat.get();
        Chat newChat = Chat.builder().firstUser(firstUser).secondUser(secondUser).build();
        chatRepo.save(newChat);
        return newChat;
    }

    public List<Chat> getChatsByName(String name) {
        return chatRepo.findAllByFirstUserOrSecondUser(name,name);
    }
}
