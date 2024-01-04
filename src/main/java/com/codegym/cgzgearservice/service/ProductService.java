package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.entitiy.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO getProductById(Long productId);
    Page<ProductDTO> getAllProducts(Pageable pageable);
    ProductDTO deleteProduct(Long productId);
    List<ProductDTO> searchByProductName(String searchTerm);
}
