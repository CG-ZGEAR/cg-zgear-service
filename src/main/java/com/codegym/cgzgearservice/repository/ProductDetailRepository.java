package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.product.Product;
import com.codegym.cgzgearservice.model.entitiy.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {
    ProductDetail findProductDetailByProductId(Long productId);
}
