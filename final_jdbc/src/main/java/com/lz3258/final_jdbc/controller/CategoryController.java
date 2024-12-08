package com.lz3258.final_jdbc.controller;

import com.lz3258.final_jdbc.dto.CategoryDTO;
import com.lz3258.final_jdbc.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/getCategories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
