package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
