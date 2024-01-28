package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.entitiy.product.Category;
import com.codegym.cgzgearservice.service.CategoryService;
import com.codegym.cgzgearservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "com.codegym.cgzgearservice")

public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/{categoryName}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PageableDefault(size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable String categoryName) {
        Page<ProductDTO> products = productService.getProductsByCategory(categoryName, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
