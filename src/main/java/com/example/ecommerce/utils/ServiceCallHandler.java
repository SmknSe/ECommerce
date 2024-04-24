package com.example.ecommerce.utils;

import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.responses.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class ServiceCallHandler {
    private ServiceCallHandler(){}

    public static <T extends BasicResponse> ResponseEntity<T> getResponse(Supplier<T> supplier){
        var response = supplier.get();
        var status = HttpStatus.resolve(response.getStatusCode());

        if (status == null){
            return ResponseEntity.internalServerError().build();
        }
        if (response instanceof DataResponse<?>){
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.status(status).build();
    }
}
