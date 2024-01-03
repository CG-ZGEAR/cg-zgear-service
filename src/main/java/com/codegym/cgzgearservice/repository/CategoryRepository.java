package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
