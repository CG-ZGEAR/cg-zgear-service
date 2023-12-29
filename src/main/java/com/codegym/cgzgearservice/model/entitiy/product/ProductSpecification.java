package com.codegym.cgzgearservice.model.entitiy.product;

import com.codegym.cgzgearservice.model.entitiy.product.specifications.SpecificationTemplate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_specifications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private SpecificationTemplate template;

    private String specValue;

}

