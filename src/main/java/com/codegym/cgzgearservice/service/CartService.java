package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.dto.CartItemDTO;
import com.codegym.cgzgearservice.entitiy.user.User;

public interface CartService {
    CartDTO addToCart(User user, Long productId, int quantity);
}
