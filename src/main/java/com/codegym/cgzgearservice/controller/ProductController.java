package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.dto.ReviewDTO;
import com.codegym.cgzgearservice.entitiy.product.Product;
import com.codegym.cgzgearservice.exception.ResourceNotFoundException;
import com.codegym.cgzgearservice.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @PageableDefault(size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam String searchTerm,
            @PageableDefault(size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductDTO> products = productService.searchProduct(searchTerm, pageable);
        return ResponseEntity.ok(products);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createOneProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.getProductById(productId);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String productName) {
        ProductDTO productDTO = productService.getProductByName(productName);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long productId, @RequestBody ReviewDTO reviewDTO, Principal principal) {
        ReviewDTO addedReview = productService.addReview(productId, reviewDTO, principal);
        return ResponseEntity.ok(addedReview);
    }

    @GetMapping("/getBestSellers")
    public ResponseEntity<Page<ProductDTO>> getBestSellers(
            @PageableDefault(size = 4) Pageable pageable) {
        Page<ProductDTO> products = productService.getBestSellers(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getNewArrivals")
    public ResponseEntity<Page<ProductDTO>> getNewArrivals(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }
}
