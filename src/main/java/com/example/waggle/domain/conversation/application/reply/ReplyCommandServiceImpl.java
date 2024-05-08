package com.example.waggle.domain.conversation.application.reply;

import com.example.waggle.domain.conversation.persistence.dao.comment.jpa.CommentRepository;
import com.example.waggle.domain.conversation.persistence.dao.reply.ReplyRepository;
import com.example.waggle.domain.conversation.persistence.entity.Comment;
import com.example.waggle.domain.conversation.persistence.entity.Reply;
import com.example.waggle.domain.conversation.presentation.dto.reply.ReplyRequest;
import com.example.waggle.domain.member.persistence.entity.Member;
import com.example.waggle.exception.object.handler.CommentHandler;
import com.example.waggle.exception.object.handler.ReplyHandler;
import com.example.waggle.exception.payload.code.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyCommandServiceImpl implements ReplyCommandService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long createReply(Long commentId, ReplyRequest createReplyRequest, Member member) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentHandler(ErrorStatus.COMMENT_NOT_FOUND));

        Reply reply = buildReply(comment, createReplyRequest, member);
        replyRepository.save(reply);

        return reply.getId();
    }

    @Override
    public Long updateReply(Long replyId, ReplyRequest updateReplyRequest, Member member) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyHandler(ErrorStatus.REPLY_NOT_FOUND));
        validateMember(reply, member);
        reply.changeContent(updateReplyRequest.getContent());
        return reply.getId();
    }

    @Override
    public void deleteReply(Long replyId, Member member) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyHandler(ErrorStatus.REPLY_NOT_FOUND));
        validateMember(reply, member);
        replyRepository.delete(reply);
    }

    public void validateMember(Reply reply, Member member) {
        if (!reply.getMember().equals(member)) {
            throw new ReplyHandler(ErrorStatus.REPLY_CANNOT_EDIT_OTHERS);
        }
    }

    private static Reply buildReply(Comment comment, ReplyRequest createReplyRequest, Member member) {
        return Reply.builder()
                .member(member)
                .comment(comment)
                .content(createReplyRequest.getContent())
                .build();
    }
}