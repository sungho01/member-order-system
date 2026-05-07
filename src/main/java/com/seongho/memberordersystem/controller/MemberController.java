package com.seongho.memberordersystem.controller;

import com.seongho.memberordersystem.dto.LoginRequestDto;
import com.seongho.memberordersystem.dto.SignupRequestDto;
import com.seongho.memberordersystem.entity.Member;
import com.seongho.memberordersystem.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public String signupMember(@RequestBody SignupRequestDto requestDto){
        return memberService.signupMember(requestDto);
    }

    @PostMapping("/login")
    public String loginMember(@RequestBody LoginRequestDto requestDto, HttpSession session){
        Member m = memberService.loginMember(requestDto);
        session.setAttribute("memberId", m.getMemberId());

        return "로그인 성공!";

    }
    @PostMapping("/logout")
    public String logoutMember(HttpSession session){
        session.invalidate();
        return "로그아웃 성공";
    }

}
