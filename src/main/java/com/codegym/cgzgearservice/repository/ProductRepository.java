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
    Optional<Product> findProductByProductNameAndAvailableIsTrue(String productName);

    Page<Product> findProductsByCategoryAndAvailableIsTrue(Category category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.available = true")
    Page<Product> findAllAvailable(Pageable pageable);
    Product findProductByIdAndAvailableIsTrue(Long id);

    @Query("SELECT p FROM Product p\n" +
            "JOIN p.cartItems ci\n" +
            "JOIN ci.cart o\n" +
            "WHERE o.status = 'COMPLETED'\n" +
            "GROUP BY p.id\n" +
            "ORDER BY SUM(ci.quantity) DESC\n")
    Page<Product> getBestSellers();
}
