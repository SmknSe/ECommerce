package com.example.ecommerce.services;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.persistence.CategoryRepo;
import com.example.ecommerce.utils.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Category save(Category category){
        if (StringUtils.isNullOrBlank(category.getName())) throw new IllegalArgumentException("name");
        return categoryRepo.save(category);
    }

    public boolean create(Category category){
        save(category);
        return true;
    }

    public boolean delete(String name) {
        Category category = categoryRepo.findByName(name).orElseThrow(IllegalArgumentException::new);
        categoryRepo.delete(category);
        return true;

    }

    public boolean deleteAll() {
        categoryRepo.deleteAll();;
        return true;
    }

    public Category read(String name){
        return categoryRepo.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    public List<Category> readAll() {
        return categoryRepo.findAll();
    }
}
