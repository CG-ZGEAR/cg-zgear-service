package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.entitiy.product.Product;
import com.codegym.cgzgearservice.entitiy.product.ProductImage;
import com.codegym.cgzgearservice.repository.ProductDetailRepository;
import com.codegym.cgzgearservice.repository.ProductImageRepository;
import com.codegym.cgzgearservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDetailRepository productDetailRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    ProductImageRepository productImageRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testCreateProduct() {
        ProductDTO productDTO = createAnProduct();
        when(modelMapper.map(any(ProductDTO.class), eq(Product.class))).thenReturn(new Product());
        productService.createProduct(productDTO);
        verify(productRepository, times(1)).save(any(Product.class));

    }
    private ProductDTO createAnProduct(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("product");
        productDTO.setPrice(1000D);
        productDTO.setDescription("description");
        productDTO.setCategoryName("category");
        productDTO.setImageUrls(Collections.emptyList());
        productDTO.setSpecifications(Collections.emptyList());
        productDTO.setReviews(Collections.emptyList());
        productDTO.setDiscounts(Collections.emptyList());
        return productDTO;
    }
}
