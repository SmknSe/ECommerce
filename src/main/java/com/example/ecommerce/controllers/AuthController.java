package com.example.ecommerce.controllers;

import com.example.ecommerce.DTO.PasswordDTO;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.services.AuthService;
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
        BasicResponse response = authService.register(userDTO);
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @PostMapping("/login")
    public ResponseEntity<? extends BasicResponse> login(
            @RequestBody UserDTO userDTO,
            HttpServletResponse response){
        BasicResponse basicResponse = authService.login(userDTO,response);
        return ResponseEntity.status(basicResponse.getStatusCode()).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<? extends BasicResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        BasicResponse basicResponse = authService.refreshToken(request,response);
        return ResponseEntity.status(basicResponse.getStatusCode()).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<? extends BasicResponse> changePassword(
            @RequestBody PasswordDTO passwordDTO,
            HttpServletRequest request
            ){
        BasicResponse basicResponse = authService.changePassword(passwordDTO,request);
        return ResponseEntity.status(basicResponse.getStatusCode()).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<? extends BasicResponse> logout(HttpServletRequest request){
        BasicResponse basicResponse = authService.logout(request);
        return ResponseEntity.status(basicResponse.getStatusCode()).build();
    }
}
