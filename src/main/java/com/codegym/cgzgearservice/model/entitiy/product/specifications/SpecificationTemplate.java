package com.codegym.cgzgearservice.model.entitiy.product.specifications;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "specification_templates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String specKey;

}
