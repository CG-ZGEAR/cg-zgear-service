package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.Cart;
import com.codegym.cgzgearservice.entitiy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUserId(Long userId);

    Cart findByUser(User user);
}
