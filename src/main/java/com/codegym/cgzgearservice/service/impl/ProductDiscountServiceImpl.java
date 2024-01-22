package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.entitiy.product.ProductDiscount;
import com.codegym.cgzgearservice.repository.ProductDiscountRepository;
import com.codegym.cgzgearservice.service.ProductDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDiscountServiceImpl implements ProductDiscountService {
    private final ProductDiscountRepository productDiscountRepository;

    public List<ProductDiscount> getCurrentDiscountsForProduct(Long productId) {
        return productDiscountRepository.findCurrentDiscountsByProductId(productId);
    }

}
