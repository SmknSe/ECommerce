package com.example.ecommerce.controllers;

import com.example.ecommerce.DTO.PasswordDTO;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.services.AuthService;
import com.example.ecommerce.utils.ServiceCallHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<? extends BasicResponse> register(@RequestBody UserDTO userDTO){
        return ServiceCallHandler.getResponse(()->authService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<? extends BasicResponse> login(
            @RequestBody UserDTO userDTO,
            HttpServletResponse response){
        return ServiceCallHandler.getResponse(()->authService.login(userDTO,response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<? extends BasicResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return ServiceCallHandler.getResponse(()->authService.refreshToken(request,response));
    }

    @PostMapping("/change-password")
    public ResponseEntity<? extends BasicResponse> changePassword(
            @RequestBody PasswordDTO passwordDTO,
            HttpServletRequest request
            ){
        return ServiceCallHandler.getResponse(()->authService.changePassword(passwordDTO,request));
    }

    @PostMapping("/logout")
    public ResponseEntity<? extends BasicResponse> logout(HttpServletRequest request){
        return ServiceCallHandler.getResponse(()->authService.logout(request));
    }
}
