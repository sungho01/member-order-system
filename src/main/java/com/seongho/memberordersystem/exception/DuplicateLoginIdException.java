package com.seongho.memberordersystem.exception;

public class DuplicateLoginIdException extends RuntimeException {
    public DuplicateLoginIdException(){
        super("아이디가 존재합니다.");
    }
}
