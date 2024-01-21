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

}