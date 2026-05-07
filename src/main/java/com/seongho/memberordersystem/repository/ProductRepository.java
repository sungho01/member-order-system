package com.seongho.memberordersystem.repository;

import com.seongho.memberordersystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
