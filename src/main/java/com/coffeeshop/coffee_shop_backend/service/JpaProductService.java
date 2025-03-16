package com.coffeeshop.coffee_shop_backend.service;

import com.coffeeshop.coffee_shop_backend.dto.ProductDTO;
import com.coffeeshop.coffee_shop_backend.exception.ResourceNotFoundException;
import com.coffeeshop.coffee_shop_backend.model.Product;
import com.coffeeshop.coffee_shop_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaProductService implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public JpaProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return convertToDTOPage(pageable, productPage);
    }

    @Override
    public Page<ProductDTO> getAllProductsByCategoryId(Long id, Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByCategoryId(id, pageable);
        return convertToDTOPage(pageable, productPage);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public Page<ProductDTO> getByNameContainingAndPriceBetween(String name, Long min, Long max, Pageable pageable) {
        Page<Product> productPage = productRepository.findByNameContainingAndPriceBetween(name, new BigDecimal(min), new BigDecimal(max), pageable);
        return convertToDTOPage(pageable, productPage);
    }

    private PageImpl<ProductDTO> convertToDTOPage(Pageable pageable, Page<Product> productPage) {
        List<ProductDTO> productDTOList = productPage.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new PageImpl<>(productDTOList, pageable, productPage.getTotalElements());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        if (product.getCategory() != null)
            dto.setCategoryId(product.getCategory().getId());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setAvailable(product.isAvailable());
        return dto;
    }
}
