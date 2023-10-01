package com.example.waggle.board;

import com.example.waggle.commons.component.auditing.BaseEntity;
import com.example.waggle.comment.domain.Comment;
import com.example.waggle.hashtag.domain.BoardHashtag;
import com.example.waggle.media.domain.Media;
import com.example.waggle.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    protected Member member;

    @Lob
    protected String content;

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    protected List<BoardHashtag> boardHashtags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    protected List<Media> medias = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    protected List<Comment> comments = new ArrayList<>();


    public Board(Long id, Member member, String content, List<BoardHashtag> boardHashtags,
                 List<Media> medias, List<Comment> comments) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.boardHashtags = boardHashtags;
        this.medias = medias;
        this.comments = comments;
    }

}