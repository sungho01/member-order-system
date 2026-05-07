package com.seongho.memberordersystem.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(){
        super("없는 상품입니다.");
    }
}
