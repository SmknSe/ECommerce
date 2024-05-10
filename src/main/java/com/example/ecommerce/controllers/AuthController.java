package com.example.ecommerce.controllers;

import com.example.ecommerce.DTO.AuthDTO;
import com.example.ecommerce.DTO.PasswordDTO;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.services.AuthService;
import com.example.ecommerce.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.ecommerce.utils.CookieUtils.createJwtCookie;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

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
            @RequestBody UserDTO userDTO){
        var response = authService.login(userDTO);
        AuthDTO data = response.getData();
        var accessJwtCookie = createJwtCookie("access_token", data.accessJwt());
        var refreshJwtCookie = createJwtCookie("refresh_token", data.refreshJwt());
        return ResponseEntity
                .status(response.getStatusCode())
                .header(
                        SET_COOKIE, accessJwtCookie.toString(), refreshJwtCookie.toString()
                )
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<? extends BasicResponse> refreshToken(
            HttpServletRequest request
    ){
        var response = authService.refreshToken(request);
        AuthDTO data = response.getData();
        var accessJwtCookie = createJwtCookie("access_token", data.accessJwt());
        var refreshJwtCookie = createJwtCookie("refresh_token", data.refreshJwt());
        return ResponseEntity
                .status(response.getStatusCode())
                .header(
                        SET_COOKIE, accessJwtCookie.toString(), refreshJwtCookie.toString()
                )
                .body(response);
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
        var emptyAccessJwtCookie = createJwtCookie("access_token", "", 0);
        var emptyRefreshJwtCookie = createJwtCookie("refresh_token", "", 0);
        return ResponseEntity
                .noContent()
                .header(
                        SET_COOKIE, emptyAccessJwtCookie.toString(), emptyRefreshJwtCookie.toString()
                )
                .build();
    }
}
