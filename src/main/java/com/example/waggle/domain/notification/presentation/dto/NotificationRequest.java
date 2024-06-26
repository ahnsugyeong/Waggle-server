package com.example.waggle.domain.notification.presentation.dto;

import com.example.waggle.domain.board.persistence.entity.BoardType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NotificationRequest {
    @Data
    @Builder
    @Schema
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParticipationDto {
        private Long teamId;
        private String teamName;
    }

    @Data
    @Builder
    @Schema
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentDto {
        private BoardType boardType;
        private String commentContent;
    }

    @Data
    @Builder
    @Schema
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReplyDto {
        private String commentContent;
        private String replyContent;
    }

    @Data
    @Builder
    @Schema
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MentionDto {
        private String conversationContent;
    }

}
