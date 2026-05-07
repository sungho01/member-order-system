package com.seongho.memberordersystem.dto;

public class OrderRequestDto {
    private Long productId;
    private int quantity;

    public OrderRequestDto() {}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
