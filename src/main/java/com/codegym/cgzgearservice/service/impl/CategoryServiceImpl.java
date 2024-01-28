package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.entitiy.product.Category;
import com.codegym.cgzgearservice.repository.CategoryRepository;
import com.codegym.cgzgearservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ADMIN
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
