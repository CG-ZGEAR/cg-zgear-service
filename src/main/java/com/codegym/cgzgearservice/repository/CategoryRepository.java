package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.Category;
import com.codegym.cgzgearservice.entitiy.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
    List<Category> findAll();
}
