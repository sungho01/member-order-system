package com.seongho.memberordersystem.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("상품 재고가 부족합니다.");
    }
}
