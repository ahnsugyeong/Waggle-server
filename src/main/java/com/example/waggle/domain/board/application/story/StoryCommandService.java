package com.example.waggle.domain.board.application.story;

import com.example.waggle.domain.board.presentation.dto.story.StoryRequest;
import com.example.waggle.domain.member.persistence.entity.Member;


public interface StoryCommandService {

    Long createStory(StoryRequest createStoryRequest, Member member);

    Long updateStory(Long boardId,
                     StoryRequest updateStoryRequest,
                     Member member);

    void deleteStoryWithRelations(Long boardId, Member member);

    void deleteStoryByAdmin(Long boardId, Member member);

}
