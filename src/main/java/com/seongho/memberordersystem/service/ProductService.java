package com.seongho.memberordersystem.service;

import com.seongho.memberordersystem.dto.ProductRequestDto;
import com.seongho.memberordersystem.dto.ProductResponseDto;
import com.seongho.memberordersystem.entity.Product;
import com.seongho.memberordersystem.exception.ProductNotFoundException;
import com.seongho.memberordersystem.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto requestDto){
        Product p = new Product();
        p.setName(requestDto.getName());
        p.setStock(requestDto.getStock());
        p.setPrice(requestDto.getPrice());
        Product saved = productRepository.save(p);

        return new ProductResponseDto(saved.getProductId(), saved.getName(), saved.getPrice(), saved.getStock());
    }

    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto){
        Product p = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        p.setName(requestDto.getName());
        p.setPrice(requestDto.getPrice());
        p.setStock(requestDto.getStock());

        return new ProductResponseDto(p.getProductId(), p.getName(), p.getPrice(), p.getStock());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId){
        Product p = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        return new ProductResponseDto(p.getProductId(), p.getName(), p.getPrice(), p.getStock());
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProducts(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(product -> new ProductResponseDto(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        ));
    }
}
