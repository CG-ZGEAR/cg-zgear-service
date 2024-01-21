package com.codegym.cgzgearservice.entitiy.product;

import com.codegym.cgzgearservice.constants.DiscountType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "product_discounts")
@Getter
@Setter
@NoArgsConstructor
public class ProductDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private Double discountAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Column(nullable = false)
    private Boolean active = true;

}

