package com.example.ecommerce.services;

import com.example.ecommerce.DTO.ChatDTO;
import com.example.ecommerce.exceptions.NoAuthenticationException;
import com.example.ecommerce.models.Chat;
import com.example.ecommerce.models.User;
import com.example.ecommerce.persistence.ChatRepo;
import com.example.ecommerce.responses.DataResponse;
import com.example.ecommerce.utils.Mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public DataResponse<?> getChatsByName(Authentication authentication) {
        if (authentication == null)
            throw new NoAuthenticationException();
        User user = (User) authentication.getPrincipal();
        String name = user.getName();
        List<Chat> chats = chatRepo.findAllByFirstUserOrSecondUser(name,name);
        List<ChatDTO> dtos = chats.stream()
                .map(chat -> ChatMapper.toDto(chat,name))
                .collect(Collectors.toList());
        return DataResponse.ok(dtos);
    }
}
