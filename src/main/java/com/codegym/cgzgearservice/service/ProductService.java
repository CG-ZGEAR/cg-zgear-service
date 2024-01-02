package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.model.entitiy.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO getProductById(Long productId);
    List<ProductDTO> getAllProducts();
    void deleteProduct(Long productId);
}
