package com.coffeeshop.coffee_shop_backend.model;

import com.coffeeshop.coffee_shop_backend.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> productList;

    public Category(CategoryDTO categoryDTO) {
        this.setId(categoryDTO.getId());
        this.setName(categoryDTO.getName());
    }
}
