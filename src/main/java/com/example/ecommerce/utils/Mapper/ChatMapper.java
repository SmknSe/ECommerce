package com.example.ecommerce.utils.Mapper;

import com.example.ecommerce.DTO.ChatDTO;
import com.example.ecommerce.models.Chat;

public class ChatMapper{
    private ChatMapper(){}
    public static ChatDTO toDto(Chat chat, String currentUser){
        String username = currentUser.equals(chat.getFirstUser()) ? chat.getSecondUser() : chat.getFirstUser();
        return new ChatDTO(username, chat.countChatNewMessages(currentUser));
    }

}
