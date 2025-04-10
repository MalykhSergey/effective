package com.coffeeshop.coffee_shop_backend.repository;

import com.coffeeshop.coffee_shop_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
