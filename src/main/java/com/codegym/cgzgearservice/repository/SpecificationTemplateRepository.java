package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.product.SpecificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationTemplateRepository extends JpaRepository<SpecificationTemplate, Long> {
}