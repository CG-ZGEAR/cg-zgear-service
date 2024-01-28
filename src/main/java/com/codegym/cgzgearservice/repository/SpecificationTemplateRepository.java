package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.product.SpecificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationTemplateRepository extends JpaRepository<SpecificationTemplate, Long> {
    SpecificationTemplate findSpecificationTemplateBySpecKey(String specKey);

    List<SpecificationTemplate> findByCategory_CategoryName(String categoryName);

}