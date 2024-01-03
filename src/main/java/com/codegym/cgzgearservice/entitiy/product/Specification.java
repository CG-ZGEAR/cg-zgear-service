package com.codegym.cgzgearservice.entitiy.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "specifications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private SpecificationTemplate template;

    @Column(name = "spec_value")
    private String specValue;

}

