package com.example.waggle.follow.service;

import com.example.waggle.domain.follow.entity.Follow;
import com.example.waggle.domain.follow.service.FollowCommandService;
import com.example.waggle.domain.follow.service.FollowQueryService;
import com.example.waggle.domain.member.entity.Member;
import com.example.waggle.domain.member.service.MemberCommandService;
import com.example.waggle.domain.member.service.MemberQueryService;
import com.example.waggle.global.component.DatabaseCleanUp;
import com.example.waggle.global.exception.handler.FollowHandler;
<<<<<<< HEAD
import com.example.waggle.web.dto.member.MemberRequest.MemberCredentialsDto;
=======
import com.example.waggle.web.dto.member.MemberRequest;
>>>>>>> develop
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FollowServiceTest {
    @Autowired
    FollowQueryService followQueryService;
    @Autowired
    FollowCommandService followCommandService;
    @Autowired
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

<<<<<<< HEAD
    MemberCredentialsDto signUpDto1;
    MemberCredentialsDto signUpDto2;
    MemberCredentialsDto signUpDto3;
    MemberCredentialsDto signUpDto4;
=======
    MemberRequest.AccessDto signUpDto1;
    MemberRequest.AccessDto signUpDto2;
    MemberRequest.AccessDto signUpDto3;
    MemberRequest.AccessDto signUpDto4;
>>>>>>> develop

    Long memberId1;
    Long memberId2;
    Long memberId3;
    Long memberId4;


    @AfterEach
    void afterEach() {
        databaseCleanUp.truncateAllEntity();
    }

    void setUp() {
<<<<<<< HEAD
        signUpDto1 = MemberCredentialsDto.builder()
=======
        signUpDto1 = MemberRequest.AccessDto.builder()
>>>>>>> develop
                .password("12345678")
                .email("wjdgks3264@naver.com")
                .build();

<<<<<<< HEAD
        signUpDto2 = MemberCredentialsDto.builder()
=======
        signUpDto2 = MemberRequest.AccessDto.builder()
>>>>>>> develop
                .password("12345678")
                .email("wjdgks2972@naver.com")
                .build();

<<<<<<< HEAD
        signUpDto3 = MemberCredentialsDto.builder()
=======
        signUpDto3 = MemberRequest.AccessDto.builder()
>>>>>>> develop
                .password("12345678")
                .email("wjdgksdfs@naver.com")
                .build();

<<<<<<< HEAD
        signUpDto4 = MemberCredentialsDto.builder()
=======
        signUpDto4 = MemberRequest.AccessDto.builder()
>>>>>>> develop
                .password("12345678")
                .email("hi@naver.com")
                .build();

        memberId1 = memberCommandService.signUp(signUpDto1);
        memberId2 = memberCommandService.signUp(signUpDto2);
        memberId3 = memberCommandService.signUp(signUpDto3);
        memberId4 = memberCommandService.signUp(signUpDto4);

    }

    @Test
    void follow() {
        //given
        setUp();
        //when
        Member A = memberQueryService.getMemberById(memberId1);
        Member B = memberQueryService.getMemberById(memberId2);
        followCommandService.follow(A.getUsername(), B.getNickname());
        List<Follow> followingsByUser = followQueryService.getFollowings(memberId1);
        //then
        assertThat(followingsByUser.size()).isEqualTo(1);
    }

    @Test
    void unfollow() {
        //given
        setUp();
        //when
        Member A = memberQueryService.getMemberById(memberId1);
        Member B = memberQueryService.getMemberById(memberId2);
        Member C = memberQueryService.getMemberById(memberId3);
        followCommandService.follow(A.getUsername(), B.getNickname());
        followCommandService.follow(A.getUsername(), C.getNickname());
        followCommandService.unFollow(A.getUsername(), B.getNickname());
        List<Follow> followingsByUser = followQueryService.getFollowings(memberId1);
        //then
        assertThat(followingsByUser.size()).isEqualTo(1);
    }

    @Test
    void follow_exception() {
        //given
        setUp();
        //when
        Member A = memberQueryService.getMemberById(memberId1);
        Member B = memberQueryService.getMemberById(memberId2);
        followCommandService.follow(A.getUsername(), B.getNickname());
        //then
<<<<<<< HEAD
        Assertions.assertThrows(FollowHandler.class,
                () -> followCommandService.follow(A.getUsername(), B.getNickname()));
=======
        Assertions.assertThrows(FollowHandler.class, () -> followCommandService.follow(A.getUsername(), B.getNickname()));
>>>>>>> develop
    }

    @Test
    void unfollow_exception() {
        //given
        setUp();
        //when
        Member A = memberQueryService.getMemberById(memberId1);
        Member B = memberQueryService.getMemberById(memberId2);
        Member C = memberQueryService.getMemberById(memberId3);
        Long followId = followCommandService.follow(A.getUsername(), B.getNickname());
        followCommandService.follow(A.getUsername(), C.getNickname());
        followCommandService.unFollow(A.getUsername(), B.getNickname());
        //then
<<<<<<< HEAD
        Assertions.assertThrows(FollowHandler.class,
                () -> followCommandService.unFollow(A.getUsername(), B.getNickname()));
=======
        Assertions.assertThrows(FollowHandler.class, () -> followCommandService.unFollow(A.getUsername(), B.getNickname()));
>>>>>>> develop
    }
}
