package com.example.ecommerce.services;

import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.models.order.Order;
import com.example.ecommerce.models.User;
import com.example.ecommerce.persistence.OrderRepo;
import com.example.ecommerce.persistence.UserRepo;
import com.example.ecommerce.responses.DataResponse;
import com.example.ecommerce.utils.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepo orderRepo;

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User doesn't exist"));
    }

    public DataResponse<User> create(UserDTO userDTO){
        System.out.println(userDTO);
        Stream<String> values = Stream.of(
                userDTO.firstname(),
                userDTO.lastname(),
                userDTO.email(),
                userDTO.password(),
                userDTO.role()
        );

        if (values.anyMatch(StringUtils::isNullOrBlank)){
            throw new IllegalArgumentException("Some properties are null or blank");
        }

        User newUser = User.builder()
                .firstname(userDTO.firstname())
                .lastname(userDTO.lastname())
                .email(userDTO.email())
                .password(passwordEncoder.encode(userDTO.password()))
                .role(Role.valueOf(userDTO.role()))
                .createdAt(LocalDateTime.now())
                .build();

        User createdUser = userRepo.save(newUser);

        orderRepo.save(Order.builder()
                .status(OrderStatus.CART)
                .total(BigDecimal.ZERO)
                .items(Collections.emptyList())
                .user(createdUser)
                .build());

        return DataResponse.created(createdUser);
    }

    public User update(UUID id, User user) {
        if (!userRepo.existsById(id)){
            throw new EntityNotFoundException("User with id "+id+" not found");
        }
        user.setId(id);
        return userRepo.save(user);
    }
}
