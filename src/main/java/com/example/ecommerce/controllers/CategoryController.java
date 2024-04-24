package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.create(category));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name){
        return ResponseEntity.ok(categoryService.delete(name));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        return ResponseEntity.ok(categoryService.deleteAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> read(@PathVariable String name){
        return ResponseEntity.ok(categoryService.read(name));
    }

    @GetMapping
    public ResponseEntity<?> readAll(){
        return ResponseEntity.ok(categoryService.readAll());
    }
}
