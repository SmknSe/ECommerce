package com.example.ecommerce.utils;

import com.example.ecommerce.exceptions.NoAuthenticationException;
import com.example.ecommerce.responses.BasicResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@ResponseBody
public class ExceptionHandlerService {

    @ExceptionHandler({IllegalArgumentException.class,
            IllegalStateException.class,
            EntityNotFoundException.class})
    @ResponseStatus(BAD_REQUEST)
    public String handleArgumentOrState(Exception e){
        System.out.println(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({NoAuthenticationException.class,
            ExpiredJwtException.class,
            AuthenticationException.class})
    @ResponseStatus(UNAUTHORIZED)
    public String handleAuthenticationException(Exception e) {
        System.out.println(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({IOException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public String handleFileException(Exception e){
        System.out.println(e.getMessage());
        return e.getMessage();
    }

}
