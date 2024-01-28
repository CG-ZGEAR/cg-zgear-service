package com.codegym.cgzgearservice.entitiy.user;

import com.codegym.cgzgearservice.entitiy.product.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Order> orders;


}