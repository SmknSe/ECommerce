package com.example.ecommerce.models;

import com.example.ecommerce.enums.ChatMessageStatus;
import com.example.ecommerce.models.Chat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue
    private UUID id;

    private String sender;

    private String receiver;

    private String content;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private ChatMessageStatus status;

    @ManyToOne
    @JsonBackReference
    private Chat chat;
}
