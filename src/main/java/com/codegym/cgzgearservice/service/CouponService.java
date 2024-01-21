package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.CouponDTO;

import java.util.List;

public interface CouponService {
    List<CouponDTO> findAllActiveCoupon();
}
