package com.codegym.cgzgearservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDiscountDTO {
    private Long id;
    private String discountType;
    private Double discountAmount;
    private Date startDate;
    private Date endDate;
}

