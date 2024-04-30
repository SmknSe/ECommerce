package com.example.ecommerce.utils.Mapper;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.models.Product;

public class ProductMapper implements Mapper<Product, ProductDTO> {
    @Override
    public ProductDTO toDto(Product object) {
        return new ProductDTO(
                object.getTitle(),
                object.getPrice(),
                object.getCategory().getName(),
                object.getProductImg()
        );
    }

    @Override
    public Product fromDto(ProductDTO object) {
        return new Product(
                object.title(),
                object.price(),
                object.productImg()
        );
    }
}
