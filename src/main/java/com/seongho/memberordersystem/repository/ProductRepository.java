package com.seongho.memberordersystem.repository;

import com.seongho.memberordersystem.entity.Order;
import com.seongho.memberordersystem.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
