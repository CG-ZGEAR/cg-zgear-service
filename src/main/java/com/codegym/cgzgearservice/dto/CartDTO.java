package com.codegym.cgzgearservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {

    private Long id;
    private List<CartItemDTO> cartItems;
    private double totalPrice;

    public double getTotalPrice() {
        this.totalPrice=0;
        for (CartItemDTO cartItemDTO : cartItems) {
            totalPrice += cartItemDTO.getSubTotal();
        }
        return totalPrice;
    }

    public CartDTO() {
       this.cartItems= new ArrayList<>();
    }
}