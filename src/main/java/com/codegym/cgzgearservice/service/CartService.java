package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.dto.CartItemDTO;
import com.codegym.cgzgearservice.entitiy.product.Cart;
import com.codegym.cgzgearservice.entitiy.user.User;

public interface CartService {
    CartDTO addToCart(User user,String sessionId, Long productId, int quantity);
    CartDTO getCart(User user, String id);

    void mergeCarts(String sessionId, User user);

    CartDTO updateCart(User user, String sessionId, CartDTO cartDTO);
}
