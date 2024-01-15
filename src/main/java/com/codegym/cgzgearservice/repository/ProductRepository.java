package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.Category;
import com.codegym.cgzgearservice.entitiy.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findProductByProductName(String productName);
    Page<Product> findProductsByCategory(Category category, Pageable pageable);
}
