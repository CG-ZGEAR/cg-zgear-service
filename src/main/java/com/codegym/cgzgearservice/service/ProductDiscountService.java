package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.entitiy.product.ProductDiscount;

import java.util.List;

public interface ProductDiscountService {
    List<ProductDiscount> getCurrentDiscountsForProduct(Long productId);
}
