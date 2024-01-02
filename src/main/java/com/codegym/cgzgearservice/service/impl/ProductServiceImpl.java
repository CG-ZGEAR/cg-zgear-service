package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.dto.SpecificationDTO;
import com.codegym.cgzgearservice.exception.ResourceNotFoundException;
import com.codegym.cgzgearservice.model.entitiy.product.*;
import com.codegym.cgzgearservice.repository.*;
import com.codegym.cgzgearservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private SpecificationTemplateRepository specificationTemplateRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        modelMapper.map(productDTO, product);
        createProductImages(product, productDTO.getImageUrls());
        product.setIsDeleted(false);
        product = productRepository.save(product);
        createProductSpecifications(product, productDTO);
        return productDTO;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        modelMapper.map(productDTO, product);
        updateProductImages(product, productDTO.getImageUrls());
        updateProductSpecifications(product, productDTO);

        productRepository.save(product);
        return productDTO;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        if (!productRepository.findById(productId).isPresent()) {
            throw new ResourceNotFoundException("Product with id " + productId + " not found in database");
        } else {
            Product product = productRepository.findById(productId).get();
            product.setIsDeleted(true);
            productRepository.save(product);
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
        }
    }


    @Override
    public ProductDTO getProductById(Long productId) {
        if (!productRepository.findById(productId).isPresent()) {
            throw new ResourceNotFoundException("Product with id " + productId + " not found in database");
        } else {
            Product product = productRepository.findById(productId).get();
            ProductDTO productDTO = convertToProductDTO(product);
            return productDTO;
        }
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
        ProductDetail productDetail = productDetailRepository.findProductDetailByProductId(product.getId());
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
        dto.setSpecKey(spec.getTemplate().getSpecKey());
        dto.setSpecValue(spec.getSpecValue());
        return dto;
    }

    private Specification findOrCreateSpecification(ProductDetail productDetail, SpecificationTemplate template) {
        return productDetail.getSpecifications().stream()
                .filter(s -> s.getTemplate().equals(template))
                .findFirst()
                .orElse(new Specification(null, productDetail, template, null));
    }

    private void updateProductImages(Product product, List<String> newImageUrls) {
        List<String> existingImageUrls = product.getProductImages().stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());

        List<ProductImage> newImages = newImageUrls.stream()
                .distinct()
                .filter(url -> !existingImageUrls.contains(url))
                .map(url -> new ProductImage(null, url, product))
                .collect(Collectors.toList());

        product.getProductImages().addAll(newImages);
    }

    private void updateProductSpecifications(Product product, ProductDTO productDTO) {
        ProductDetail productDetail = productDetailRepository.findProductDetailByProductId(product.getId());
        if (productDetail == null) {
            productDetail = new ProductDetail();
            productDetail.setProduct(product);
        }

        Set<Specification> updatedSpecifications = getUpdatedSpecifications(productDetail, productDTO.getSpecifications());
        productDetail.setSpecifications(updatedSpecifications);
        productDetail.setDescription(productDTO.getDescription());

        productDetailRepository.save(productDetail);
    }

    private Set<Specification> getUpdatedSpecifications(ProductDetail productDetail, List<SpecificationDTO> specificationDTOs) {
        Set<Specification> updatedSpecifications = new HashSet<>();
        for (SpecificationDTO specDTO : specificationDTOs) {
            SpecificationTemplate template = specificationTemplateRepository.findSpecificationTemplateBySpecKey(specDTO.getSpecKey());
            if (template == null) {
                throw new ResourceNotFoundException("Invalid specification key: " + specDTO.getSpecKey());
            }
            Specification spec = findOrCreateSpecification(productDetail, template);
            spec.setSpecValue(specDTO.getSpecValue());
            updatedSpecifications.add(spec);
        }
        return updatedSpecifications;
    }

    private void createProductImages(Product product, List<String> imageUrls) {
        List<ProductImage> images = imageUrls.stream()
                .distinct()
                .map(url -> new ProductImage(null, url, product))
                .collect(Collectors.toList());
        product.setProductImages(images);
    }

    private void createProductSpecifications(Product product, ProductDTO productDTO) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);

        Set<Specification> specifications = getUpdatedSpecifications(productDetail, productDTO.getSpecifications());
        productDetail.setSpecifications(specifications);
        productDetail.setDescription(productDTO.getDescription());

        productDetailRepository.save(productDetail);
    }

}
