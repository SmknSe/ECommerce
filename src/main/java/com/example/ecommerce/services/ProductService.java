package com.example.ecommerce.services;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import com.example.ecommerce.persistence.ProductRepo;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.responses.DataResponse;
import com.example.ecommerce.utils.CookieUtils;
import com.example.ecommerce.utils.Mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryService categoryService;
    private final JwtService jwtService;
    private final UserService userService;
    public DataResponse<?> createProduct(ProductDTO dto, HttpServletRequest request){
        String accessToken = CookieUtils.extractTokenFromCookie(request,"access_token");

        String email = jwtService.getUsername(accessToken);
        User user = userService.getUserByEmail(email);

        ProductMapper mapper = new ProductMapper();
        Product product = mapper.fromDto(dto);
        Category category = categoryService.findCategoryByName(dto.category());
        product.setCategory(category);
        product.setOwner(user);
        productRepo.save(product);
        return DataResponse.created(dto);
    }

    public DataResponse<?> updateProduct(UUID id, ProductDTO dto){
        Product product = productRepo
                .findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException(
                                "Product with uuid "+id+" doesn't exist"
                        )
                );
        product.setTitle(dto.title());
        product.setProductImg(dto.productImg());
        product.setPrice(dto.price());
        Category category = categoryService.findCategoryByName(dto.category());
        product.setCategory(category);
        productRepo.save(product);
        return DataResponse.ok(product);
    }


    public DataResponse<?> findProductById(UUID id){
        Product product = productRepo
                .findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException(
                                "Product with uuid "+id+" doesn't exist"
                        )
                );
        return DataResponse.ok(product);
    }

    public DataResponse<?> findAll() {
        return DataResponse.ok(productRepo.findAll());
    }

    public BasicResponse deleteProductById(UUID id){
        productRepo.deleteById(id);
        return BasicResponse.ok();
    }

}
