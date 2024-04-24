package com.example.ecommerce.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BasicResponse {
    private final int statusCode;
    protected BasicResponse(int statusCode){
        this.statusCode = statusCode;
    }

    public static BasicResponse ok(){
        return new BasicResponse(200);
    }

    public static BasicResponse noContent(){
        return new BasicResponse(204);
    }
}
