package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.SpecificationTemplateDTO;

import java.util.List;

/**
 * @author ADMIN
 */
public interface SpecificationTemplateService {

    List<SpecificationTemplateDTO> getSpecTemplatesByCategory(String categoryName);
}
