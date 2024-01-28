package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.SpecificationTemplateDTO;
import com.codegym.cgzgearservice.entitiy.product.SpecificationTemplate;
import com.codegym.cgzgearservice.repository.SpecificationTemplateRepository;
import com.codegym.cgzgearservice.service.SpecificationTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ADMIN
 */
@Service
@AllArgsConstructor
public class SpecificationTemplateServiceImpl implements SpecificationTemplateService {


    private final SpecificationTemplateRepository specificationTemplateRepository;

    @Override
    public List<SpecificationTemplateDTO> getSpecTemplatesByCategory(String categoryName) {
        List<SpecificationTemplate> specTemplates = specificationTemplateRepository.findByCategory_CategoryName(categoryName);
        return convertToDTOs(specTemplates);
    }

    private List<SpecificationTemplateDTO> convertToDTOs(List<SpecificationTemplate> specTemplates) {
        return specTemplates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SpecificationTemplateDTO convertToDTO(SpecificationTemplate specTemplate) {
        SpecificationTemplateDTO dto = new SpecificationTemplateDTO();
        dto.setId(specTemplate.getId());
        dto.setSpecKey(specTemplate.getSpecKey());
        return dto;
    }
}
