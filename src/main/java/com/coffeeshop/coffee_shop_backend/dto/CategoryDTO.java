package com.coffeeshop.coffee_shop_backend.dto;

import com.coffeeshop.coffee_shop_backend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO{
    private Long id;
    private String name;


    public CategoryDTO(Category category) {
        this.setId(category.getId());
        this.setName(category.getName());
    }
}