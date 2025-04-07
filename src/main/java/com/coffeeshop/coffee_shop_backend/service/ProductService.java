package com.coffeeshop.coffee_shop_backend.service;

import com.coffeeshop.coffee_shop_backend.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDTO> getAllProducts(Pageable pageable);

    Page<ProductDTO> getAllProductsByCategoryId(Long id, Pageable pageable);

    ProductDTO getProductById(Long id);

    Page<ProductDTO> getByNameContainingAndPriceBetween(String name, Long min, Long max, Pageable pageable);

    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);

}
