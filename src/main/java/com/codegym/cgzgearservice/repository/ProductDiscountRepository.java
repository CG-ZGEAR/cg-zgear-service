package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.ProductDiscount;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long> {
    @Query("SELECT pd FROM ProductDiscount pd WHERE pd.product.id = :productId AND pd.active = TRUE " +
            "AND (pd.startDate <= CURRENT_DATE AND (pd.endDate IS NULL OR pd.endDate >= CURRENT_DATE))")
    List<ProductDiscount> findCurrentDiscountsByProductId(@Param("productId") Long productId);

}
