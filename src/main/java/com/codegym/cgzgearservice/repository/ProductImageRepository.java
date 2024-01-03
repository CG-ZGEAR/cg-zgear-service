package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
