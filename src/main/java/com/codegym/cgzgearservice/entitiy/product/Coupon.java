package com.codegym.cgzgearservice.entitiy.product;

import com.codegym.cgzgearservice.constants.DiscountType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private Double discountAmount;

    @Column(nullable = false)
    private Integer maxUses;

    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Column(nullable = false)
    private Boolean active = true;
}
