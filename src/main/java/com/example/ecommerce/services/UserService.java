package com.example.ecommerce.services;

import com.example.ecommerce.DTO.SellerDTO;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.models.User;
import com.example.ecommerce.persistence.UserRepo;
import com.example.ecommerce.responses.DataResponse;
import com.example.ecommerce.utils.CookieUtils;
import com.example.ecommerce.utils.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    private final JwtService jwtService;

    public DataResponse<?> getUserById(UUID id){
        return DataResponse.ok(getUser(id));
    }

    public User getUserByEmail(String email){
        return userRepo
                .findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(
                                "User doesn't exist"
                        )
                );
    }

    public DataResponse<User> createUser(UserDTO userDTO){
        System.out.println(userDTO);
        if (Stream.of(
                userDTO.name(),
                userDTO.email(),
                userDTO.password()
        ).anyMatch(StringUtils::isNullOrBlank)){
            throw new IllegalArgumentException("Some properties are null or blank");
        }

        User newUser = User.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(passwordEncoder.encode(userDTO.password()))
                .role(
                        userDTO.role() != null ?
                        Role.valueOf(userDTO.role()) :
                        Role.USER)
                .createdAt(LocalDateTime.now())
                .orders(new ArrayList<>())
                .build();

        User createdUser = userRepo.save(newUser);

        orderService.createCartByUser(createdUser);

        return DataResponse.created(createdUser);
    }

    public User updateUser(UUID id, User user) {
        if (!userRepo.existsById(id)){
            throw new EntityNotFoundException("User with id "+id+" not found");
        }
        user.setId(id);
        return userRepo.save(user);
    }

    public DataResponse<?> getAllUsers() {
        return DataResponse.ok(userRepo.findAll());
    }

    public DataResponse<?> getUserSellerInfoById(UUID id) {
        User user = getUser(id);
        SellerDTO dto = SellerDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .products(user.getProducts())
                .build();
        return DataResponse.ok(dto);
    }

    public User getUser(UUID uuid){
        return userRepo
                .findById(uuid)
                .orElseThrow(()->
                        new EntityNotFoundException(
                                "User with id "+uuid+" doesnt exist"
                        )
                );
    }

    public User getUserFromRequest(HttpServletRequest request){
        String accessToken = CookieUtils.extractTokenFromCookie(request,"access_token");

        String email = jwtService.getUsername(accessToken);
        return getUserByEmail(email);
    }

}
