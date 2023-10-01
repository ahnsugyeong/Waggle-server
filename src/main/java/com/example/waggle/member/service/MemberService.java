package com.example.waggle.member.service;

import com.example.waggle.commons.component.file.UploadFile;
import com.example.waggle.commons.security.JwtToken;
import com.example.waggle.member.dto.MemberDto;
import com.example.waggle.member.dto.MemberSimpleDto;
import com.example.waggle.member.dto.SignInDto;
import com.example.waggle.member.dto.SignUpDto;
import jakarta.servlet.http.HttpSession;

public interface MemberService {
    JwtToken signIn(SignInDto signInDto);

    MemberDto signUp(SignUpDto signUpDto);

    MemberDto signUpWithProfileImg(SignUpDto signUpDto, UploadFile profileImg);

    void signOut(HttpSession session);

    MemberSimpleDto findMemberSimpleDto(String username);

    MemberDto changeProfileImg(String username, UploadFile profileImg);
}