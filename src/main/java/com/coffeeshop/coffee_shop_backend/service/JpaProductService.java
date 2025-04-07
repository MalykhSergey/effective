package com.coffeeshop.coffee_shop_backend.service;

import com.coffeeshop.coffee_shop_backend.dto.ProductDTO;
import com.coffeeshop.coffee_shop_backend.exception.ResourceNotFoundException;
import com.coffeeshop.coffee_shop_backend.model.Category;
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

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    @Autowired
    public JpaProductService(CategoryService categoryService, ProductRepository productRepository) {
        this.categoryService = categoryService;
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
        return productRepository.findById(id).map(ProductDTO::new).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public Page<ProductDTO> getByNameContainingAndPriceBetween(String name, Long min, Long max, Pageable pageable) {
        Page<Product> productPage = productRepository.findByNameContainingAndPriceBetween(name, new BigDecimal(min), new BigDecimal(max), pageable);
        return convertToDTOPage(pageable, productPage);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        if (categoryService.getCategoryById(productDTO.getCategory().getId()).isEmpty())
            throw new ResourceNotFoundException("Category not found with id " + productDTO.getCategory().getId());
        Product product = new Product(productDTO);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        if (categoryService.getCategoryById(productDTO.getCategory().getId()).isEmpty())
            throw new ResourceNotFoundException("Category not found with id " + productDTO.getCategory().getId());
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setAvailable(productDTO.isAvailable());
        existingProduct.setCategory(new Category(productDTO.getCategory()));
        existingProduct = productRepository.save(existingProduct);
        return new ProductDTO(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        productRepository.delete(existingProduct);
    }

    private PageImpl<ProductDTO> convertToDTOPage(Pageable pageable, Page<Product> productPage) {
        List<ProductDTO> productDTOList = productPage.stream().map(ProductDTO::new).collect(Collectors.toList());
        return new PageImpl<>(productDTOList, pageable, productPage.getTotalElements());
    }
}
