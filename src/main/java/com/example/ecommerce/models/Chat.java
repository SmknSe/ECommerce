package com.example.ecommerce.models;

import com.example.ecommerce.enums.ChatMessageStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstUser;
    private String secondUser;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ChatMessage> chatMessages;

    public long countChatNewMessages(String username){
        long res = getChatMessages().stream().
                filter(chatMessage ->
                        chatMessage.getReceiver().equals(username)
                                && chatMessage.getStatus().equals(ChatMessageStatus.SENT))
                .count();
        return res;
    }
}
