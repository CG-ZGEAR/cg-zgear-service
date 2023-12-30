package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.dto.SpecificationDTO;
import com.codegym.cgzgearservice.model.entitiy.product.Product;
import com.codegym.cgzgearservice.model.entitiy.product.ProductDetail;
import com.codegym.cgzgearservice.model.entitiy.product.ProductImage;
import com.codegym.cgzgearservice.model.entitiy.product.Specification;
import com.codegym.cgzgearservice.repository.ProductDetailRepository;
import com.codegym.cgzgearservice.repository.ProductImageRepository;
import com.codegym.cgzgearservice.repository.ProductRepository;
import com.codegym.cgzgearservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        return null;
    }

    @Override
    public Product getProductById(Long productId) {
        return null;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        dto.setImageUrls(product.getProductImages().stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList()));
        dto.setSpecifications(getSpecificationsForProduct(product));
        dto.setDescription(getDescriptionForProduct(product));
        return dto;
    }

    private String getDescriptionForProduct(Product product) {
        ProductDetail productDetail= productDetailRepository.findProductDetailByProductId(product.getId());
        return productDetail.getDescription();
    }

    private List<SpecificationDTO> getSpecificationsForProduct(Product product) {
        ProductDetail detail = productDetailRepository.findProductDetailByProductId(product.getId());
        if (detail == null || detail.getSpecifications() == null) {
            return Collections.emptyList();
        }
        return detail.getSpecifications().stream()
                .map(this::convertToSpecificationDTO)
                .collect(Collectors.toList());
    }

    private SpecificationDTO convertToSpecificationDTO(Specification spec) {
        SpecificationDTO dto = new SpecificationDTO();
        dto.setId(spec.getId());
        dto.setSpecKey(spec.getTemplate().getSpecKey());
        dto.setSpecValue(spec.getSpecValue());
        return dto;
    }

    @Override
    public void deleteProduct(Long productId) {
    }
}
