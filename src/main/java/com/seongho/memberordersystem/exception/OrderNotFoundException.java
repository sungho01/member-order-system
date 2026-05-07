package com.seongho.memberordersystem.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(){
        super("없는 주문입니다.");
    }
}
