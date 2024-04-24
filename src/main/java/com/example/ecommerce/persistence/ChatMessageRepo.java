package com.example.ecommerce.persistence;

import com.example.ecommerce.enums.ChatMessageStatus;
import com.example.ecommerce.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, UUID> {
    public long countByReceiverAndStatus(String receiver, ChatMessageStatus status);
}
