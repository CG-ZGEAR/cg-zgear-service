package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    ProductDetail findProductDetailByProductId(Long productId);
}
