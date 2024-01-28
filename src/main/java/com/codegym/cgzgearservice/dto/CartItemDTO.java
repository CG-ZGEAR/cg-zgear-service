package com.codegym.cgzgearservice.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long productId;
    private Double productPrice;
    private String productImageUrl;
    private String productName;
    private Double subTotal;
    private Integer quantity;

}