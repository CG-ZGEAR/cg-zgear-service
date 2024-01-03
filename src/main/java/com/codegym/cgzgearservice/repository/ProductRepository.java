package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findAllByCategory(String category);
}
