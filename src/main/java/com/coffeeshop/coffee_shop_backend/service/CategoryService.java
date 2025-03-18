package com.coffeeshop.coffee_shop_backend.service;

import com.coffeeshop.coffee_shop_backend.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO);

    void delete(Long id);

    Page<CategoryDTO> getAll(Pageable pageable);
}
