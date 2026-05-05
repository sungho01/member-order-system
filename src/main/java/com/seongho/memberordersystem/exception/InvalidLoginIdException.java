package com.seongho.memberordersystem.exception;

public class InvalidLoginIdException extends RuntimeException {
    public InvalidLoginIdException(){
        super("아이디가 유효하지 않습니다.");
    }
}
