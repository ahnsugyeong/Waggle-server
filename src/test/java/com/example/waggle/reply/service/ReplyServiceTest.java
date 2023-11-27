package com.example.waggle.reply.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.waggle.board.story.dto.StorySummaryDto;
import com.example.waggle.board.story.dto.StoryWriteDto;
import com.example.waggle.board.story.service.StoryService;
import com.example.waggle.comment.dto.CommentViewDto;
import com.example.waggle.comment.dto.CommentWriteDto;
import com.example.waggle.comment.dto.ReplyViewDto;
import com.example.waggle.comment.dto.ReplyWriteDto;
import com.example.waggle.comment.service.CommentService;
import com.example.waggle.comment.service.ReplyService;
import com.example.waggle.commons.annotation.withMockUser.WithMockCustomUser;
import com.example.waggle.commons.component.DatabaseCleanUp;
import com.example.waggle.commons.util.service.BoardType;
import com.example.waggle.member.dto.SignUpDto;
import com.example.waggle.member.service.MemberService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ReplyServiceTest {

    @Autowired
    private StoryService storyService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;


    SignUpDto signUpDto1;
    SignUpDto signUpDto2;
    SignUpDto signUpDto3;
    SignUpDto signUpDto4;
    SignUpDto signUpDto5;


    StoryWriteDto storyWriteDto1;
    StoryWriteDto storyWriteDto2;

    CommentWriteDto commentWriteDto1;
    ReplyWriteDto replyWriteDto1;
    ReplyWriteDto replyWriteDto2;

    List<String> mentions1 = new ArrayList<>();
    List<String> mentions2 = new ArrayList<>();



    @BeforeEach
    void setDto() {

        mentions1.add("user1");
        mentions1.add("user2");
        mentions2.add("user3");
        mentions2.add("user4");

        signUpDto1 = SignUpDto.builder()
                .username("member1")
                .password("12345678")
                .nickname("닉네임1")
                .address("서울시 광진구")
                .phone("010-1234-5678")
                .build();
        signUpDto2 = SignUpDto.builder()
                .username("user1")
                .password("12345678")
                .nickname("닉네임1")
                .address("서울시 광진구")
                .phone("010-1234-5678")
                .build();
        signUpDto3 = SignUpDto.builder()
                .username("user2")
                .password("12345678")
                .nickname("닉네임1")
                .address("서울시 광진구")
                .phone("010-1234-5678")
                .build();
        signUpDto4 = SignUpDto.builder()
                .username("user3")
                .password("12345678")
                .nickname("닉네임1")
                .address("서울시 광진구")
                .phone("010-1234-5678")
                .build();
        signUpDto5 = SignUpDto.builder()
                .username("user4")
                .password("12345678")
                .nickname("닉네임1")
                .address("서울시 광진구")
                .phone("010-1234-5678")
                .build();



        storyWriteDto1 = StoryWriteDto.builder()
                .content("i love my choco")
                .thumbnail("www.waggle")
                .build();

        storyWriteDto2 = StoryWriteDto.builder()
                .content("how can i do make he is happy?")
                .thumbnail("www.waggle")
                .build();

        commentWriteDto1 = CommentWriteDto.builder()
                .content("comment1")
                .build();

        replyWriteDto1 = ReplyWriteDto.builder()
                .content("reply1")
                .mentions(mentions1)
                .build();

        replyWriteDto2 = ReplyWriteDto.builder()
                .content("reply2")
                .mentions(mentions2)
                .build();


    }
    @AfterEach
    void clean() {
        databaseCleanUp.truncateAllEntity();
    }

    private void setAll() throws IOException {
        //member set
        memberService.signUp(signUpDto1, null);
        memberService.signUp(signUpDto2, null);
        memberService.signUp(signUpDto3, null);
        memberService.signUp(signUpDto4, null);
        memberService.signUp(signUpDto5, null);

        //story set
        storyService.createStory(storyWriteDto1, new ArrayList<>(), null);
        storyService.createStory(storyWriteDto2, new ArrayList<>(), null);

        StorySummaryDto storySummaryDto = storyService.getStories().get(0);

        //comment set
        commentService.createComment(storySummaryDto.getId(), commentWriteDto1, BoardType.STORY);
        //reply set
        List<CommentViewDto> comments = commentService.getComments(storySummaryDto.getId());
        replyService.createReply(comments.get(0).getId(), replyWriteDto1);

    }


    @Test
    @WithMockCustomUser
    void findReplies() throws IOException {
        //given
        setAll();
        StorySummaryDto storySummaryDto = storyService.getStories().get(0);
        List<CommentViewDto> comments = commentService.getComments(storySummaryDto.getId());
        //when
        List<ReplyViewDto> replies = replyService.getReplies(comments.get(0).getId());
        //then
        assertThat(replies.size()).isEqualTo(1);
        assertThat(replies.get(0).getMentionMembers().size()).isEqualTo(2);
    }


    @Test
    @WithMockCustomUser
    void changeReply() throws IOException {
        //given
        setAll();
        StorySummaryDto storySummaryDto = storyService.getStories().get(0);
        List<CommentViewDto> comments = commentService.getComments(storySummaryDto.getId());
        List<ReplyViewDto> replies = replyService.getReplies(comments.get(0).getId());
        //when
        replyService.updateReply(replies.get(0).getId(), replyWriteDto2);
        //then
        List<ReplyViewDto> editReplies = replyService.getReplies(comments.get(0).getId());
        assertThat(editReplies.get(0).getContent()).isEqualTo("reply2");
        assertThat(editReplies.get(0).getMentionMembers().get(0)).isEqualTo("user3");

    }

    @Test
    @WithMockCustomUser
    void deleteReply() throws IOException {
        //given
        setAll();
        StorySummaryDto storySummaryDto = storyService.getStories().get(0);
        List<CommentViewDto> comments = commentService.getComments(storySummaryDto.getId());
        List<ReplyViewDto> replies = replyService.getReplies(comments.get(0).getId());

        //when
        replyService.deleteReply(replies.get(0).getId());

        //then
        List<ReplyViewDto> afterDeleteReplies = replyService.getReplies(comments.get(0).getId());
        assertThat(afterDeleteReplies.size()).isEqualTo(0);
    }
}