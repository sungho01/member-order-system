package com.seongho.memberordersystem.controller;

import com.seongho.memberordersystem.dto.ProductRequestDto;
import com.seongho.memberordersystem.dto.ProductResponseDto;
import com.seongho.memberordersystem.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto requestDto){
        return productService.addProduct(requestDto);
    }

    @PutMapping("/products/{productId}")
    public ProductResponseDto updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto requestDto){
        return productService.updateProduct(productId, requestDto);
    }

    @GetMapping("/products/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(Pageable pageable){
        return productService.getProducts(pageable);
    }
}
