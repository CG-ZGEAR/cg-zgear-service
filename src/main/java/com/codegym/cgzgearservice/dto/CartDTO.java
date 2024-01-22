package com.codegym.cgzgearservice.dto;

import com.codegym.cgzgearservice.entitiy.product.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {

    private Long id;
    private List<CartItemDTO> cartItems;
    private double totalPrice;

    public double getTotalPrice() {
        for (CartItemDTO cartItemDTO : cartItems) {
            totalPrice += cartItemDTO.getPrice();
        }
        return totalPrice;
    }
}