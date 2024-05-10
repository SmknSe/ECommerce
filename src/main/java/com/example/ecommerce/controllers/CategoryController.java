package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createAll(@RequestBody List<Category> categories){
        return ResponseEntity.ok(categoryService.createCategory(categories));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name){
        return ResponseEntity.ok(categoryService.deleteCategoryByName(name));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        return ResponseEntity.ok(categoryService.deleteAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> read(@PathVariable String name){
        return ResponseEntity.ok(categoryService.findCategoryByName(name));
    }

    @GetMapping
    public ResponseEntity<?> readAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/filter/name")
    public ResponseEntity<?> readNames(){
        return ResponseEntity.ok(categoryService.findAllNames());
    }
}
