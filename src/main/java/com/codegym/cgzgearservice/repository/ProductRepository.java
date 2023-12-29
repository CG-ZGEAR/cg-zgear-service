package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p, spec FROM Product p LEFT JOIN ProductSpecification spec ON p.id = spec.productDetail.id WHERE p.id = :productId")
    List<Object[]> findProductWithSpecifications(@Param("productId") Long productId);


}
