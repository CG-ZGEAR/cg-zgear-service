package com.codegym.cgzgearservice.dto;

import com.codegym.cgzgearservice.entitiy.product.ProductImage;
import com.codegym.cgzgearservice.service.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productImageUrl;
    private String productName;
    private Double subTotal;
    private Integer quantity;

}