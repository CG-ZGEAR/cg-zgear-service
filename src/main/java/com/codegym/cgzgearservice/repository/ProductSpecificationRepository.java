package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.product.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<Specification,Long> {
}
