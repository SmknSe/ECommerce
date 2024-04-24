package com.example.ecommerce.services;

import com.example.ecommerce.enums.ChatMessageStatus;
import com.example.ecommerce.models.Chat;
import com.example.ecommerce.models.ChatMessage;
import com.example.ecommerce.persistence.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepo chatMessageRepo;
    private final ChatService chatService;

    public void save(ChatMessage chatMessage){
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setStatus(ChatMessageStatus.SENT);
        Chat chat = chatService.getChat(chatMessage.getSender(), chatMessage.getReceiver());
        chatMessage.setChat(chat);
        chatMessageRepo.save(chatMessage);
    }

    public List<ChatMessage> readAllByChat(String currentUser, String secondUser){
        Chat chat = chatService.getChat(currentUser, secondUser);
        List<ChatMessage> chatMessages = chat.getChatMessages();
        for (ChatMessage chatMessage : chatMessages){
            if (chatMessage.getStatus().equals(ChatMessageStatus.SENT)
            && chatMessage.getReceiver().equals(currentUser)){
                chatMessage.setStatus(ChatMessageStatus.READ);
            }
            chatMessageRepo.save(chatMessage);
        }
        return chatMessages;
    }

    public long countAllNewMessages(String username){
        return chatMessageRepo.countByReceiverAndStatus(username,ChatMessageStatus.SENT);
    }


}
