package com.example.ecommerce.utils;

import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.services.CategoryService;
import com.example.ecommerce.services.ProductService;
import com.example.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StartupInitializer implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) {
        try {
            userService.createUser(new UserDTO("admin", "admin@mail.ru",
                    "admin", "ADMIN"));
        } catch (DataIntegrityViolationException ignored) {
        }
        try {
            categoryService.createCategory(List.of(
                    Category.builder().name("beds").build(),
                    Category.builder().name("sofas").build(),
                    Category.builder().name("shelfs").build(),
                    Category.builder().name("lamp").build(),
                    Category.builder().name("chairs").build(),
                    Category.builder().name("armchairs").build(),
                    Category.builder().name("closets").build()
            ));
        } catch (DataIntegrityViolationException ignored) {
        }
    }
}
