package com.example.ecommerce.services;

import com.example.ecommerce.DTO.PasswordDTO;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.exceptions.NoAuthenticationException;
import com.example.ecommerce.models.User;
import com.example.ecommerce.responses.BasicResponse;
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

    public BasicResponse login(UserDTO userDTO, HttpServletResponse response){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.email(),
                        userDTO.password()
                )
        );

        User user = userService.getUserByEmail(userDTO.email());

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        response.addCookie(CookieUtils.createJwtCookie(accessToken,"access_token"));
        response.addCookie(CookieUtils.createJwtCookie(refreshToken,"refresh_token"));
        return BasicResponse.ok();
    }

    public BasicResponse logout(HttpServletRequest request){
        //todo cookie deleting
        return BasicResponse.ok();
    }

    public BasicResponse refreshToken(HttpServletRequest request, HttpServletResponse response){
        String accessToken = CookieUtils.extractTokenFromCookie(request,"access_token");
        String refreshToken = CookieUtils.extractTokenFromCookie(request,"refresh_token");

        String email = jwtService.getUsername(refreshToken);
        User user = userService.getUserByEmail(email);

        String newAccessToken = jwtService.generateToken(user);
        response.addCookie(CookieUtils.createJwtCookie(newAccessToken, "access_token"));
        return BasicResponse.ok();
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
