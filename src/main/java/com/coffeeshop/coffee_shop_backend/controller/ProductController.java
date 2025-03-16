package com.coffeeshop.coffee_shop_backend.controller;

import com.coffeeshop.coffee_shop_backend.dto.ProductDTO;
import com.coffeeshop.coffee_shop_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns list of all products  as a paginated response")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a single product by its ID")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{id}")
    @Operation(summary = "Get product by category ID", description = "Returns products by its category ID as a paginated response")
    public ResponseEntity<Page<ProductDTO>> getProductByCategoryId(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(id, pageable));
    }

    @GetMapping("/special")
    @Operation(
            summary = "Get products by name containing a string and price within a range",
            description = "Returns products whose names contain the specified string and whose prices fall within the given range. The results are returned as a paginated response."
    )
    public ResponseEntity<Page<ProductDTO>> getByNameContainingAndPriceBetween(@PathParam("name") String name, @PathParam("min") Long min, @PathParam("max") Long max, Pageable pageable) {
        return ResponseEntity.ok(productService.getByNameContainingAndPriceBetween(name, min, max, pageable));
    }
}
