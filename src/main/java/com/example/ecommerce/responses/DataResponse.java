package com.example.ecommerce.responses;

public class DataResponse<T> extends BasicResponse{
    T data;

    private DataResponse(int statusCode, T data){
        super(statusCode);
        this.data = data;
    }

    public static <T> DataResponse<T> ok(T data){
        return new DataResponse<>(200, data);
    }

    public static <T> DataResponse<T> created(T data){
        return new DataResponse<>(201, data);
    }
}
