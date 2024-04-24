package com.example.ecommerce.exceptions;

public class NoAuthenticationException extends RuntimeException{
    public NoAuthenticationException(String message){
        super(message);
    }

    public NoAuthenticationException(){
        super("No authentication");
    }
}
