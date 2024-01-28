package com.codegym.cgzgearservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CouponDTO {
    private Long id;
    private String code;
    private String discountType;
    private Double discountAmount;
    private Integer maxUses;
    private Date expireDate;
    private Boolean active;
}
