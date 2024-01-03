package com.codegym.cgzgearservice.dto;

import lombok.Data;

import java.util.List;

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
