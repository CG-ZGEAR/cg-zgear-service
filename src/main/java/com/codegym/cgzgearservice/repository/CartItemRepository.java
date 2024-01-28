package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
