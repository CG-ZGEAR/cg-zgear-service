package com.codegym.cgzgearservice.entitiy.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BIT default false")
    private Boolean isDeleted;

    @OneToOne(mappedBy = "product",fetch = FetchType.EAGER)
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductImage> productImages;

}
