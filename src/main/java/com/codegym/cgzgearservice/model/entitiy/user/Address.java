package com.codegym.cgzgearservice.model.entitiy.user;

import com.codegym.cgzgearservice.model.entitiy.product.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    @NotBlank
    private String street;

    @Column(name = "city")
    @NotBlank
    private String city;

    @Column(name = "district")
    @NotBlank
    private String district;

    @Column(name = "ward")
    @NotBlank
    private String ward;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BIT default false")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    User user;

    @ManyToOne
    @JoinColumn(name = "oder_id", nullable = true)
    Order oder;


}