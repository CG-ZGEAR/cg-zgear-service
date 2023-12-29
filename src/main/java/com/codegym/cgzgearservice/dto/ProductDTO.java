package com.codegym.cgzgearservice.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {

    private Long id;
    private String productName;
    private Double price;
    private Long categoryId;
    private List<String> imageUrls;
    private List<SpecificationDTO> specifications;
    @Data
    public static class SpecificationDTO {
        private Long templateId;
        private String specValue;
    }
}
