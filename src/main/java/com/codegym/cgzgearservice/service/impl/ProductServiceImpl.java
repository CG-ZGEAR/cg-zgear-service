package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.dto.ProductDiscountDTO;
import com.codegym.cgzgearservice.dto.ReviewDTO;
import com.codegym.cgzgearservice.dto.SpecificationDTO;
import com.codegym.cgzgearservice.entitiy.product.*;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.exception.ProductNotFoundException;
import com.codegym.cgzgearservice.exception.ResourceNotFoundException;
import com.codegym.cgzgearservice.repository.*;
import com.codegym.cgzgearservice.service.ProductDiscountService;
import com.codegym.cgzgearservice.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ProductDetailRepository productDetailRepository;
    private final SpecificationTemplateRepository specificationTemplateRepository;
    private final UserRepository userService;
    private final ProductDiscountService productDiscountService;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();

        modelMapper.map(productDTO, product);

        Category category = categoryRepository.findByCategoryName(productDTO.getCategoryName());
        product.setCategory(category);

        createProductImages(product, productDTO.getImageUrls());
        product.setAvailable(true);

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
            product.setAvailable(false);
            productRepository.save(product);
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
        }
    }

    @Override
    public Page<ProductDTO> searchProduct(String searchTerm, Pageable pageable) {
        org.springframework.data.jpa.domain.Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //find by name
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + searchTerm.toLowerCase() + "%");
            predicates.add(namePredicate);
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(this::convertToProductDTO);
    }

    @Override
    public ProductDTO getProductByName(String productName) {
        if (!productRepository.findProductByProductNameAndAvailableIsTrue(productName).isPresent()) {
            throw new ResourceNotFoundException("Product with id " + productName + " not found in database");
        } else {
            Product product = productRepository.findProductByProductNameAndAvailableIsTrue(productName).get();
            ProductDTO productDTO = convertToProductDTO(product);
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

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAllAvailable(pageable);
        return products.map(this::convertToProductDTO);
    }

    @Override
    public Page<ProductDTO> getProductsByCategory(String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        Page<Product> products = productRepository.findProductsByCategoryAndAvailableIsTrue(category, pageable);
        return products.map(this::convertToProductDTO);
    }

    @Override
    @Transactional
    public ReviewDTO addReview(Long productId, ReviewDTO reviewDTO, Principal principal) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Lấy thông tin người dùng từ Principal
            User user = userService.findUserByUsername(principal.getName());

            Review review = new Review();
            review.setRating(reviewDTO.getRating());
            review.setComment(reviewDTO.getComment());
            review.setCreatedAt(LocalDateTime.now());
            review.setUser(user); // Thiết lập người dùng đánh giá
            review.setProduct(product);

            product.getReviews().add(review);
            productRepository.save(product);

            return modelMapper.map(review, ReviewDTO.class);
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Page<ProductDTO> getBestSellers(Pageable pageable) {
        Page<Product> products = productRepository.getBestSellers(pageable);
        return products.map(this::convertToProductDTO);
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        dto.setImageUrls(product.getProductImages().stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList()));
        dto.setSpecifications(getSpecificationsForProduct(product));
        dto.setDescription(getDescriptionForProduct(product));

        // Fetch and set product discounts
        List<ProductDiscountDTO> discountDTOs = productDiscountService
                .getCurrentDiscountsForProduct(product.getId())
                .stream()
                .map(discount -> modelMapper.map(discount, ProductDiscountDTO.class))
                .collect(Collectors.toList());
        dto.setDiscounts(discountDTOs);

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

    private List<ReviewDTO> getReviewsForProduct(Product product) {
        return product.getReviews().stream()
                .map(this::convertToReviewDTO)
                .collect(Collectors.toList());
    }

    private ReviewDTO convertToReviewDTO(Review review) {
        ReviewDTO dto = modelMapper.map(review, ReviewDTO.class);
        return dto;
    }


}
