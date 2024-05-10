package com.example.ecommerce.services;

import com.example.ecommerce.DTO.AuthDTO;
import com.example.ecommerce.DTO.PasswordDTO;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.exceptions.NoAuthenticationException;
import com.example.ecommerce.models.User;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.responses.DataResponse;
import com.example.ecommerce.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public BasicResponse register(UserDTO userDTO){
        userService.createUser(userDTO);
        return BasicResponse.ok();
    }

    public DataResponse<AuthDTO> login(UserDTO userDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.email(),
                        userDTO.password()
                )
        );

        User user = userService.getUserByEmail(userDTO.email());
        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().toString())
                .createdAt(user.getCreatedAt())
                .build();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return DataResponse.ok(AuthDTO.builder()
                .userDTO(dto)
                .accessJwt(accessToken)
                .refreshJwt(refreshToken)
                .build());
    }

    public BasicResponse logout(HttpServletRequest request){
        //todo cookie deleting
        return BasicResponse.ok();
    }

    public DataResponse<AuthDTO> refreshToken(HttpServletRequest request){
        System.out.println("refreshed");
        String accessToken = CookieUtils.extractTokenFromCookie(request,"access_token");
        String refreshToken = CookieUtils.extractTokenFromCookie(request,"refresh_token");

        String email = jwtService.getUsername(refreshToken);
        User user = userService.getUserByEmail(email);

        var newAccessJwt = jwtService.generateToken(user);
        var newRefreshJwt = jwtService.generateRefreshToken(user);
        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().toString())
                .createdAt(user.getCreatedAt())
                .build();
        return DataResponse.ok(AuthDTO.builder()
                .userDTO(dto)
                .accessJwt(newAccessJwt)
                .refreshJwt(newRefreshJwt)
                .build());
    }

    public BasicResponse changePassword(PasswordDTO passwordDTO, HttpServletRequest request){
        String token = CookieUtils.extractTokenFromCookie(request,"access_token");

        String email = jwtService.getUsername(token);
        User user = userService.getUserByEmail(email);

        if (!passwordEncoder.matches(passwordDTO.currentPassword(), user.getPassword())){
            throw new NoAuthenticationException("Passwords mismatch");
        }

        user.setPassword(passwordEncoder.encode(passwordDTO.newPassword()));

        userService.updateUser(user.getId(),user);
        //todo session management
        return BasicResponse.ok();
    }

}
