package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.SpecificationTemplateDTO;
import com.codegym.cgzgearservice.service.SpecificationTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spec-templates")
@AllArgsConstructor
public class SpecificationTemplateController {

    private final SpecificationTemplateService specificationTemplateService;

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<SpecificationTemplateDTO>> getSpecTemplatesByCategory(@PathVariable String categoryName) {
        List<SpecificationTemplateDTO> specTemplates = specificationTemplateService.getSpecTemplatesByCategory(categoryName);
        return ResponseEntity.ok(specTemplates);
    }
}

