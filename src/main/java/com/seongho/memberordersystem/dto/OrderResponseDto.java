package com.seongho.memberordersystem.dto;

import java.time.LocalDateTime;

public class OrderResponseDto {
    private Long OrderId;

    private String productName;
    private int quantity;
    private int totalPrice;
    private LocalDateTime createdAt;

    public OrderResponseDto(Long OrderId, String productName, int quantity, int totalPrice, LocalDateTime createdAt){
        this.OrderId = OrderId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public Long getOrderId() {
        return OrderId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
