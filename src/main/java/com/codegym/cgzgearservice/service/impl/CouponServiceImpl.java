package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.CouponDTO;
import com.codegym.cgzgearservice.entitiy.product.Coupon;
import com.codegym.cgzgearservice.repository.CouponRepository;
import com.codegym.cgzgearservice.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CouponDTO> findAllActiveCoupon() {
        List<Coupon> coupons = couponRepository.findAllActiveAndNotExpiredCoupons();
        return coupons.stream()
                .map(coupon -> modelMapper.map(coupon, CouponDTO.class))
                .collect(Collectors.toList());
    }
}
