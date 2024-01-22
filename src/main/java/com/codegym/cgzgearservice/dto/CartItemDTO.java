package com.codegym.cgzgearservice.dto;

import com.codegym.cgzgearservice.service.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class CartItemDTO {
    private Long id;

    private Long productId;

    private String productName;

    private Double price;

    private Integer quantity;

}