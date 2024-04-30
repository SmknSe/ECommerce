package com.example.ecommerce.services;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.persistence.CategoryRepo;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.utils.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public void saveCategory(Category category){
        if (StringUtils.isNullOrBlank(category.getName()))
            throw new IllegalArgumentException("Empty category name");
        categoryRepo.save(category);
    }

    @Transactional
    public BasicResponse createCategory(List<Category> categories){
        categories.forEach(this::saveCategory);
        return BasicResponse.ok();
    }

    public boolean deleteCategoryByName(String name) {
        Category category = categoryRepo
                .findByName(name)
                .orElseThrow(IllegalArgumentException::new);
        categoryRepo.delete(category);
        return true;

    }

    public boolean deleteAll() {
        categoryRepo.deleteAll();;
        return true;
    }

    public Category findCategoryByName(String name){
        return categoryRepo
                .findByName(name)
                .orElseThrow(
                        ()->new EntityNotFoundException(
                                "Category "+name+" doesnt exist"
                        )
                );
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }
}
