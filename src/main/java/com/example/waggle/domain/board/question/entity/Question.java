package com.example.waggle.domain.board.question.entity;

import com.example.waggle.domain.board.Board;
import com.example.waggle.domain.board.ResolutionStatus;
import com.example.waggle.web.dto.question.QuestionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Entity
@Getter
@SuperBuilder
@DiscriminatorValue("type_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends Board {
    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResolutionStatus status;

    public void changeQuestion(QuestionRequest.Post request) {
        this.content = request.getContent();
        this.title = request.getTitle();
        this.status = ResolutionStatus.valueOf(request.getStatus());
    }

    public void changeStatus(ResolutionStatus status) {
        this.status = status;
    }

}
