package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.dto.ReviewDTO;
import com.codegym.cgzgearservice.entitiy.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    Page<ProductDTO> getProductsByCategory(String categoryName, Pageable pageable);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO getProductById(Long productId);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    ProductDTO deleteProduct(Long productId);

    Page<ProductDTO> searchProduct(String searchTerm, Pageable pageable);

    ProductDTO getProductByName(String productName);

    ReviewDTO addReview(Long productId, ReviewDTO reviewDTO, Principal principal);
    Page<ProductDTO> getBestSellers(Pageable pageable);
}
