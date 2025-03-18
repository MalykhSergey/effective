package com.coffeeshop.coffee_shop_backend.dto;

import com.coffeeshop.coffee_shop_backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private boolean available;
    private CategoryDTO category;

    public ProductDTO(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        if (product.getCategory() != null) this.setCategory(new CategoryDTO(product.getCategory()));
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setImageUrl(product.getImageUrl());
        this.setAvailable(product.isAvailable());
    }
}