package com.example.ecommerce.services;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.enums.ProductStatus;
import com.example.ecommerce.exceptions.NoAuthenticationException;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import com.example.ecommerce.persistence.ProductRepo;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.responses.DataResponse;
import com.example.ecommerce.utils.Mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryService categoryService;
    public DataResponse<?> createProduct(ProductDTO dto, Authentication authentication){
        if (authentication == null)
            throw new NoAuthenticationException();
        User user = (User) authentication.getPrincipal();

        ProductMapper mapper = new ProductMapper();
        Product product = mapper.fromDto(dto);
        Category category = categoryService.findCategoryByName(dto.category());
        product.setCategory(category);
        product.setOwner(user);
        product.setStatus(ProductStatus.ON_SALE);
        productRepo.save(product);
        return DataResponse.created(dto);
    }

    public DataResponse<?> updateProduct(UUID id, ProductDTO dto, Authentication authentication){
        if (authentication == null)
            throw new NoAuthenticationException();
        Product product = getProduct(id);
        if (!product.getOwner().getEmail().equals(authentication.getName()))
            throw new IllegalArgumentException("You are not owner of the product");
        product.setTitle(dto.title());
        product.setProductImg(dto.productImg());
        product.setPrice(dto.price());
        Category category = categoryService.findCategoryByName(dto.category());
        product.setCategory(category);
        productRepo.save(product);
        return DataResponse.ok(product);
    }


    public DataResponse<?> findProductById(UUID id){
        Product product = getProduct(id);
        return DataResponse.ok(product);
    }

    public DataResponse<?> findAll() {
        return DataResponse.ok(productRepo.findAll());
    }

    public BasicResponse deleteProductById(UUID id){
        productRepo.deleteById(id);
        return BasicResponse.ok();
    }

    public Product getProduct(UUID uuid){
        return productRepo
                .findById(uuid)
                .orElseThrow(()->
                        new EntityNotFoundException(
                                "Product with uuid "+uuid+" doesn't exist"
                        )
                );
    }

    public void updateProductStatus(UUID uuid){
        Product product = getProduct(uuid);
        product.setStatus(ProductStatus.PURCHASED);
        productRepo.save(product);
    }

    public void save(Product product) {
        productRepo.save(product);
    }
}
