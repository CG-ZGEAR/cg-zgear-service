package com.codegym.cgzgearservice.entitiy.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL)
    private Set<Specification> specifications = new HashSet<>();


}
