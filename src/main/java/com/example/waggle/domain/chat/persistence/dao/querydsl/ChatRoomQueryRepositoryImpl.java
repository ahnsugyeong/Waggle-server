package com.example.waggle.domain.chat.persistence.dao.querydsl;

import static com.example.waggle.domain.chat.persistence.entity.QChatRoom.chatRoom;
import static com.example.waggle.domain.chat.persistence.entity.QChatRoomMember.chatRoomMember;

import com.example.waggle.domain.chat.persistence.entity.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ChatRoom> findByMemberId(Long memberId, Pageable pageable) {
        List<ChatRoom> chatRooms = queryFactory.selectFrom(chatRoom)
                .join(chatRoom.chatRoomMembers, chatRoomMember)
                .where(chatRoomMember.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(chatRoomMember.count())
                .from(chatRoomMember)
                .where(chatRoomMember.member.id.eq(memberId))
                .fetchFirst();

        return new PageImpl<>(chatRooms, pageable, total);
    }

    @Override
    public Long countChatRoomsByMemberId(Long memberId) {
        return queryFactory
                .select(chatRoomMember.count())
                .from(chatRoomMember)
                .where(chatRoomMember.member.id.eq(memberId))
                .fetchFirst();
    }
}