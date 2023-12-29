package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.model.entitiy.product.Product;
import com.codegym.cgzgearservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        return null;
    }

    @Override
    public Product getProductById(Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
    }
}
