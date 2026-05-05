package com.seongho.memberordersystem.service;

import com.seongho.memberordersystem.dto.LoginRequestDto;
import com.seongho.memberordersystem.dto.SignupRequestDto;
import com.seongho.memberordersystem.entity.Member;
import com.seongho.memberordersystem.exception.DuplicateLoginIdException;
import com.seongho.memberordersystem.exception.InvalidLoginIdException;
import com.seongho.memberordersystem.exception.InvalidPasswordException;
import com.seongho.memberordersystem.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.Supplier;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String signupMember(SignupRequestDto requestDto){
        Member m = new Member();
        if(memberRepository.existsByLoginId(requestDto.getLoginId())){
            throw new DuplicateLoginIdException();
        }

        m.setLoginId(requestDto.getLoginId());
        m.setPassword(requestDto.getPassword());
        m.setName(requestDto.getName());
        m.setPhoneNumber(requestDto.getPhoneNumber());
        memberRepository.save(m);


        return "회원가입 성공!";
    }
    @Transactional
    public Member loginMember(LoginRequestDto requestDto) {
        Member m = memberRepository.findByLoginId(requestDto.getLoginId()).orElseThrow(InvalidLoginIdException::new);
        if(!Objects.equals(m.getPassword(), requestDto.getPassword())){
            throw new InvalidPasswordException();
        }

        return m;
    }
}