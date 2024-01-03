package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findAllByCategory(String category);

}
