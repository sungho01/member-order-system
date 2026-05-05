package com.seongho.memberordersystem.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(){
        super("비밀번호가 유효하지 않습니다..");
    }
}
