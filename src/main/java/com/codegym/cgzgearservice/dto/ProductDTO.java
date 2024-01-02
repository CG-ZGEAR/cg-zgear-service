package com.codegym.cgzgearservice.dto;

import com.codegym.cgzgearservice.model.entitiy.product.ProductDetail;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {
        private Long id;
        private String productName;
        private Double price;
        private Long categoryId;
        private String description;
        private List<String> imageUrls;
        private List<SpecificationDTO> specifications;
}
