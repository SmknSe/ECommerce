package com.example.ecommerce.utils;

import com.example.ecommerce.DTO.ChatDTO;
import com.example.ecommerce.models.Chat;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private Mapper(){}
    public static ChatDTO toDto(Chat chat, String currentUser){
        String username = currentUser.equals(chat.getFirstUser()) ? chat.getSecondUser() : chat.getFirstUser();
        return new ChatDTO(username, chat.countChatNewMessages(currentUser));
    }
}
