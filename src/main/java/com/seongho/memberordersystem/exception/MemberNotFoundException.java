package com.seongho.memberordersystem.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(){
        super("없는 회원입니다.");
    }
}
