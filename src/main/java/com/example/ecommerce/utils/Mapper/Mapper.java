package com.example.ecommerce.utils.Mapper;

public interface Mapper<T1, T2> {

    T2 toDto(T1 object);
    T1 fromDto(T2 object);
}
