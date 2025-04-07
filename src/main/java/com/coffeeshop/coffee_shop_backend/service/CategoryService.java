package com.coffeeshop.coffee_shop_backend.service;

import com.coffeeshop.coffee_shop_backend.dto.CategoryDTO;
import com.coffeeshop.coffee_shop_backend.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO);

    void delete(Long id);

    Page<CategoryDTO> getAll(Pageable pageable);

    Optional<Category> getCategoryById(Long id);
}
