package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRepository extends JpaRepository<Specification, Long> {
}
