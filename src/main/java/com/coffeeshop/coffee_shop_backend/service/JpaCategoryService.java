package com.coffeeshop.coffee_shop_backend.service;

import com.coffeeshop.coffee_shop_backend.dto.CategoryDTO;
import com.coffeeshop.coffee_shop_backend.exception.IdMustBeNullException;
import com.coffeeshop.coffee_shop_backend.exception.ResourceNotFoundException;
import com.coffeeshop.coffee_shop_backend.model.Category;
import com.coffeeshop.coffee_shop_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public JpaCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryDTO> getAll(Pageable pageable) {
        return this.convertToDTOPage(pageable, categoryRepository.findAll(pageable));
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() != null) {
            throw new IdMustBeNullException("Id of new category must be null!");
        }
        Category new_category = new Category(categoryDTO);
        categoryRepository.save(new_category);
        return new CategoryDTO(new_category);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryDTO.getId()));
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        categoryRepository.delete(category);
    }

    private PageImpl<CategoryDTO> convertToDTOPage(Pageable pageable, Page<Category> categoryPage) {
        List<CategoryDTO> categoryDTOList = categoryPage.stream().map(CategoryDTO::new).collect(Collectors.toList());
        return new PageImpl<>(categoryDTOList, pageable, categoryPage.getTotalElements());
    }
}
