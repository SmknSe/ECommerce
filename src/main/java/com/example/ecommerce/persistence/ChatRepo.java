package com.example.ecommerce.persistence;

import com.example.ecommerce.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepo extends JpaRepository<Chat, UUID> {
    Optional<Chat> findByFirstUserAndSecondUser(String firstUser, String secondUser);
    List<Chat> findAllByFirstUserOrSecondUser(String firstUser, String secondUser);
}
